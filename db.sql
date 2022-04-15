CREATE DATABASE server_management;
CREATE TABLE clients (
    Id SERIAL PRIMARY KEY,
    Ip CHAR(15),
    Country VARCHAR(255),
    City VARCHAR(255),
    Location VARCHAR(255),
    Organization VARCHAR(255),
    Date Date,
    Time Time
);