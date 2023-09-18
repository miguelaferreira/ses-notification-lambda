package ses.notification.lambda.reports;

import ses.notification.lambda.OnlineReportingConfiguration;
import ses.notification.lambda.aws.SesComplaintMessage;

import java.util.stream.Collectors;

public class ComplaintEmailReport extends EmailReport {

    public static final String COMPLAINT_REPORT_SUBJECT = "Email Complaint Report";
    public static final String COMPLAINT_BODY_HEADER = "Receivers of the following email complained about it:\n\n";
    public static final String COMPLAINT_DESCRIPTION_HEADER = "Details of this complaint are:\n\n";
    public static final String COMPLAINT_BODY_ADDRESS_LIST_HEADER = "The addresses from which we received complaints are:\n";
    public static final String COMPLAINT_TYPE = "\tComplaint Type: ";

    public ComplaintEmailReport(OnlineReportingConfiguration configuration, SesComplaintMessage message) {
        super(COMPLAINT_REPORT_SUBJECT, configuration.from(), configuration.to(), getComplaintBody(message));
    }


    private static String getComplaintBody(SesComplaintMessage message) {
        final StringBuilder sb = new StringBuilder();
        final SesComplaintMessage.Complaint complaint = message.getComplaint();
        sb.append(COMPLAINT_BODY_HEADER)
          .append(Report.getEmailDetails(message.getMail()))
          .append(EMAIL_BODY_SEPARATOR)
          .append(COMPLAINT_DESCRIPTION_HEADER)
          .append(COMPLAINT_TYPE)
          .append((String.format("%s (%s)", complaint.getComplaintFeedbackType(), complaint.getComplaintSubType())))
          .append(EMAIL_BODY_SEPARATOR)
          .append(COMPLAINT_BODY_ADDRESS_LIST_HEADER)
          .append(getListOfComplaintAddresses(complaint));

        return sb.toString();
    }

    private static String getListOfComplaintAddresses(SesComplaintMessage.Complaint complaint) {
        return complaint.getComplainedRecipients()
                        .stream()
                        .map(SesComplaintMessage.ComplaintRecipients::getEmailAddress)
                        .map(address -> "  - " + address)
                        .collect(Collectors.joining("\n"));
    }
}
