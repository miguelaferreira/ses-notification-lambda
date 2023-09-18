package ses.notification.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime;
import lombok.extern.slf4j.Slf4j;
import ses.notification.lambda.aws.SnsEvent;

import java.net.MalformedURLException;

@Slf4j
public class FunctionLambdaRuntime extends AbstractMicronautLambdaRuntime<SnsEvent, String, SnsEvent, String> {

    public static void main(String[] args) {
        try {
            new FunctionLambdaRuntime().run(args);
        } catch (MalformedURLException e) {
            log.error("Error caught in main method", e);
        }
    }

    @Override
    @Nullable
    protected RequestHandler<SnsEvent, String> createRequestHandler(String... args) {
        return new FunctionRequestHandler();
    }
}
