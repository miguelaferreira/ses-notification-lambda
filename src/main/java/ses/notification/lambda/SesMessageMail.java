package ses.notification.lambda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Introspected
@Serdeable.Deserializable
@JsonIgnoreProperties(ignoreUnknown = true)
public class SesMessageMail {

    private LocalDateTime timestamp;
    private String source;
    private String sourceArn;
    private String sourceIp;
    private String sendingAccountId;
    private String messageId;
    private List<String> destination;
    private boolean headersTruncated;
    private List<Header> headers;
    private CommonHeaders commonHeaders;

    @Data
    @ToString
    @Introspected
    @Serdeable.Deserializable
    static class Header {
        private String name;
        private String value;
    }

    @Data
    @ToString
    @Introspected
    @Serdeable.Deserializable
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommonHeaders {
        private String subject;
        private List<String> from;
        private List<String> to;
        private List<String> replyTo;
    }
}
