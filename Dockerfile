FROM openjdk:17 AS builder

COPY . .

RUN ["chmod", "+x", "./gradlew" ]

RUN ["./gradlew", "assemble" ]

FROM openjdk:17

COPY --from=builder /build/libs/app.jar .

ENTRYPOINT ["java", "-jar", "app.jar"]
