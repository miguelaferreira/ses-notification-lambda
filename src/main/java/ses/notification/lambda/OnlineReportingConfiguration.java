package ses.notification.lambda;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(OnlineReportingConfiguration.PREFIX)
public class OnlineReportingConfiguration {

    public static final String PREFIX = "online.reporting";

    private String from;
    private String to;
}
