package ses.notification.lambda.reports;

import ses.notification.lambda.SesMessageMail;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A SES message report.
 *
 * @param <T> The type of the report object.
 */
public interface Report<T> {

    String SUBJECT = "\tSubject: ";
    String FROM = "\tFrom: ";
    String TO = "\tTo: ";
    String CC_AND_BCC = "\tCC and BCC: ";
    String REPLY_TO = "\tReply-To: ";
    String EMAIL_BODY_SEPARATOR = "\n\n";
    List<String> EMPTY_LIST = List.of();

    T build();

    static String getEmailDetails(SesMessageMail mail) {
        final StringBuilder sb = new StringBuilder();
        final SesMessageMail.CommonHeaders commonHeaders = mail.getCommonHeaders();

        sb.append(SUBJECT).append(commonHeaders.getSubject()).append("\n");
        sb.append(FROM).append(joinStrings(commonHeaders.getFrom())).append("\n");
        final List<String> to = readList(commonHeaders.getTo());
        if (!to.isEmpty()) {
            sb.append(TO).append(joinStrings(to)).append("\n");
        }
        final List<String> ccAndBcc = copyList(mail).stream()
                                                    .filter(ccAndBCC -> addressNotIncludedIn(ccAndBCC, to))
                                                    .collect(Collectors.toList());
        if (!ccAndBcc.isEmpty()) {
            sb.append(CC_AND_BCC).append(joinStrings(ccAndBcc)).append("\n");
        }
        final List<String> replyTo = readList(commonHeaders.getReplyTo());
        if (!replyTo.isEmpty()) {
            sb.append(REPLY_TO).append(joinStrings(replyTo)).append("\n");
        }

        return sb.toString();
    }

    private static boolean addressNotIncludedIn(String address, List<String> to) {
        return to.stream().noneMatch(addressWithName -> addressWithName.contains(address));
    }

    private static ArrayList<String> copyList(SesMessageMail mail) {
        return new ArrayList<>(readList(mail.getDestination()));
    }

    private static String joinStrings(List<String> list) {
        return String.join(", ", list);
    }

    private static List<String> readList(List<String> nullableList) {
        return Objects.requireNonNullElse(nullableList, EMPTY_LIST);
    }
}
