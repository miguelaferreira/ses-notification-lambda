package ses.notification.lambda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Introspected
@JsonIgnoreProperties(ignoreUnknown = true)
class SesMessageMail {

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
    static class Header {
        private String name;
        private String value;
    }

    @Data
    @ToString
    @Introspected
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class CommonHeaders {
        private String subject;
        private List<String> from;
        private List<String> to;
        private List<String> replyTo;
    }
}
