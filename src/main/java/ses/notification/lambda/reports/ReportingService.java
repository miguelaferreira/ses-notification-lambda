package ses.notification.lambda.reports;

import ses.notification.lambda.SesMessage;

/**
 * A service that produces reports off type {@code T} for a given {@code SesMessage}.
 *
 * @param <T> The type of the report object.
 */
public interface ReportingService<T> {

    T report(SesMessage message);
}
