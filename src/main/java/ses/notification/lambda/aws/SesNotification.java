package ses.notification.lambda.aws;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.micronaut.core.annotation.Introspected;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Introspected
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "notificationType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SesDeliveryMessage.class, name = "Delivery"),
        @JsonSubTypes.Type(value = SesBounceMessage.class, name = "Bounce"),
        @JsonSubTypes.Type(value = SesComplaintMessage.class, name = "Complaint")
})
public interface SesNotification extends SesMessage {
}
