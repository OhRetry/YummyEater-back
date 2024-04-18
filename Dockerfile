FROM amazoncorretto:16-alpine as builder
WORKDIR /app

COPY ./build.gradle ./settings.gradle ./gradlew ./
COPY ./gradle ./gradle
RUN dos2unix gradlew
RUN ./gradlew build -x test --parallel --continue > /dev/null 2>&1 || true

COPY ./src ./src
RUN ./gradlew build -x test --parallel

FROM amazoncorretto:16-alpine
COPY --from=builder /app/build/libs/demo-0.1.3-SNAPSHOT.jar ./

ENTRYPOINT ["java", "-jar", "demo-0.1.3-SNAPSHOT.jar"]