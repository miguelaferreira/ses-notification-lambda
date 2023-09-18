package ses.notification.lambda;

import io.micronaut.context.condition.Condition;
import io.micronaut.context.condition.ConditionContext;

import java.util.Optional;

public class DynamoDbConfigurationCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context) {
        final Optional<String> maybeProperty = context.getProperty(OfflineReportingConfiguration.PREFIX + ".backend", String.class);
        if (maybeProperty.isPresent()) {
            final String property = maybeProperty.get();
            try {
                PersistenceBackend.valueOf(property);
                return true;
            } catch (Exception ignored) {
            }
        }

        return false;
    }
}
