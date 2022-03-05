package ses.notification.lambda;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Introspected
class SesBounceMessage implements SesNotification, SesEvent {

    private Bounce bounce;
    private SesMessageMail mail;

    @Data
    @ToString
    @Introspected
    static class Bounce {
        private LocalDateTime timestamp;
        private String feedbackId;
        private String bounceType;
        private String bounceSubType;
        private List<BounceRecipients> bouncedRecipients;
        private String remoteMtaIp;
        private String reportingMTA;
    }

    @Data
    @ToString
    @Builder(builderClassName = "Builder")
    @NoArgsConstructor
    @AllArgsConstructor
    @Introspected
    static class BounceRecipients {
        private String emailAddress;
        private String action;
        private String status;
        private String diagnosticCode;
    }
}
