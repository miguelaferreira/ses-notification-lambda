package ses.notification.lambda;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
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
@Serdeable.Deserializable
public class SesBounceMessage implements SesNotification, SesEvent {

    private Bounce bounce;
    private SesMessageMail mail;

    @Data
    @ToString
    @Introspected
    @Serdeable.Deserializable
    public static class Bounce {
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
    @Introspected
    @NoArgsConstructor
    @AllArgsConstructor
    @Serdeable.Deserializable
    @Builder(builderClassName = "Builder")
    public static class BounceRecipients {
        private String emailAddress;
        private String action;
        private String status;
        private String diagnosticCode;
    }
}
