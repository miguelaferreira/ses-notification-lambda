package ses.notification.lambda;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ses.notification.lambda.aws.SesEvent;
import ses.notification.lambda.aws.SesMessage;
import ses.notification.lambda.aws.SesNotification;

import java.io.IOException;

@Slf4j
@Singleton
@SerdeImport(SesMessage.class)
class DeserializationService {

    private final ObjectMapper objectMapper;

    DeserializationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    SesMessage readSesMessage(String value) throws IOException {
        try {
            log.debug("Trying to deserialize SES event");
            return readSesEvent(value);
        } catch (IOException e) {
            log.debug("Wasn't able to deserialize SES event", e);
            log.debug("Trying to deserialize SES notification");
            return readSesNotification(value);
        }
    }

    private SesNotification readSesNotification(String value) throws IOException {
        return objectMapper.readValue(value, SesNotification.class);
    }

    private SesEvent readSesEvent(String value) throws IOException {
        return objectMapper.readValue(value, SesEvent.class);
    }
}
