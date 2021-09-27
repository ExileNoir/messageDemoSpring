FROM openjdk:8-jre-slim as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract



FROM openjdk:8-jre-slim
WORKDIR application
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/application/ ./
RUN mkdir config
ENTRYPOINT ["java", "-Dspring.config.location=/application/config/application.yml", "org.springframework.boot.loader.JarLauncher"]