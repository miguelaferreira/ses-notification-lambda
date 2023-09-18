package ses.notification.lambda;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Requires(condition = CIAwsRegionProviderChainCondition.class)
@Requires(condition = CIAwsCredentialsProviderChainCondition.class)
@Requires(beans = {DynamoDbConfiguration.class, DynamoDbClient.class})
@Singleton
public class DynamoRepository {

    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbConfiguration dynamoDbConfiguration;

    public DynamoRepository(DynamoDbClient dynamoDbClient,
                            DynamoDbConfiguration dynamoDbConfiguration) {
        this.dynamoDbClient = dynamoDbClient;
        this.dynamoDbConfiguration = dynamoDbConfiguration;
    }
}
