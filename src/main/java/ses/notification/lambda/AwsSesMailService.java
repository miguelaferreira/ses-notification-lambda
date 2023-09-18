package ses.notification.lambda;

import io.micronaut.aws.AWSConfiguration;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.email.BodyType;
import io.micronaut.email.Contact;
import io.micronaut.email.Email;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;

import java.util.stream.Collectors;

@Slf4j
@Singleton
@Requires(property = AWSConfiguration.PREFIX + ".region")
public class AwsSesMailService implements EmailService {

    protected final SesClient ses;

    @Inject
    public AwsSesMailService(SesClient ses) {
        this.ses = ses;
    }

    @Override
    // TODO: review double use of not null annotation
    public void send(@NonNull @NotNull @Valid Email email) {
        assert email.getTo() != null;
        assert email.getBody() != null;
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                                                            .destination(Destination.builder()
                                                                                    .toAddresses(email.getTo()
                                                                                                      .stream()
                                                                                                      .map(Contact::getEmail)
                                                                                                      .collect(Collectors.toList()))
                                                                                    .build())
                                                            .source(email.getFrom().getEmail())
                                                            .message(Message.builder()
                                                                            .subject(Content.builder()
                                                                                            .data(email.getSubject())
                                                                                            .build())
                                                                            .body(Body.builder()
                                                                                      .text(Content.builder()
                                                                                                   .data(email.getBody()
                                                                                                              .get(BodyType.TEXT)
                                                                                                              .orElse("Email body missing!"))
                                                                                                   .build())
                                                                                      .build())
                                                                            .build())
                                                            .build();
        SendEmailResponse response = ses.sendEmail(sendEmailRequest);
        log.info("Sent email with id: {}", response.messageId());
    }
}
