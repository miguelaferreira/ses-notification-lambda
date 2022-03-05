package ses.notification.lambda;

import io.micronaut.email.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Testcontainers
class AwsSesMailServiceTest {

    private static final DockerImageName LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack:0.14");

    @Container
    private static final LocalStackContainer localstack = new LocalStackContainer(LOCALSTACK_IMAGE).withServices(LocalStackContainer.Service.SES);

    private final SesClient sesClient = SesClient.builder()
                                                 .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.SES))
                                                 .credentialsProvider(StaticCredentialsProvider.create(
                                                         AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())
                                                 ))
                                                 .region(Region.of(localstack.getRegion()))
                                                 .build();

    private final ReportingConfiguration reportingConfiguration = new ReportingConfiguration();
    private final AwsSesMailService sesMailService = new AwsSesMailService(sesClient);

    @BeforeEach
    void setUpAll() {
        final String from = "from@email.com";
        final String to = "to@email.com";
        reportingConfiguration.setFrom(from);
        reportingConfiguration.setTo(to);
        sesClient.verifyEmailAddress(request -> request.emailAddress(from));
        sesClient.verifyEmailAddress(request -> request.emailAddress(to));
    }

    @Test
    void sendMail() {
        final Email build = Email.builder()
                                 .from(reportingConfiguration.getFrom())
                                 .to(reportingConfiguration.getTo())
                                 .subject("Test email")
                                 .body("This is a test!")
                                 .build();
        sesMailService.send(build);
    }
}
