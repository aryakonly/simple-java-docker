FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY src/Time.java .

RUN javac Time.java

CMD ["java", "Time"]