# Stage 1. Package and cache dependencies
FROM maven:3.9-eclipse-temurin-22-alpine AS maven
ENV HOME=/home/usr/app

RUN mkdir -p $HOME
# 1. add pom.xml only here
COPY . $HOME
WORKDIR $HOME/rh-parent
# 2. start downloading dependencies for core
RUN mvn verify clean --fail-never
# 3. install core
RUN mvn package install -DskipTests
# 4. start downloading dependencies for app
WORKDIR $HOME
RUN mvn verify clean --fail-never
# 5. add all source code and start compiling
RUN mvn -B -e -X install -DskipTests 

# https://medium.com/@RoussiAbdelghani/optimizing-java-base-docker-images-size-from-674mb-to-58mb-c1b7c911f622
# Stage 2. Build the custom JRE
# FROM eclipse-temurin:17-jdk-alpine AS jre-builder

# RUN mkdir /opt/app
# COPY . /opt/app

# WORKDIR /opt/app

# ENV MAVEN_VERSION 3.5.4
# ENV MAVEN_HOME /usr/lib/mvn
# ENV PATH $MAVEN_HOME/bin:$PATH
# ENV APP_NAME_VERSION service-auth-0.0.1-SNAPSHOT

# RUN apk update && \
#     apk add --no-cache tar binutils

# COPY --from=maven $HOME .
# RUN jar xvf target/$APP_NAME_VERSION.jar
# RUN jdeps --ignore-missing-deps -q  \
#     --recursive  \
#     --multi-release 17  \
#     --print-module-deps  \
#     --class-path 'BOOT-INF/lib/*'  \
#     target/$APP_NAME_VERSION.jar > modules.txt

# # Build small JRE image
# RUN $JAVA_HOME/bin/jlink \
#          --verbose \
#          --add-modules $(cat modules.txt) \
#          --strip-debug \
#          --no-man-pages \
#          --no-header-files \
#          --compress=2 \
#          --output /optimized-jdk-17

# # Stage 3. Use the custom JRE and build the app image
# FROM alpine:latest
# ENV JAVA_HOME=/opt/jdk/jdk-17
# ENV PATH="${JAVA_HOME}/bin:${PATH}"

# # copy JRE from the base image
# COPY --from=jre-builder /optimized-jdk-17 $JAVA_HOME

# # Add app user
# ARG APPLICATION_USER=spring

# # Create a user to run the application, don't run as root
# RUN addgroup --system $APPLICATION_USER &&  adduser --system $APPLICATION_USER --ingroup $APPLICATION_USER

# # Create the application directory
# RUN mkdir /app && chown -R $APPLICATION_USER /app

# COPY --chown=$APPLICATION_USER:$APPLICATION_USER target/*.jar /app/app.jar

# WORKDIR /app

# USER $APPLICATION_USER

# EXPOSE 8082
# ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]