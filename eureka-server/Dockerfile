# Stage 1. Package and cache dependencies
FROM openjdk:22-jdk-slim
ARG APPLICATION_USER=spring

# Create a user to run the application, don't run as root
RUN addgroup --system $APPLICATION_USER &&  adduser --system $APPLICATION_USER --ingroup $APPLICATION_USER

# Create the application directory
RUN mkdir /app && chown -R $APPLICATION_USER /app
WORKDIR /app

# 1. add pom.xml only here
COPY target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]