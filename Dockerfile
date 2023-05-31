FROM adoptopenjdk/openjdk17:alpine-jre

WORKDIR /app

COPY . .

RUN ./gradlew clean build
RUN cp build/libs/library-1.0-SNAPSHOT.jar /app/library.jar

ENTRYPOINT ["java", "-jar", "/app/library.jar"]
