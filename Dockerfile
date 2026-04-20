FROM openjdk:17-slim

WORKDIR /app

COPY src/Time.java .

RUN javac Time.java

CMD ["java", "Time"]