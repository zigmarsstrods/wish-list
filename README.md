# 🥰 Wish list 🥰

## Description

REST API using JAVA + PostgreSQL database that allows:

1. To add, fetch/get, update and delete wishes via HTTP requests into PostgreSQL database;
2. To fetch/get all wishes saved at some time into the PostgreSQL database;
3. On separate `/users/` path to accept JSON request with users data in following format:
   `{"users": \[{ "type": "type as string", "id": integer, "name": "name as string", "email": "email as string" } ] }`
   and return all names separated by comma.

## Setup

1. Clone this repository
2. Local PostgresSQL database is necessary to run the application. The configuration details of the database should be
   provided in `application.properties` file entering following properties:

- URL(hostname, port and database name) for Your local PosgresSQL database in field `spring.datasource.url=`
- Username in `spring.datasource.username=`
- Password in `spring.datasource.password=`

## Usage

The program can be run via   `./gradlew bootRun`

After running endpoint description is available in [Swagger](http://localhost:8080/swagger-ui/index.html)
![Swagger](Swagger.png)

## Tests

The tests can be run via  `./gradlew clean test`