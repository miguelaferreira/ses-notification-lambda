package ses.notification.lambda;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.exceptions.DependencyInjectionException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ses.notification.lambda.PersistenceBackend.DYNAMO_DB;

@MicronautTest(rebuildContext = true)
class OfflineReportingConfigurationTest {

    public static final String FROM_EMAIL_COM = "from@email.com";
    public static final String TO_EMAIL_COM = "to@email.com";
    public static final String DYNAMO_DB_STRING = "dynamoDB";
    public static final String INVALID_BACKEND = "mysql";

    @Inject
    ApplicationContext context;

    @Test
    @Property(name = "offline.reporting.from", value = FROM_EMAIL_COM)
    @Property(name = "offline.reporting.to", value = TO_EMAIL_COM)
    @Property(name = "offline.reporting.backend", value = DYNAMO_DB_STRING)
    void allExpectedPropertiesSet(OfflineReportingConfiguration config) {
        assertThat(config).isNotNull();
        assertThat(config.from()).isEqualTo(FROM_EMAIL_COM);
        assertThat(config.to()).isEqualTo(TO_EMAIL_COM);
        assertThat(config.backend()).isEqualTo(DYNAMO_DB);
    }

    @Test
    @Property(name = "offline.reporting.to", value = TO_EMAIL_COM)
    @Property(name = "offline.reporting.backend", value = DYNAMO_DB_STRING)
    void missingFromProperty() {
        assertThatThrownBy(() -> context.getBean(OfflineReportingConfiguration.class))
                .isInstanceOf(DependencyInjectionException.class)
                .hasMessageContaining("Message: Error resolving property value [offline.reporting.from]. Property doesn't exist");
    }

    @Test
    @Property(name = "offline.reporting.from", value = FROM_EMAIL_COM)
    @Property(name = "offline.reporting.backend", value = DYNAMO_DB_STRING)
    void missingToProperty() {
        assertThatThrownBy(() -> context.getBean(OfflineReportingConfiguration.class))
                .isInstanceOf(DependencyInjectionException.class)
                .hasMessageContaining("Message: Error resolving property value [offline.reporting.to]. Property doesn't exist");
    }

    @Test
    @Property(name = "offline.reporting.from", value = FROM_EMAIL_COM)
    @Property(name = "offline.reporting.to", value = TO_EMAIL_COM)
    void missingBackendProperty() {
        assertThatThrownBy(() -> context.getBean(OfflineReportingConfiguration.class))
                .isInstanceOf(DependencyInjectionException.class)
                .hasMessageContaining("Message: Error resolving property value [offline.reporting.backend]. Property doesn't exist");
    }

    @Test
    @Property(name = "offline.reporting.from", value = FROM_EMAIL_COM)
    @Property(name = "offline.reporting.to", value = TO_EMAIL_COM)
    @Property(name = "offline.reporting.backend", value = INVALID_BACKEND)
    void invalidBackendProperty() {
        assertThatThrownBy(() -> context.getBean(OfflineReportingConfiguration.class))
                .isInstanceOf(DependencyInjectionException.class)
                .hasMessageContaining("Message: Error resolving property value [offline.reporting.backend]. Unable to convert value [" + INVALID_BACKEND + "] to target type [PersistenceBackend]");
    }
}
