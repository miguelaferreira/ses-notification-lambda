package ses.notification.lambda;

import io.micronaut.email.Email;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import ses.notification.lambda.aws.SesBounceMessage;
import ses.notification.lambda.aws.SesComplaintMessage;
import ses.notification.lambda.aws.SesDeliveryMessage;
import ses.notification.lambda.aws.SesMessage;
import ses.notification.lambda.reports.BounceEmailReport;
import ses.notification.lambda.reports.ComplaintEmailReport;
import ses.notification.lambda.reports.DeliveryEmailReport;
import ses.notification.lambda.reports.ReportingService;

@Singleton
public class EmailReportingService implements ReportingService<Email> {

    private final OnlineReportingConfiguration configuration;

    @Inject
    public EmailReportingService(OnlineReportingConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Email report(SesMessage message) {
        if (message instanceof SesBounceMessage) {
            return new BounceEmailReport(configuration, (SesBounceMessage) message).build();
        } else if (message instanceof SesComplaintMessage) {
            return new ComplaintEmailReport(configuration, (SesComplaintMessage) message).build();
        } else if (message instanceof SesDeliveryMessage) {
            return new DeliveryEmailReport(configuration, (SesDeliveryMessage) message).build();
        } else {
            throw new IllegalArgumentException("Cannot handle SES Message of type " + message.getClass());
        }
    }
}
