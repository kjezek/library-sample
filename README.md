# Introduction

This is a simple library application. It provides a REST API
to manipulate with the books. It allows for adding, removing,
getting, and searching for the books. 

# Build

Maven is used to build this project. Type: 
```
mvn clean install
```

# Run

The application is a single execution file. Type: 
```
 java -jar target/backend-1.0-SNAPSHOT.jar
```

The application is accessible at the following URL:

```
localhost:8080/swagger-ui.html
```

The webpage documents the API. 

# Build in Docker

Alternatively, the application may be built by docker. Type:
```
docker build -t library .
```

# Run in Docker

Run the docker container. Type: 
```
docker run -p 8080:8080  -it library 
```
