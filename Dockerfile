FROM openjdk:9
ADD target/springbootkafka.jar springbootkafka.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "springbootkafka.jar"]