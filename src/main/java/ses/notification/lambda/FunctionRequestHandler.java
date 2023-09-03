package ses.notification.lambda;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.email.Email;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import ses.notification.lambda.aws.SesMessage;
import ses.notification.lambda.aws.SnsEvent;
import ses.notification.lambda.reports.ReportingService;

import java.io.IOException;

@Slf4j
@Introspected
public class FunctionRequestHandler extends MicronautRequestHandler<SnsEvent, String> {

    @Inject
    private EmailService emailService;
    @Inject
    private ReportingService<Email> reportingService;
    @Inject
    DeserializationService deserializationService;

    @Override
    public String execute(SnsEvent event) {
        log.debug("Deserialized SNSEvent: {}", event);
        final String message = event.records().get(0).sns().message();
        try {
            final SesMessage sesMessage = deserializationService.readSesMessage(message);
            emailService.send(reportingService.report(sesMessage));
        } catch (IOException e) {
            log.error("Error deserializing event payload. Payload = " + message, e);
        }
        return "Done";
    }

    void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    void setNotificationReportingService(ReportingService<Email> reportingService) {
        this.reportingService = reportingService;
    }
}
