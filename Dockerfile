FROM openjdk:17-alpine

WORKDIR /app

COPY src/Time.java .

RUN javac Time.java

CMD ["java", "Time"]