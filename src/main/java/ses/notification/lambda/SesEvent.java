package ses.notification.lambda;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Introspected
@Serdeable.Deserializable
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "eventType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SesDeliveryMessage.class, name = "Delivery"),
        @JsonSubTypes.Type(value = SesBounceMessage.class, name = "Bounce"),
        @JsonSubTypes.Type(value = SesComplaintMessage.class, name = "Complaint")
})
interface SesEvent extends SesMessage {
}
