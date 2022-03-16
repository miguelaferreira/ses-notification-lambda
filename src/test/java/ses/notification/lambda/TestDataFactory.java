package ses.notification.lambda;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class TestDataFactory {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String TEST_1_EMAIL_COM = "test1@email.com";
    public static final String TEST_1_EMAIL_COM_WITH_NAME = String.format("Test 1 <%s>", TEST_1_EMAIL_COM);
    public static final String TEST_2_EMAIL_COM = "test2@email.com";
    public static final String TEST_2_EMAIL_COM_WITH_NAME = String.format("Test 2 <%s>", TEST_2_EMAIL_COM);
    public static final String TEST_SUBJECT = "Test subject";
    public static final String FROM_1_EMAIL_COM = "from1@email.com";
    public static final String FROM_2_EMAIL_COM = "from2@email.com";
    public static final String REPLY_TO_2_EMAIL_COM = "replyto2@email.com";
    public static final String REPLY_TO_1_EMAIL_COM = "replyto1@email.com";
    public static final String THE_ACCOUNT_DOES_NOT_EXIST = "The account does not exist.";

    static SNSEvent snsEvent(SesNotification message) throws JsonProcessingException {
        final SNSEvent event = new SNSEvent();
        final SNSEvent.SNSRecord record = new SNSEvent.SNSRecord();
        final SNSEvent.SNS sns = new SNSEvent.SNS();
        sns.setMessage(MAPPER.writeValueAsString(message));
        record.setSns(sns);
        event.setRecords(List.of(record));

        return event;
    }

    static SesBounceMessage bounceNotification() {
        final SesBounceMessage notification = new SesBounceMessage();
        final SesBounceMessage.Bounce bounce = new SesBounceMessage.Bounce();
        bounce.setBouncedRecipients(List.of(
                SesBounceMessage.BounceRecipients.builder()
                                                 .emailAddress(TEST_1_EMAIL_COM)
                                                 .build(),
                SesBounceMessage.BounceRecipients.builder()
                                                 .emailAddress(TEST_2_EMAIL_COM)
                                                 .build()
        ));
        notification.setBounce(bounce);
        final SesMessageMail mail = new SesMessageMail();
        final SesMessageMail.CommonHeaders commonHeaders = new SesMessageMail.CommonHeaders();
        commonHeaders.setSubject(TEST_SUBJECT);
        commonHeaders.setFrom(List.of(FROM_1_EMAIL_COM, FROM_2_EMAIL_COM));
        commonHeaders.setReplyTo(List.of(REPLY_TO_1_EMAIL_COM, REPLY_TO_2_EMAIL_COM));
        mail.setCommonHeaders(commonHeaders);
        notification.setMail(mail);

        return notification;
    }

    static SesBounceMessage bounceNotificationMultipleToAddresses() {
        final SesBounceMessage message = new SesBounceMessage();
        final SesBounceMessage.Bounce bounce = new SesBounceMessage.Bounce();
        bounce.setBounceType("Permanent");
        bounce.setBounceSubType("General");
        bounce.setBouncedRecipients(List.of(
                SesBounceMessage.BounceRecipients.builder()
                                                 .emailAddress(TEST_1_EMAIL_COM)
                                                 .action("failed")
                                                 .status("5.1.1")
                                                 .diagnosticCode(THE_ACCOUNT_DOES_NOT_EXIST)
                                                 .build()
        ));
        message.setBounce(bounce);
        final SesMessageMail mail = new SesMessageMail();
        mail.setDestination(List.of(TEST_1_EMAIL_COM, TEST_2_EMAIL_COM));
        final SesMessageMail.CommonHeaders commonHeaders = new SesMessageMail.CommonHeaders();
        commonHeaders.setSubject(TEST_SUBJECT);
        commonHeaders.setFrom(List.of(FROM_1_EMAIL_COM, FROM_2_EMAIL_COM));
        commonHeaders.setTo(List.of(TEST_1_EMAIL_COM_WITH_NAME));
        commonHeaders.setReplyTo(List.of(REPLY_TO_1_EMAIL_COM, REPLY_TO_2_EMAIL_COM));
        mail.setCommonHeaders(commonHeaders);
        message.setMail(mail);

        return message;
    }

    static SesBounceMessage bounceNotificationMultipleToAddressesNoDiagnosticsCode() {
        final SesBounceMessage message = new SesBounceMessage();
        final SesBounceMessage.Bounce bounce = new SesBounceMessage.Bounce();
        bounce.setBounceType("Permanent");
        bounce.setBounceSubType("General");
        bounce.setBouncedRecipients(List.of(
                SesBounceMessage.BounceRecipients.builder()
                                                 .emailAddress(TEST_1_EMAIL_COM)
                                                 .build()
        ));
        message.setBounce(bounce);
        final SesMessageMail mail = new SesMessageMail();
        mail.setDestination(List.of(TEST_1_EMAIL_COM, TEST_2_EMAIL_COM));
        final SesMessageMail.CommonHeaders commonHeaders = new SesMessageMail.CommonHeaders();
        commonHeaders.setSubject(TEST_SUBJECT);
        commonHeaders.setFrom(List.of(FROM_1_EMAIL_COM, FROM_2_EMAIL_COM));
        commonHeaders.setTo(List.of(TEST_1_EMAIL_COM_WITH_NAME));
        commonHeaders.setReplyTo(List.of(REPLY_TO_1_EMAIL_COM, REPLY_TO_2_EMAIL_COM));
        mail.setCommonHeaders(commonHeaders);
        message.setMail(mail);

        return message;
    }
}
