package ses.notification.lambda;

import io.micronaut.context.annotation.Requires;
import io.micronaut.email.Email;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
@Requires(property = ReportingConfiguration.PREFIX + ".from")
@Requires(property = ReportingConfiguration.PREFIX + ".to")
class ReportingService {

    public static final String BOUNCE_REPORT_SUBJECT = "Bounced Email Report";
    public static final String BOUNCE_BODY_HEADER = "The following email bounced:\n\n";
    public static final String BOUNCE_BODY_ADDRESS_LIST_HEADER = "The addresses for which the email bounced are:\n";
    public static final String COMPLAINT_REPORT_SUBJECT = "Email Complaint Report";
    public static final String COMPLAINT_BODY_HEADER = "Receivers of the following email complained about it:\n\n";
    public static final String COMPLAINT_BODY_ADDRESS_LIST_HEADER = "The addresses from which we received complaints are:\n";
    public static final String DELIVERY_REPORT_SUBJECT = "Email Delivery Report";
    public static final String DELIVERY_BODY_HEADER = "The following email was delivered:\n\n";
    public static final String DELIVERY_BODY_ADDRESS_LIST_HEADER = "The inbox that received it are:\n";
    public static final String EMAIL_BODY_SEPARATOR = "\n\n";
    public static final String SUBJECT = "\tSubject: ";
    public static final String FROM = "\tFrom: ";
    public static final String REPLY_TO = "\tReply-To: ";

    private final String fromAddress;
    private final String toAddress;

    public ReportingService(ReportingConfiguration configuration) {
        this.fromAddress = configuration.getFrom();
        this.toAddress = configuration.getTo();
    }

    Email report(SesMessage notification) {
        if (notification instanceof SesBounceMessage) {
            return report((SesBounceMessage) notification);
        } else if (notification instanceof SesComplaintMessage) {
            return report((SesComplaintMessage) notification);
        } else if (notification instanceof SesDeliveryMessage) {
            return report((SesDeliveryMessage) notification);
        } else {
            throw new IllegalArgumentException("Cannot handle SES Notification fo type " + notification.getClass());
        }
    }

    Email report(SesBounceMessage notification) {
        log.info("Creating SES Bounce report email");
        return Email.builder()
                    .to(toAddress)
                    .from(fromAddress)
                    .subject(BOUNCE_REPORT_SUBJECT)
                    .body(getBounceBody(notification))
                    .build();
    }

    Email report(SesComplaintMessage notification) {
        log.info("Creating SES Complaint report email");
        return Email.builder()
                    .to(toAddress)
                    .from(fromAddress)
                    .subject(COMPLAINT_REPORT_SUBJECT)
                    .body(getComplaintBody(notification))
                    .build();
    }

    Email report(SesDeliveryMessage notification) {
        log.info("Creating SES Delivery report email");
        return Email.builder()
                    .to(toAddress)
                    .from(fromAddress)
                    .subject(DELIVERY_REPORT_SUBJECT)
                    .body(getDeliveryBody(notification))
                    .build();
    }

    private String getBounceBody(SesBounceMessage notification) {
        final StringBuilder sb = new StringBuilder();
        sb.append(BOUNCE_BODY_HEADER)
          .append(getEmailDetails(notification.getMail()))
          .append(EMAIL_BODY_SEPARATOR)
          .append(BOUNCE_BODY_ADDRESS_LIST_HEADER)
          .append(getListOfBouncedAddresses(notification));

        return sb.toString();
    }

    private String getComplaintBody(SesComplaintMessage notification) {
        final StringBuilder sb = new StringBuilder();
        sb.append(COMPLAINT_BODY_HEADER)
          .append(getEmailDetails(notification.getMail()))
          .append(EMAIL_BODY_SEPARATOR)
          .append(COMPLAINT_BODY_ADDRESS_LIST_HEADER)
          .append(getListOfComplaintAddresses(notification));

        return sb.toString();
    }

    private String getDeliveryBody(SesDeliveryMessage notification) {
        final StringBuilder sb = new StringBuilder();
        sb.append(DELIVERY_BODY_HEADER)
          .append(getEmailDetails(notification.getMail()))
          .append(EMAIL_BODY_SEPARATOR)
          .append(DELIVERY_BODY_ADDRESS_LIST_HEADER)
          .append(getListOfDeliveryAddresses(notification));

        return sb.toString();
    }

    private String getEmailDetails(SesMessageMail mail) {
        final StringBuilder sb = new StringBuilder();
        final SesMessageMail.CommonHeaders commonHeaders = mail.getCommonHeaders();

        sb.append(SUBJECT).append(commonHeaders.getSubject()).append("\n");
        sb.append(FROM).append(joinStrings(commonHeaders.getFrom())).append("\n");
        final List<String> replyTo = commonHeaders.getReplyTo();
        if (replyTo != null) {
            sb.append(REPLY_TO).append(joinStrings(replyTo)).append("\n");
        }

        return sb.toString();
    }

    private String joinStrings(List<String> list) {
        return String.join(", ", list);
    }

    private String getListOfBouncedAddresses(SesBounceMessage notification) {
        return notification.getBounce()
                           .getBouncedRecipients()
                           .stream()
                           .map(SesBounceMessage.BounceRecipients::getEmailAddress)
                           .map(address -> "  - " + address)
                           .collect(Collectors.joining("\n"));
    }

    private String getListOfComplaintAddresses(SesComplaintMessage notification) {
        return notification.getComplaint()
                           .getComplainedRecipients()
                           .stream()
                           .map(SesComplaintMessage.ComplaintRecipients::getEmailAddress)
                           .map(address -> "  - " + address)
                           .collect(Collectors.joining("\n"));
    }

    private String getListOfDeliveryAddresses(SesDeliveryMessage notification) {
        return notification.getDelivery()
                           .getRecipients()
                           .stream()
                           .map(address -> "  - " + address)
                           .collect(Collectors.joining("\n"));
    }
}
