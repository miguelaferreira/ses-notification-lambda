package ses.notification.lambda;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import jakarta.validation.constraints.NotBlank;

@Requires(property = DynamoDbConfiguration.PREFIX + ".table-name")
@Requires(condition = DynamoDbConfigurationCondition.class)
@ConfigurationProperties(DynamoDbConfiguration.PREFIX)
public record DynamoDbConfiguration(@NotBlank String tableName) {
    public static final String PREFIX = "dynamodb";
}
