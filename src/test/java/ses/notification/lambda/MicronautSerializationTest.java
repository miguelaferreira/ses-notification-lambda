package ses.notification.lambda;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import ses.notification.lambda.aws.SnsEvent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class MicronautSerializationTest {

    @Inject
    private ObjectMapper mapper;

    private final String event = """
            {
                "Records": [
                    {
                        "EventSource": "aws:sns",
                        "EventVersion": "1.0",
                        "EventSubscriptionArn": "arn:aws:sns:eu-central-1:136651410320:immortal-anteater-sns-monitoring:0cd21928-4067-4df8-a688-7b0e0dd152f2",
                        "Sns": {
                            "Type": "Notification",
                            "MessageId": "ef666b50-c15c-5450-9c81-363ebe1f4319",
                            "TopicArn": "arn:aws:sns:eu-central-1:136651410320:immortal-anteater-sns-monitoring",
                            "Subject": null,
                            "Message": "{\\"notificationType\\":\\"Bounce\\",\\"bounce\\":{\\"feedbackId\\":\\"0107018a5b085338-c1874a3d-ddfb-4b3a-ae2a-9307cf6cc3d3-000000\\",\\"bounceType\\":\\"Permanent\\",\\"bounceSubType\\":\\"General\\",\\"bouncedRecipients\\":[{\\"emailAddress\\":\\"bounce@simulator.amazonses.com\\",\\"action\\":\\"failed\\",\\"status\\":\\"5.1.1\\",\\"diagnosticCode\\":\\"smtp; 550 5.1.1 user unknown\\"}],\\"timestamp\\":\\"2023-09-03T12:33:06.000Z\\",\\"remoteMtaIp\\":\\"54.154.47.106\\",\\"reportingMTA\\":\\"dns; b224-6.smtp-out.eu-central-1.amazonses.com\\"},\\"mail\\":{\\"timestamp\\":\\"2023-09-03T12:33:06.244Z\\",\\"source\\":\\"m.ferreira+test-from@gresb.com\\",\\"sourceArn\\":\\"arn:aws:ses:eu-central-1:136651410320:identity/m.ferreira+test-from@gresb.com\\",\\"sourceIp\\":\\"84.83.92.168\\",\\"callerIdentity\\":\\"gresb-developer\\",\\"sendingAccountId\\":\\"136651410320\\",\\"messageId\\":\\"0107018a5b0850c4-f382fa08-0b44-491e-a91c-f757a3df1438-000000\\",\\"destination\\":[\\"bounce@simulator.amazonses.com\\"],\\"headersTruncated\\":false,\\"headers\\":[{\\"name\\":\\"From\\",\\"value\\":\\"m.ferreira+test-from@gresb.com\\"},{\\"name\\":\\"To\\",\\"value\\":\\"bounce@simulator.amazonses.com\\"},{\\"name\\":\\"Subject\\",\\"value\\":\\"sasfsfsa\\"},{\\"name\\":\\"MIME-Version\\",\\"value\\":\\"1.0\\"},{\\"name\\":\\"Content-Type\\",\\"value\\":\\"multipart/alternative;  boundary=\\\\\\"----=_Part_3116359_1463192386.1693744386247\\\\\\"\\"}],\\"commonHeaders\\":{\\"from\\":[\\"m.ferreira+test-from@gresb.com\\"],\\"to\\":[\\"bounce@simulator.amazonses.com\\"],\\"subject\\":\\"sasfsfsa\\"}}}",
                            "Timestamp": "2023-09-03T12:33:07.075Z",
                            "SignatureVersion": "1",
                            "Signature": "FIQvcJ3qHZUbXjjkAanbCLSocEbdr0OPWmhoADZmAzaWlSxFw3X3cRXVWo0m3S3eGk2eB/h4kUWoiR5uOYWtKM1loUDJ3ngtB7jvR84/YZSDicHBCI37fgej7qpNAMMwnmThjvsSn+HmZGQai6mDvhMEZlGZNZOKXPGSWKEGhWcGnL0a7Qd8yRzx370TRMfmrVvt30mqgnvAPnTwqvVI6p0oZE6IzRzXn272ISBbPHQQqcLsSy5RtUh6lSRKV1AbB6HhFS8rohJ25BbDJAWmfSGBJCi2FFfhSk4rJpJeaphabIe4EWoYLy8TJEmgFpGw9Lm1oLP9mKK7xZVyGoiyPQ==",
                            "SigningCertUrl": "https://sns.eu-central-1.amazonaws.com/SimpleNotificationService-01d088a6f77103d0fe307c0069e40ed6.pem",
                            "UnsubscribeUrl": "https://sns.eu-central-1.amazonaws.com/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:eu-central-1:136651410320:immortal-anteater-sns-monitoring:0cd21928-4067-4df8-a688-7b0e0dd152f2",
                            "MessageAttributes": {}
                        }
                    }
                ]
            }
            """;

    @Test
    void deserializeSnsEvent() throws IOException {
        final SnsEvent object = mapper.readValue(event, SnsEvent.class);

        assertThat(object).isNotNull();
        assertThat(object.records()).isNotEmpty();
        assertThat(object.records().get(0).sns()).isNotNull();
        assertThat(object.records().get(0).sns().message()).isNotNull();
    }
}
