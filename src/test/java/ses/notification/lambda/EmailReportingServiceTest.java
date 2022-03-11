package ses.notification.lambda;

import io.micronaut.email.BodyType;
import io.micronaut.email.Email;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ses.notification.lambda.TestDataFactory.REPLY_TO_1_EMAIL_COM;
import static ses.notification.lambda.TestDataFactory.REPLY_TO_2_EMAIL_COM;
import static ses.notification.lambda.TestDataFactory.TEST_1_EMAIL_COM;
import static ses.notification.lambda.TestDataFactory.TEST_1_EMAIL_COM_WITH_NAME;
import static ses.notification.lambda.TestDataFactory.TEST_2_EMAIL_COM;
import static ses.notification.lambda.reports.BounceEmailReport.BOUNCE_BODY_ADDRESS_LIST_HEADER;
import static ses.notification.lambda.reports.BounceEmailReport.BOUNCE_BODY_HEADER;
import static ses.notification.lambda.reports.BounceEmailReport.BOUNCE_DESCRIPTION_HEADER;
import static ses.notification.lambda.reports.BounceEmailReport.BOUNCE_REPORT_SUBJECT;
import static ses.notification.lambda.reports.Report.CC_AND_BCC;
import static ses.notification.lambda.reports.Report.REPLY_TO;
import static ses.notification.lambda.reports.Report.TO;

@MicronautTest
class EmailReportingServiceTest {

    @Inject
    private EmailReportingService service;

    @Test
    void reportBounce() {
        final Email email = service.report(TestDataFactory.bounceNotification());

        assertThat(email).isNotNull();
        assertThat(email.getSubject()).isEqualTo(BOUNCE_REPORT_SUBJECT);
        assertThat(email.getBody().get(BodyType.TEXT)).isPresent();
        assertThat(email.getBody().get(BodyType.TEXT).get()).contains(BOUNCE_BODY_HEADER)
                                                            .contains(BOUNCE_BODY_ADDRESS_LIST_HEADER)
                                                            .contains(TEST_1_EMAIL_COM)
                                                            .contains(TEST_2_EMAIL_COM)
                                                            .contains(REPLY_TO_1_EMAIL_COM)
                                                            .contains(REPLY_TO_2_EMAIL_COM);
    }

    @Test
    void reportBounce_withMultipleAddresses() {
        final Email email = service.report(TestDataFactory.bounceNotificationMultipleToAddresses());

        assertThat(email).isNotNull();
        assertThat(email.getSubject()).isEqualTo(BOUNCE_REPORT_SUBJECT);
        assertThat(email.getBody().get(BodyType.TEXT)).isPresent();
        assertThat(email.getBody().get(BodyType.TEXT).get()).contains(BOUNCE_BODY_HEADER)
                                                            .contains(BOUNCE_BODY_ADDRESS_LIST_HEADER)
                                                            .contains(BOUNCE_DESCRIPTION_HEADER)
                                                            .contains(TO + TEST_1_EMAIL_COM_WITH_NAME)
                                                            .contains(CC_AND_BCC + TEST_2_EMAIL_COM)
                                                            .contains(REPLY_TO + REPLY_TO_1_EMAIL_COM + ", " + REPLY_TO_2_EMAIL_COM)
                                                            .contains("Bounce Type: Permanent (General)");
    }


}
