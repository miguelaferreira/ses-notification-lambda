terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.15"
    }
  }
}

provider "aws" {
  region = local.region

  # Make it faster by skipping something
  skip_metadata_api_check     = true
  skip_region_validation      = true
  skip_credentials_validation = true
  skip_requesting_account_id  = true
}

variable "function_version" {
  type    = string
  default = "test"
}

locals {
  region        = "eu-central-1"
  account_id    = data.aws_caller_identity.current.account_id
  app_name      = "ses-notifications"
  resource_name = "${local.app_name}-${random_pet.this.id}"
}

data "aws_caller_identity" "current" {}

resource "random_pet" "this" {
  length = 2
}

module "lambda_function" {
  source  = "terraform-aws-modules/lambda/aws"
  version = "~> 5.0"

  function_name  = local.resource_name
  runtime        = "provided.al2"
  create_package = false
  package_type   = "Image"
  image_uri      = "${local.container_image}:test"
  architectures  = [var.architecture]

  timeout     = 60
  memory_size = 512

  tracing_mode          = "Active"
  attach_tracing_policy = true


  environment_variables = {
    ONLINE_REPORTING_FROM = var.email_from
    ONLINE_REPORTING_TO   = var.email_to
  }

  publish                                 = true
  create_current_version_allowed_triggers = true

  allowed_triggers = {
    sns = {
      service    = "sns"
      source_arn = aws_sns_topic.monitoring.arn
    }
  }

  depends_on = [null_resource.build_container]
}

resource "aws_iam_role_policy_attachment" "lambda_role_network" {
  role       = module.lambda_function.lambda_role_name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaENIManagementAccess"
}

resource "aws_iam_role_policy_attachment" "lambda_role" {
  role       = module.lambda_function.lambda_role_name
  policy_arn = aws_iam_policy.lambda_role.arn
}

resource "aws_iam_policy" "lambda_role" {
  policy = data.aws_iam_policy_document.lambda_role.json
}

data "aws_iam_policy_document" "lambda_role" {
  statement {
    actions   = ["ses:SendEmail"]
    effect    = "Allow"
    resources = ["*"]
  }
}

resource "aws_sns_topic_subscription" "monitoring_lambda" {
  topic_arn = aws_sns_topic.monitoring.arn
  protocol  = "lambda"
  endpoint  = module.lambda_function.lambda_function_arn
}
