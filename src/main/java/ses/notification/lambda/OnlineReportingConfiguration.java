package ses.notification.lambda;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties(OnlineReportingConfiguration.PREFIX)
public record OnlineReportingConfiguration(String from, String to) {

    public static final String PREFIX = "online.reporting";
}
