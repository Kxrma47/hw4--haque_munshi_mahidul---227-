-- Create Users Table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

-- Create Stations Table
CREATE TABLE stations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    station VARCHAR(50) NOT NULL
);

-- Create Orders Table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    from_station_id INT NOT NULL,
    to_station_id INT NOT NULL,
    status INT NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (from_station_id) REFERENCES stations(id),
    FOREIGN KEY (to_station_id) REFERENCES stations(id)
);

-- Insert Initial Stations Data
INSERT INTO stations (station) VALUES ('Station 1');
INSERT INTO stations (station) VALUES ('Station 2');

-- Insert Initial User Data
INSERT INTO users (nickname, email, password, created)
VALUES
    ('userone', 'userone@example.com', '$2a$10$e9GMOxpHXLmh3uIedIEp5.gXxIsU8uUmjCYReKp73eT/KQp4haXpS', CURRENT_TIMESTAMP()),
    ('usertwo', 'usertwo@example.com', '$2a$10$e9GMOxpHXLmh3uIedIEp5.gXxIsU8uUmjCYReKp73eT/KQp4haXpS', CURRENT_TIMESTAMP());
