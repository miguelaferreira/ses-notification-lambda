package ses.notification.lambda.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable.Deserializable
public record SnsEvent(@JsonProperty("Records") List<SnsEventRecord> records) {
}
