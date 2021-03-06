package ses.notification.lambda;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.email.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

interface EmailService {
    void send(@NonNull @NotNull @Valid Email email);
}
