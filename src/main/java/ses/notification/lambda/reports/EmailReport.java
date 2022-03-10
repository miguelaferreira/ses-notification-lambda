package ses.notification.lambda.reports;

import io.micronaut.email.Email;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class EmailReport implements Report<Email> {

    protected final String subject;
    protected final String toAddress;
    protected final String fromAddress;
    protected final String body;

    public EmailReport(String subject, String fromAddress, String toAddress, String body) {
        this.subject = subject;
        this.toAddress = toAddress;
        this.fromAddress = fromAddress;
        this.body = body;
    }

    public Email build() {
        log.info("Building report email");
        return Email.builder()
                    .to(toAddress)
                    .from(fromAddress)
                    .subject(subject)
                    .body(body)
                    .build();
    }
}
