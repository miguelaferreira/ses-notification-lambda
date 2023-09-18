package ses.notification.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ses.notification.lambda.aws.SesBounceMessage;
import ses.notification.lambda.aws.SnsEvent;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionRequestHandlerTest {

    private static FunctionRequestHandler requestHandler;

    @BeforeAll
    public static void setupServer() {
        final OnlineReportingConfiguration configuration = new OnlineReportingConfiguration();
        configuration.setTo("to@email.com");
        configuration.setFrom("from@email.com");
        requestHandler = new FunctionRequestHandler();
        requestHandler.setEmailService(new MockEmailService());
        requestHandler.setNotificationReportingService(new EmailReportingService(configuration));
    }

    @AfterAll
    public static void stopServer() {
        if (requestHandler != null) {
            requestHandler.getApplicationContext().close();
        }
    }

    @Test
    public void testHandler() throws JsonProcessingException {
        final SesBounceMessage notification = TestDataFactory.bounceNotification();
        final SnsEvent event = TestDataFactory.snsEvent(notification);

        String result = requestHandler.execute(event);
        assertThat(result).isEqualTo("Done");
    }
}
