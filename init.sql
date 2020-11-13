CREATE DATABASE IF NOT EXISTS plipski;
USE plipski;
CREATE TABLE users (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
firstname VARCHAR(30) NOT NULL,
lastname VARCHAR(30) NOT NULL,
email VARCHAR(50),
reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO users(firstname, lastname, email) VALUES 
("Jan", "Kowalski", "jan@kowalski.pl"),
("Adam", "Nowak", "adam@nowak.pl");
