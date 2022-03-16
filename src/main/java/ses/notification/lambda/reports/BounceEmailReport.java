package ses.notification.lambda.reports;

import lombok.extern.slf4j.Slf4j;
import ses.notification.lambda.ReportingConfiguration;
import ses.notification.lambda.SesBounceMessage;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class BounceEmailReport extends EmailReport {

    public static final String BOUNCE_REPORT_SUBJECT = "Bounced Email Report";
    public static final String BOUNCE_BODY_HEADER = "The following email bounced:\n\n";
    public static final String BOUNCE_DESCRIPTION_HEADER = "Details of this bounce are:\n\n";
    public static final String BOUNCE_BODY_ADDRESS_LIST_HEADER = "The addresses for which the email bounced are:\n";
    public static final String BOUNCE_TYPE = "\tBounce Type: ";
    public static final String BOUNCED_ADDRESS_ERROR_MESSAGE = ", Error message: ";
    public static final String NA = "NA";

    private String bounceType;
    private String bounceReason;

    public BounceEmailReport(ReportingConfiguration configuration, SesBounceMessage message) {
        super(BOUNCE_REPORT_SUBJECT, configuration.getFrom(), configuration.getTo(), getBounceBody(message));
    }

    private static String getBounceBody(SesBounceMessage notification) {
        final StringBuilder sb = new StringBuilder();
        final SesBounceMessage.Bounce bounce = notification.getBounce();
        sb.append(BOUNCE_BODY_HEADER)
          .append(Report.getEmailDetails(notification.getMail()))
          .append(EMAIL_BODY_SEPARATOR)
          .append(BOUNCE_DESCRIPTION_HEADER)
          .append(BOUNCE_TYPE).append(String.format("%s (%s)", bounce.getBounceType(), bounce.getBounceSubType()))
          .append(EMAIL_BODY_SEPARATOR)
          .append(BOUNCE_BODY_ADDRESS_LIST_HEADER)
          .append(getListOfBouncedAddresses(bounce));

        return sb.toString();
    }

    private static String getListOfBouncedAddresses(SesBounceMessage.Bounce bounce) {
        return bounce.getBouncedRecipients()
                     .stream()
                     .map(BounceEmailReport::buildAddressLine)
                     .collect(Collectors.joining("\n"));
    }

    private static String buildAddressLine(SesBounceMessage.BounceRecipients bounceRecipients) {
        String diagnosticCode = Objects.requireNonNullElse(bounceRecipients.getDiagnosticCode(), NA);
        diagnosticCode = diagnosticCode.isBlank() ? NA : diagnosticCode;
        return "  - Address: " + bounceRecipients.getEmailAddress() + BOUNCED_ADDRESS_ERROR_MESSAGE + diagnosticCode;
    }
}
