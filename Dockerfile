FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/*.jar app.jar

RUN mkdir /data

ENV DB_PATH=/data/pii-kokodi-game.db
ENV DATABASE_URL=jdbc:sqlite:/data/pii-kokodi-game.db
ENV JWT_SECRET=dev-secret

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["-Ddatabase.url=${DATABASE_URL}", "-Djwt.secret=${JWT_SECRET}"]
