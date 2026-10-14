
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/certs/ca.pem /app/ca.pem

RUN keytool -importcert -noprompt \
    -alias aiven-mysql-ca \
    -file /app/ca.pem \
    -keystore /app/truststore.jks \
    -storepass changeit

EXPOSE 8080

CMD ["sh", "-c", "java -Djavax.net.ssl.trustStore=/app/truststore.jks -Djavax.net.ssl.trustStorePassword=changeit -Dserver.port=${PORT:-8080} -jar app.jar"]
