package ses.notification.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
class DeserializationService {

    private final ObjectMapper objectMapper;

    DeserializationService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();
    }

    SesMessage readSesMessage(String value) throws JsonProcessingException {
        try {
            log.debug("Trying to deserialize SES event");
            return readSesEvent(value);
        } catch (JsonProcessingException e) {
            log.debug("Wasn't able to deserialize SES event", e);
            log.debug("Trying to deserialize SES notification");
            return readSesNotification(value);
        }
    }

    private SesNotification readSesNotification(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, SesNotification.class);
    }

    private SesEvent readSesEvent(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, SesEvent.class);
    }
}
