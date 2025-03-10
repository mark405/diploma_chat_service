# Use Maven base image with OpenJDK
FROM maven:3.8.5-openjdk-17

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file into the container
COPY pom.xml .

# Fetch the dependencies offline
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Expose the application port (if applicable)
EXPOSE 2002

# Set the entry point to run the application
CMD ["java", "-jar", "target/chat.jar"]
