package ses.notification.lambda;

import io.micronaut.serde.ObjectMapper;
import org.junit.jupiter.api.Test;
import ses.notification.lambda.aws.SesBounceMessage;
import ses.notification.lambda.aws.SesComplaintMessage;
import ses.notification.lambda.aws.SesDeliveryMessage;
import ses.notification.lambda.aws.SesMessage;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class DeserializationServiceTest {

    private final DeserializationService deserializationService = new DeserializationService(ObjectMapper.getDefault());

    @Test
    void readDeliveryNotification() throws IOException {
        String notification = ""
                + "{"
                + "\"notificationType\": \"Delivery\""
                + "}";

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesDeliveryMessage.class);
    }

    @Test
    void readDeliveryEvent() throws IOException {
        String notification = ""
                + "{"
                + "\"eventType\": \"Delivery\""
                + "}";

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesDeliveryMessage.class);
    }

    @Test
    void readBounceNotification() throws IOException {
        String notification = ""
                + "{"
                + "\"notificationType\": \"Bounce\""
                + "}";

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesBounceMessage.class);
    }

    @Test
    void readBounceEvent() throws IOException {
        String notification = "{\n" +
                "    \"eventType\": \"Bounce\",\n" +
                "    \"bounce\": {\n" +
                "        \"feedbackId\": \"0100017f5acb69b0-6c754b6c-3cfd-423f-8d91-2cc01b3d7f75-000000\",\n" +
                "        \"bounceType\": \"Permanent\",\n" +
                "        \"bounceSubType\": \"General\",\n" +
                "        \"bouncedRecipients\": [\n" +
                "            {\n" +
                "                \"emailAddress\": \"bounce@simulator.amazonses.com\",\n" +
                "                \"action\": \"failed\",\n" +
                "                \"status\": \"5.1.1\",\n" +
                "                \"diagnosticCode\": \"smtp; 550 5.1.1 user unknown\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"timestamp\": \"2022-03-05T15:55:54.762Z\",\n" +
                "        \"reportingMTA\": \"dns; a8-62.smtp-out.amazonses.com\"\n" +
                "    },\n" +
                "    \"mail\": {\n" +
                "        \"timestamp\": \"2022-03-05T15:55:53.969Z\",\n" +
                "        \"source\": \"m.ferreira@gresb.com\",\n" +
                "        \"sourceArn\": \"arn:aws:ses:us-east-1:844875565510:identity/gresb.com\",\n" +
                "        \"sendingAccountId\": \"844875565510\",\n" +
                "        \"messageId\": \"0100017f5acb66f1-e509f90c-9c19-4dd6-ae93-bb7a0f7ea93c-000000\",\n" +
                "        \"destination\": [\n" +
                "            \"bounce@simulator.amazonses.com\"\n" +
                "        ],\n" +
                "        \"headersTruncated\": false,\n" +
                "        \"headers\": [\n" +
                "            {\n" +
                "                \"name\": \"From\",\n" +
                "                \"value\": \"m.ferreira@gresb.com\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"To\",\n" +
                "                \"value\": \"bounce@simulator.amazonses.com\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Subject\",\n" +
                "                \"value\": \"dafasdf\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"MIME-Version\",\n" +
                "                \"value\": \"1.0\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"Content-Type\",\n" +
                "                \"value\": \"multipart/alternative;  boundary=\\\"----=_Part_1237092_28065239.1646495753972\\\"\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"commonHeaders\": {\n" +
                "            \"from\": [\n" +
                "                \"m.ferreira@gresb.com\"\n" +
                "            ],\n" +
                "            \"to\": [\n" +
                "                \"bounce@simulator.amazonses.com\"\n" +
                "            ],\n" +
                "            \"messageId\": \"0100017f5acb66f1-e509f90c-9c19-4dd6-ae93-bb7a0f7ea93c-000000\",\n" +
                "            \"subject\": \"dafasdf\"\n" +
                "        },\n" +
                "        \"tags\": {\n" +
                "            \"ses:operation\": [\n" +
                "                \"SendEmail\"\n" +
                "            ],\n" +
                "            \"ses:configuration-set\": [\n" +
                "                \"gresb-ses-config\"\n" +
                "            ],\n" +
                "            \"ses:source-ip\": [\n" +
                "                \"45.95.65.150\"\n" +
                "            ],\n" +
                "            \"ses:from-domain\": [\n" +
                "                \"gresb.com\"\n" +
                "            ],\n" +
                "            \"ses:caller-identity\": [\n" +
                "                \"gresb-root-developer-break-glass\"\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesBounceMessage.class);
    }

    @Test
    void readComplaintNotification() throws IOException {
        String notification = ""
                + "{"
                + "\"notificationType\": \"Complaint\""
                + "}";

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesComplaintMessage.class);
    }
}
