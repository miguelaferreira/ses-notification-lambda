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
        String notification = """
                {
                    "notificationType": "Delivery"
                }
                """;

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesDeliveryMessage.class);
    }

    @Test
    void readDeliveryEvent() throws IOException {
        String notification = """
                {
                    "eventType": "Delivery"
                }
                """;


        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesDeliveryMessage.class);
    }

    @Test
    void readBounceNotification() throws IOException {
        String notification = """
                {
                    "notificationType": "Bounce"
                }
                """;

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesBounceMessage.class);
    }

    @Test
    void readBounceEvent() throws IOException {
        String notification = """
                {
                     "eventType": "Bounce",
                     "bounce": {
                         "feedbackId": "0100017f5acb69b0-6c754b6c-3cfd-423f-8d91-2cc01b3d7f75-000000",
                         "bounceType": "Permanent",
                         "bounceSubType": "General",
                         "bouncedRecipients": [
                             {
                                 "emailAddress": "bounce@simulator.amazonses.com",
                                 "action": "failed",
                                 "status": "5.1.1",
                                 "diagnosticCode": "smtp; 550 5.1.1 user unknown"
                             }
                         ],
                         "timestamp": "2022-03-05T15:55:54.762Z",
                         "reportingMTA": "dns; a8-62.smtp-out.amazonses.com"
                     },
                     "mail": {
                         "timestamp": "2022-03-05T15:55:53.969Z",
                         "source": "m.ferreira@gresb.com",
                         "sourceArn": "arn:aws:ses:us-east-1:844875565510:identity/gresb.com",
                         "sendingAccountId": "844875565510",
                         "messageId": "0100017f5acb66f1-e509f90c-9c19-4dd6-ae93-bb7a0f7ea93c-000000",
                         "destination": [
                             "bounce@simulator.amazonses.com"
                         ],
                         "headersTruncated": false,
                         "headers": [
                             {
                                 "name": "From",
                                 "value": "m.ferreira@gresb.com"
                             },
                             {
                                 "name": "To",
                                 "value": "bounce@simulator.amazonses.com"
                             },
                             {
                                 "name": "Subject",
                                 "value": "dafasdf"
                             },
                             {
                                 "name": "MIME-Version",
                                 "value": "1.0"
                             },
                             {
                                 "name": "Content-Type",
                                 "value": "multipart/alternative;  boundary=\\"----=_Part_1237092_28065239.1646495753972\\""
                             }
                         ],
                         "commonHeaders": {
                             "from": [
                                 "m.ferreira@gresb.com"
                             ],
                             "to": [
                                 "bounce@simulator.amazonses.com"
                             ],
                             "messageId": "0100017f5acb66f1-e509f90c-9c19-4dd6-ae93-bb7a0f7ea93c-000000",
                             "subject": "dafasdf"
                         },
                         "tags": {
                             "ses:operation": [
                                 "SendEmail"
                             ],
                             "ses:configuration-set": [
                                 "gresb-ses-config"
                             ],
                             "ses:source-ip": [
                                 "45.95.65.150"
                             ],
                             "ses:from-domain": [
                                 "gresb.com"
                             ],
                             "ses:caller-identity": [
                                 "gresb-root-developer-break-glass"
                             ]
                         }
                     }
                 }
                """;

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesBounceMessage.class);
    }

    @Test
    void readComplaintNotification() throws IOException {
        String notification = """
                {
                    "notificationType": "Complaint"
                }
                """;

        final SesMessage sesMessage = deserializationService.readSesMessage(notification);

        assertThat(sesMessage).isInstanceOf(SesComplaintMessage.class);
    }
}
