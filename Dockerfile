FROM java:alpine

COPY "target/xml-analyzer-1.0-SNAPSHOT.jar" /app/analyzer.jar

CMD ["java", "-jar", "/app/analyzer.jar"]