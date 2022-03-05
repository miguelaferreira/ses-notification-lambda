package ses.notification.lambda;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Introspected
class SesDeliveryMessage implements SesNotification, SesEvent {

    private SesMessageMail mail;
    private Delivery delivery;

    @Data
    @ToString
    @Introspected
    static class Delivery {
        private LocalDateTime timestamp;
        private long processingTimeMillis;
        private List<String> recipients;
        private String smtpResponse;
        private String remoteMtaIp;
        private String reportingMTA;
    }
}
