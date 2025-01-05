# Set the basic image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy build JAR file into the container's working directory
COPY target/Oracle-1-0.0.1-SNAPSHOT.jar /app/Oracle-1-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","Oracle-1-0.0.1-SNAPSHOT.jar"]

