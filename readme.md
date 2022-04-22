# IP Logger

## Create Database
first execute `db.sql` file queries in `psql` to create `SERVER_MANAGEMENT` database and `clients` table.

## Configuration
create a file named `app.config` like `app.example.config` and enter your postgres database info in it.
also get a token from [ipinfo.io](https://ipinfo.io) and enter in `app.config` file.

then
```
./mvnw spring-boot:run
```
to start