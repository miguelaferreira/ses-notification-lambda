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
public class SesDeliveryMessage implements SesNotification, SesEvent {

    private SesMessageMail mail;
    private Delivery delivery;

    @Data
    @ToString
    @Introspected
    @Serdeable.Deserializable
    public static class Delivery {
        private LocalDateTime timestamp;
        private long processingTimeMillis;
        private List<String> recipients;
        private String smtpResponse;
        private String remoteMtaIp;
        private String reportingMTA;
    }
}
