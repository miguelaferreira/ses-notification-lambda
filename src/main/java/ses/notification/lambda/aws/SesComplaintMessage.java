package ses.notification.lambda.aws;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.ToString;
import ses.notification.lambda.SesEvent;
import ses.notification.lambda.SesMessageMail;
import ses.notification.lambda.SesNotification;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Introspected
@Serdeable.Deserializable
public class SesComplaintMessage implements SesNotification, SesEvent {

    private Complaint complaint;
    private SesMessageMail mail;

    @Data
    @ToString
    @Introspected
    @Serdeable.Deserializable
    public static class Complaint {
        private LocalDateTime timestamp;
        private String feedbackId;
        private String complaintSubType;
        private List<ComplaintRecipients> complainedRecipients;
        private String userAgent;
        private String complaintFeedbackType;
        private LocalDateTime arrivalDate;
    }

    @Data
    @ToString
    @Introspected
    @Serdeable.Deserializable
    public static class ComplaintRecipients {
        private String emailAddress;
    }
}
