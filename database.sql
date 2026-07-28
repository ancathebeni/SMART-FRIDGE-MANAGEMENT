CREATE DATABASE smartfridge;

USE smartfridge;

CREATE TABLE products(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    date_added DATE,
    expiry_date DATE
);
