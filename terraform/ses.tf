resource "aws_ses_identity_notification_topic" "test" {
  topic_arn                = aws_sns_topic.monitoring.arn
  notification_type        = "Bounce"
  identity                 = aws_ses_email_identity.emails[var.email_from].email
  include_original_headers = true
}

resource "aws_sns_topic" "monitoring" {
  name = "${random_pet.this.id}-sns-monitoring"
}

resource "aws_ses_email_identity" "emails" {
  for_each = toset([var.email_from, var.email_to])

  email = each.value
}
