package ses.notification.lambda;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHandlerTest {

    private static RequestHandler requestHandler;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @BeforeAll
    public static void setupServer() {
        final ReportingConfiguration configuration = new ReportingConfiguration();
        configuration.setTo("to@email.com");
        configuration.setFrom("from@email.com");
        requestHandler = new RequestHandler();
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
        final SNSEvent event = TestDataFactory.snsEvent(notification);

        String result = requestHandler.execute(event);
        assertThat(result).isEqualTo("Done");
    }
}
