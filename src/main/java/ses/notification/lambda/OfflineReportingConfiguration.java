package ses.notification.lambda;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@Requires(OfflineReportingConfiguration.PREFIX)
@ConfigurationProperties(OfflineReportingConfiguration.PREFIX)
public record OfflineReportingConfiguration(String from, String to, PersistenceBackend backend) {

    public static final String PREFIX = "offline.reporting";
}
