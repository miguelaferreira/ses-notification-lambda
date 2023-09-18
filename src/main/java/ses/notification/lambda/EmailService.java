package ses.notification.lambda;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.email.Email;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

interface EmailService {

    // TODO: review double use of not null annotation
    void send(@NonNull @NotNull @Valid Email email);
}
