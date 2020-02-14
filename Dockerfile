# Start with a base image containing Java runtime
FROM openjdk:8

# Make port 8080 available to the world outside this container
EXPOSE 8080

ADD target/currencyconverter-0.0.1-SNAPSHOT.jar currencyconverter-0.0.1-SNAPSHOT.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/currencyconverter-0.0.1-SNAPSHOT.jar"]