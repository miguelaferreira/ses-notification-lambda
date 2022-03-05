package ses.notification.lambda;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Introspected
public class RequestHandler extends MicronautRequestHandler<SNSEvent, String> {

    private static final DeserializationService DESERIALIZATION_SERVICE = new DeserializationService();

    @Inject
    private EmailService emailService;
    @Inject
    private ReportingService reportingService;

    @Override
    public String execute(SNSEvent event) {
        final String message = event.getRecords().get(0).getSNS().getMessage();
        try {
            final SesMessage sesMessage = DESERIALIZATION_SERVICE.readSesMessage(message);
            emailService.send(reportingService.report(sesMessage));
        } catch (JsonProcessingException e) {
            log.error("Error deserializing event payload. Payload = " + message, e);
        }
        return "Done";
    }

    void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    void setNotificationReportingService(ReportingService reportingService) {
        this.reportingService = reportingService;
    }
}
