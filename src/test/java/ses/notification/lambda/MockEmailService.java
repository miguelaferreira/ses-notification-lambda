package ses.notification.lambda;

import io.micronaut.email.Email;

public class MockEmailService implements EmailService {

    @Override
    public void send(Email email) {
        System.out.println("Sending email = " + email.toString());
    }
}
