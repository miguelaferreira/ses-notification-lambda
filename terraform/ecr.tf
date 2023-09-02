locals {
  container_image = aws_ecr_repository.test.repository_url
}

resource "aws_ecr_repository" "test" {
  name                 = local.app_name
  image_tag_mutability = "MUTABLE"
  force_delete         = true
}

data "aws_iam_policy_document" "full_access" {
  statement {
    effect = "Allow"

    principals {
      type = "AWS"

      identifiers = ["arn:aws:iam::${local.account_id}:root"]
    }

    actions = ["ecr:*"]
  }
}

resource "aws_ecr_repository_policy" "ecr" {
  repository = aws_ecr_repository.test.name
  policy     = data.aws_iam_policy_document.full_access.json
}

data "aws_ecr_authorization_token" "token" {
}
