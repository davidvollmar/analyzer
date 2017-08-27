Xml Analyzer

# Compiling
`mvn clean compile`

# Running

## Standalone:
`java -jar target/xml-analyzer-1.0-SNAPSHOT.jar`

## Through docker:
Build the image: `docker build -t analyzer .`

Running the image: `docker run -ti -p 8080:8080 analyzer`
