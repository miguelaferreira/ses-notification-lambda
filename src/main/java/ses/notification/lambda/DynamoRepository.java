package ses.notification.lambda;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Requires(condition = CIAwsRegionProviderChainCondition.class)
@Requires(condition = CIAwsCredentialsProviderChainCondition.class)
@Requires(beans = {DynamoConfiguration.class, DynamoDbClient.class})
@Singleton
public class DynamoRepository {

    private final DynamoDbClient dynamoDbClient;
    private final DynamoConfiguration dynamoConfiguration;

    public DynamoRepository(DynamoDbClient dynamoDbClient,
                            DynamoConfiguration dynamoConfiguration) {
        this.dynamoDbClient = dynamoDbClient;
        this.dynamoConfiguration = dynamoConfiguration;
    }
}
