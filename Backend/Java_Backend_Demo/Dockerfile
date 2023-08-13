# Build container

FROM maven:3.8.3-openjdk-17 as builder

WORKDIR /app

COPY src src
COPY pom.xml .

RUN mvn -f ./pom.xml clean package -Dmaven.test.skip=true

# End build


# Run container

FROM openjdk:17-jdk-slim

WORKDIR /usr/app

COPY --from=builder /app/target/Java_Backend_Demo-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# start app
CMD ["java", "-jar", "Java_Backend_Demo-0.0.1-SNAPSHOT.jar"]