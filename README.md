[![Continuous Integration](https://github.com/miguelaferreira/ses-notification-lambda/actions/workflows/development.yml/badge.svg)](https://github.com/miguelaferreira/ses-notification-lambda/actions/workflows/development.yml)
[![Continuous Delivery](https://github.com/miguelaferreira/ses-notification-lambda/actions/workflows/create-release.yaml/badge.svg)](https://github.com/miguelaferreira/ses-notification-lambda/actions/workflows/create-release.yaml)
[![CodeQL](https://github.com/miguelaferreira/ses-notification-lambda/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/miguelaferreira/ses-notification-lambda/actions/workflows/codeql-analysis.yml)

# ses-notification-lambda

An AWS Lambda function to process SES notifications and report them via email. SES requires that users setup
notifications for email events (e.g. bounced emails) in order to monitor email delivery. These notifications are JSON
objects that are either delivered to an email address, or to an SNS topic. When the notifications are delivered to an
SNS topic it is possible to have them processed by a lambda function. The ses-notification-lambda in this repository
does just that. It parses the SNS events, extracts the container SES notifications, parses them and then creates an
email report for each notification. The reports are sent by email using SES.

## How to use

### Create an AWS lambda function

Follow the [AWS Lambda docs](https://docs.aws.amazon.com/lambda/latest/dg/getting-started-create-function.html), select
a Java 11 (Corretto) runtime, and create an execution role with basic lambda permissions. Edit the execution role to add
permissions to send email via SES. Follow
the [AWS SES docs](https://docs.aws.amazon.com/ses/latest/dg/configure-identities.html) to configure and verify the
required identities in SES to allow the lambda to send email using SES. Configure the lambda function handler to
be `ses.notification.lambda.RequestHandler` and two environment variables:

- `REPORTING_FROM`: The email address used in the "From" field of the report messages;
- `REPORTING_TO`: The email address used in the "To" field of the report messages.

The from and to addresses have to be verified in SES (either by verifying a domain, or the addresses), and can even be
the same.

### Download a release

Download a release from the [releases page](https://github.com/miguelaferreira/ses-notification-lambda/releases).
The deployable for the lambda is the jar with all dependencies (named after `ses-notification-lambda-vA.B.C-all.jar`).

### Deploy the lambda function

Deploy the jar file (`ses-notification-lambda-vA.B.C-all.jar`) to the AWS Lambda function.

### Configure SES to deliver notifications to an SNS topic

Follow the [AWS SES docs](https://docs.aws.amazon.com/ses/latest/dg/monitor-using-event-publishing.html) to configure
event publishing to SNS. Make sure to select "AWS Lambda" protocol for the topic subscript, and select the
ses-notification-lambda function as the endpoint for it.

### Test the setup

To test the setup use
the [SES mailbox simulator](https://docs.aws.amazon.com/ses/latest/dg/send-an-email-from-console.html) and generate the
events for the types of scenarios you are interested in. If all is well configured you should receive reports in the
email inbox you configured for `REPORTING_TO`. If the reports do not arrive, check the event publishing configuration
between SES and SNS, and the lambda execution logs.
