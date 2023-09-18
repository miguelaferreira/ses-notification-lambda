package ses.notification.lambda.reports;

import ses.notification.lambda.OnlineReportingConfiguration;
import ses.notification.lambda.aws.SesDeliveryMessage;

import java.util.stream.Collectors;

public class DeliveryEmailReport extends EmailReport {

    public static final String DELIVERY_REPORT_SUBJECT = "Email Delivery Report";
    public static final String DELIVERY_BODY_HEADER = "The following email was delivered:\n\n";
    public static final String DELIVERY_BODY_ADDRESS_LIST_HEADER = "The inbox that received it are:\n";

    public DeliveryEmailReport(OnlineReportingConfiguration configuration, SesDeliveryMessage message) {
        super(DELIVERY_REPORT_SUBJECT, configuration.getFrom(), configuration.getTo(), getDeliveryBody(message));
    }

    private static String getDeliveryBody(SesDeliveryMessage message) {
        final StringBuilder sb = new StringBuilder();
        sb.append(DELIVERY_BODY_HEADER)
          .append(Report.getEmailDetails(message.getMail()))
          .append(EMAIL_BODY_SEPARATOR)
          .append(DELIVERY_BODY_ADDRESS_LIST_HEADER)
          .append(getListOfDeliveryAddresses(message.getDelivery()));

        return sb.toString();
    }

    private static String getListOfDeliveryAddresses(SesDeliveryMessage.Delivery delivery) {
        return delivery.getRecipients()
                       .stream()
                       .map(address -> "  - " + address)
                       .collect(Collectors.joining("\n"));
    }
}
