package ses.notification.lambda;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Introspected
class SesComplaintMessage implements SesNotification, SesEvent {

    private Complaint complaint;
    private SesMessageMail mail;

    @Data
    @ToString
    @Introspected
    static class Complaint {
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
    static class ComplaintRecipients {
        private String emailAddress;
    }
}
