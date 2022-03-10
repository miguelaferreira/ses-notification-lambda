package ses.notification.lambda;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(ReportingConfiguration.PREFIX)
public class ReportingConfiguration {

    public static final String PREFIX = "reporting";

    private String from;
    private String to;
}
