FROM amazoncorretto:17-alpine3.18

COPY build/libs/ses-notification-lambda-test-all.jar /home/not-root/application.jar

# Configure non-root user
RUN addgroup -g 3000 not-root \
    && adduser -u 1000 -s /bin/false -D -G not-root not-root
USER not-root

ENTRYPOINT ["java", "-jar", "/home/not-root/application.jar"]
