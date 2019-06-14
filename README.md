# User Service

## Prerequisites

* Local Postgres Database
* Java

## Build

```../mvnw clean install```

## Run

Add the following properties to a local file `src/main/resources/application-local.properties`

```
spring.datasource.url={your_database_url}
spring.datasource.username={your_username}
spring.datasource.password={your_password}
```

Now run it

```./mvnw spring-boot:run```

## Documentation

http://localhost:8080/swagger-ui.html
