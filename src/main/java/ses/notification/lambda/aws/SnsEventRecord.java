package ses.notification.lambda.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable.Deserializable
public record SnsEventRecord(@JsonProperty("Sns") Sns sns) {
}
