FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY app/pom.xml pom.xml
COPY app/src src
RUN mvn -B verify

FROM eclipse-temurin:17-jre
RUN apt-get update && apt-get install -y --no-install-recommends gosu && rm -rf /var/lib/apt/lists/* \
    && groupadd --gid 10001 fuzzy && useradd --uid 10001 --gid fuzzy --no-create-home fuzzy
WORKDIR /app
COPY --from=build /build/target/fuzzy-auditing-1.0.0.jar /app/fuzzy-auditing.jar
COPY deployment/entrypoint.sh /app/entrypoint.sh
RUN chmod 755 /app/entrypoint.sh
ENV SERVER_ADDRESS=0.0.0.0 PORT=8080 FUZZY_DATA_ROOT=/var/data COOKIE_SECURE=true SPRING_PROFILES_ACTIVE=mysql,hosted
EXPOSE 8080
ENTRYPOINT ["/app/entrypoint.sh"]
