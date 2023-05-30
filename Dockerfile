FROM adoptopenjdk/openjdk17:alpine-jre

ENV GRADLE_VERSION=7.4.2

# Устанавливаем Gradle
RUN apk add --no-cache curl \
    && curl -L -O "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" \
    && unzip "gradle-${GRADLE_VERSION}-bin.zip" -d /opt \
    && rm "gradle-${GRADLE_VERSION}-bin.zip"

ENV PATH="/opt/gradle-${GRADLE_VERSION}/bin:${PATH}"

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN gradle clean build

ENTRYPOINT ["java", "-jar", "/build/libs/library-1.0-SNAPSHOT.jar"]
