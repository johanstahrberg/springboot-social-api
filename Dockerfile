# =========================
# STEG 1: Build
# =========================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Kopiera pom.xml först för caching
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Kopiera källkod och bygg
COPY src ./src
RUN mvn -B clean package -DskipTests













FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/springboot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

