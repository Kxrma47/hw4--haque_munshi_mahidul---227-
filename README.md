# hw4--haque_munshi_mahidul---227-

### Project Overview

This project has of two separate microservices designed to handle user authentication and ticket order processing for a ticket ordering system. The projects goal is to demonstrate the implementation of RESTful APIs for user registration, login, and order management using modern technologies such as Spring Boot, Kotlin, and Docker. 

The two microservices are:

1. Authentication (Auth) Microservice: Handles user registration, login, and retrieval of user information. It uses JWT ( Web Tokens) for session management and ensures secure handling of user credentials.
  
2. Order Microservice: Allows authenticated users to create, manage, and retrieve ticket orders. It includes features for processing orders, updating their status, and retrieving order information by ID.

Both microservices are designed to interact with their respective databases, implemented using H2 for simplicity and demonstration purposes. The project also includes Docker and Docker Compose configurations for easy deployment and testing of the microservices.

Key Components:

- Auth Service: Manages user accounts and authentication processes.
- Order Service: Manages the lifecycle of ticket orders, including creation, status updates, and retrieval.
- Database: H2 in-memory databases for both microservices.
- Security: Implementation of JWT for secure user sessions.
- Docker: Containerization of microservices for ease of deployment and testing.
- Postman Collection: Demonstrates the functionality of the API endpoints.

Technologies Used:

- Spring Boot: For building the RESTful API services.
- Kotlin: The programming language used for development.
- JWT: For managing user sessions securely.
- H2 Database: In-memory database for storing user and order data.
- Docker: For containerizing the microservices.
- Gradle: For building and managing project dependencies.


### 2. Microservice API Requirements

#### I. User Authentication Microservice

##### I.1. API Requirements

###### I.1.1. Register a New User
- Endpoint: /api/auth/register
- Method: POST
- Description: Registers a new user with the necessary details: username (nickname), email address (login), and password.
- Request Body:
  
  {
      "nickname": "string",
      "email": "string",
      "password": "string"
  }
  
- Response Codes:
  - 201 Created: User registered successfully.
  - 400 Bad Request: Invalid input data.
- Example Response:
  
  {
      "status": "CREATED",
      "message": "User registered successfully",
      "data": 1
  }
  

###### I.1.2. User Login
- Endpoint: /api/auth/login
- Method: POST
- Description: Authenticates a registered user with their email and password, returning a JWT token for session management.
- Request Body:
  
  {
      "email": "string",
      "password": "string"
  }
  
- Response Codes:
  - 200 OK: Login successful.
  - 401 Unauthorized: Invalid credentials.
- Example Response:
  
  {
      "status": "OK",
      "message": "Login successful",
      "data": "JWT_TOKEN"
  }
  

###### I.1.3. Retrieve User Information
- Endpoint: /api/auth/user
- Method: GET
- Description: Returns user information based on the provided JWT token.
- Request Header: Authorization: Bearer {JWT_TOKEN}
- Response Codes:
  - 200 OK: User information retrieved successfully.
  - 401 Unauthorized: Invalid or missing token.
- Example Response:
  
  {
      "id": 1,
      "nickname": "userone",
      "email": "userone@example.com",
      "password": "$2a$10$...",
      "created": "2024-06-10T12:56:53.954+00:00"
  }
  

#### I.2. Database Schema for Authentication Microservice

###### I.2.1. User Table

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);


###### I.2.2. Session Table

CREATE TABLE session (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);


#### II. Ticket Order Microservice

##### II.1. API Requirements

###### II.1.1. Create a Ticket Order
- Endpoint: /api/orders/create
- Method: POST
- Description: Creates a new ticket order for an authenticated user.
- Request Header: Authorization: Bearer {JWT_TOKEN}
- Request Body:
  
  {
      "userId": 1,
      "fromStationId": 1,
      "toStationId": 2,
      "status": 1
  }
  
- Response Codes:
  - 201 Created: Order created successfully.
  - 400 Bad Request: Invalid input data.
  - 500 Internal Server Error: Order creation failed.
- Example Response:
  
  {
      "status": "CREATED",
      "message": "Order created successfully",
      "data": {
          "id": 1,
          "userId": 1,
          "fromStationId": 1,
          "toStationId": 2,
          "status": 1,
          "created": "2024-06-10T13:04:11.611+00:00"
      }
  }
  

###### II.1.2. Retrieve Order Information
- Endpoint: /api/orders/{id}
- Method: GET
- Description: Retrieves information about a specific order by its ID.
- Response Codes:
  - 200 OK: Order information retrieved successfully.
  - 404 Not Found: Order not found.
- Example Response:
  
  {
      "status": "OK",
      "message": "Order retrieved successfully",
      "data": {
          "id": 1,
          "userId": 1,
          "fromStationId": 1,
          "toStationId": 2,
          "status": 1,
          "created": "2024-06-10T13:04:11.611+00:00"
      }
  }
  

##### II.2. Database Schema for Order Microservice

###### II.2.1. Station Table

CREATE TABLE station (
    id INT AUTO_INCREMENT PRIMARY KEY,
    station VARCHAR(50) NOT NULL
);


###### II.2.2. Order Table

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    from_station_id INT NOT NULL,
    to_station_id INT NOT NULL,
    status INT NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (from_station_id) REFERENCES station(id),
    FOREIGN KEY (to_station_id) REFERENCES station(id)
);


### 3. Postman Collection

A Postman collection is used to demonstrated the functionality of the implemented microservices. Below are the necessary details for setting up a Postman collection to cover all API endpoints for both microservices.

#### 3.1. Setting Up Postman Collection

##### Step 1: Create a New Collection
- Name: Ticket Ordering System
- Description: Collection to test all endpoints of the Ticket Ordering System.

##### Step 2: Add Authentication Requests

###### 3.1.1. Register a User
- Request Name: Register User
- Method: POST
- URL: http://localhost:8082/api/auth/register
- Headers:
  - Content-Type: application/
- Body (raw, ):
  
  {
      "nickname": "userone",
      "email": "userone@example.com",
      "password": "Password1!"
  }
  
- Expected Response:
  
  {
      "status": "CREATED",
      "message": "User registered successfully",
      "data": 1
  }
  <img width="1280" alt="Screenshot 2024-06-10 at 7 41 35 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/92e6d4e6-12ba-4138-9346-b84f19218f00">


###### 3.1.2. Login User
- Request Name: Login User
- Method: POST
- URL: http://localhost:8082/api/auth/login
- Headers:
  - Content-Type: application/
- Body (raw, ):
  
  {
      "email": "userone@example.com",
      "password": "Password1!"
  }
  
- Tests:
  javascript
  var Data = pm.response.();
  pm.environment.set("TOKEN", Data.data);
  
- Expected Response:
  
  {
      "status": "OK",
      "message": "Login successful",
      "data": "JWT_TOKEN"
  }
  
<img width="1280" alt="Screenshot 2024-06-10 at 7 44 03 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/5e4171e5-293f-4dee-8b0e-10af68ade8c4">

###### 3.1.3. Get User Information
- Request Name: Get User Information
- Method: GET
- URL: http://localhost:8082/api/auth/user
- Headers:
  - Authorization: Bearer {{TOKEN}}
- Expected Response:
  
  {
      "id": 1,
      "nickname": "userone",
      "email": "userone@example.com",
      "password": "$2a$10$...",
      "created": "2024-06-10T12:56:53.954+00:00"
  }
  
<img width="1280" alt="Screenshot 2024-06-10 at 7 47 50 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/c8dad22f-d1f0-4bbc-b677-ba5e2dfab9aa">

##### Step 3: Add Order Requests

###### 3.2.1. Create an Order
- Request Name: Create Order
- Method: POST
- URL: http://localhost:8083/api/orders/create
- Headers:
  - Authorization: Bearer {{TOKEN}}
  - Content-Type: application/
- Body (raw, ):
  
  {
      "userId": 1,
      "fromStationId": 1,
      "toStationId": 2,
      "status": 1
  }
  
- Expected Response:
  
  {
      "status": "CREATED",
      "message": "Order created successfully",
      "data": {
          "id": 1,
          "userId": 1,
          "fromStationId": 1,
          "toStationId": 2,
          "status": 1,
          "created": "2024-06-10T13:04:11.611+00:00"
      }
  }
  
![Uploading Screenshot 2024-06-10 at 7.49.25 PM.png…]()

###### 3.2.2. Retrieve Order Information
- Request Name: Get Order Information
- Method: GET
- URL: http://localhost:8083/api/orders/1
- Headers:
  - Authorization: Bearer {{TOKEN}}
- Expected Response:
  
  {
      "status": "OK",
      "message": "Order retrieved successfully",
      "data": {
          "id": 1,
          "userId": 1,
          "fromStationId": 1,
          "toStationId": 2,
          "status": 1,
          "created": "2024-06-10T13:04:11.611+00:00"
      }
  }
  
<img width="1280" alt="Screenshot 2024-06-10 at 7 51 49 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/798c4213-f889-458c-a140-d1d5a4c855dd">

###### 3.2.3. Retrieve All Orders
- Request Name: Get All Orders
- Method: GET
- URL: http://localhost:8083/api/orders
- Headers:
  - Authorization: Bearer {{TOKEN}}
- Expected Response:
  
  {
      "status": "OK",
      "message": "Orders retrieved successfully",
      "data": [
          {
              "id": 1,
              "userId": 1,
              "fromStationId": 1,
              "toStationId": 2,
              "status": 1,
              "created": "2024-06-10T13:04:11.611+00:00"
          }
      ]
  }
  
<img width="1280" alt="Screenshot 2024-06-10 at 7 52 41 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/3b7469d5-8df1-4af3-a421-28e7ff379d32">




### 4. Database Schema and  Scripts

#### 4.1. Authentication Microservice Database Schema

##### 4.1.1. Table: users
- Description: Stores user information.
- Columns:
  - id (integer, primary key, auto-increment): Unique identifier for the user.
  - nickname (varchar): User's nickname.
  - email (varchar, unique): User's email address.
  - password (varchar): Hashed password.
  - created (timestamp): Timestamp of user registration.

#####  Script for Creating users Table:

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);


##### 4.1.2. Table: session
- Description: Stores session information for authenticated users.
- Columns:
  - id (integer, primary key, auto-increment): Unique identifier for the session.
  - user_id (integer, foreign key): ID of the user.
  - token (varchar): JWT token for the session.
  - expires (timestamp): Expiration timestamp for the session.

#####  Script for Creating session Table:

CREATE TABLE session (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


#### 4.2. Order Microservice Database Schema

##### 4.2.1. Table: stations
- Description: Stores station information.
- Columns:
  - id (integer, primary key, auto-increment): Unique identifier for the station.
  - station (varchar): Name of the station.

#####  Script for Creating stations Table:

CREATE TABLE stations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    station VARCHAR(50) NOT NULL
);


##### 4.2.2. Table: orders
- Description: Stores order information.
- Columns:
  - id (integer, primary key, auto-increment): Unique identifier for the order.
  - user_id (integer, foreign key): ID of the user placing the order.
  - from_station_id (integer, foreign key): ID of the departure station.
  - to_station_id (integer, foreign key): ID of the arrival station.
  - status (integer): Status of the order (1 – check, 2 – success, 3 – rejection).
  - created (timestamp): Timestamp of order creation.

#####  Script for Creating orders Table:

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


#### 4.3. Initial Data Insertion Scripts

##### 4.3.1. Insert Initial Stations Data

INSERT INTO stations (station) VALUES ('Station 1');
INSERT INTO stations (station) VALUES ('Station 2');


##### 4.3.2. Insert Initial User Data

INSERT INTO users (nickname, email, password, created)
VALUES
    ('userone', 'userone@example.com', '$2a$10$e9GMOxpHXLmh3uIedIEp5.gXxIsU8uUmjCYReKp73eT/KQp4haXpS', CURRENT_TIMESTAMP()),
    ('usertwo', 'usertwo@example.com', '$2a$10$e9GMOxpHXLmh3uIedIEp5.gXxIsU8uUmjCYReKp73eT/KQp4haXpS', CURRENT_TIMESTAMP());


#### 4.4. Database Configuration

Both microservices use H2 in-memory databases for simplicity in testing and development.

##### Authentication Microservice Configuration (application.yml)
yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:h2:mem:authdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE}
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
  jpa:
    hibernate:
      ddl-auto: update
    show-: true
    database-platform: org.hibernate.dialect.H2Dialect


##### Order Microservice Configuration (application.yml)
yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:h2:mem:orderdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE}
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
  jpa:
    hibernate:
      ddl-auto: update
    show-: true
    database-platform: org.hibernate.dialect.H2Dialect


### 5. API Endpoints Documentation

#### 5.1. Authentication Microservice API

##### 5.1.1. Register a New User

- Endpoint: POST /api/auth/register
- Description: Registers a new user with the given details.
- Request:
  - Headers: Content-Type: application/
  - Body:
    
    {
        "nickname": "string",
        "email": "string",
        "password": "string"
    }
    
  - Example:
    
    {
        "nickname": "userone",
        "email": "userone@example.com",
        "password": "Password1!"
    }
    
- Responses:
  - 201 Created:
    
    {
        "status": "CREATED",
        "message": "User registered successfully",
        "data": 1
    }
    
  - 400 Bad Request: Invalid input or email already registered.
    
    {
        "status": "BAD_REQUEST",
        "message": "Email already registered",
        "data": null
    }
    

##### 5.1.2. User Login

- Endpoint: POST /api/auth/login
- Description: Logs in a user and returns a JWT token.
- Request:
  - Headers: Content-Type: application/
  - Body:
    
    {
        "email": "string",
        "password": "string"
    }
    
  - Example:
    
    {
        "email": "userone@example.com",
        "password": "Password1!"
    }
    
- Responses:
  - 200 OK:
    
    {
        "status": "OK",
        "message": "Login successful",
        "data": "token"
    }
    
  - 401 Unauthorized: Invalid credentials.
    
    {
        "status": "UNAUTHORIZED",
        "message": "Invalid credentials",
        "data": null
    }
    

##### 5.1.3. Get User Information

- Endpoint: GET /api/auth/user
- Description: Returns user information for the authenticated user.
- Request:
  - Headers: Authorization: Bearer {token}
- Responses:
  - 200 OK:
    
    {
        "id": 1,
        "nickname": "userone",
        "email": "userone@example.com",
        "password": "$2a$10$...",
        "created": "2024-06-10T12:56:53.954+00:00"
    }
    
  - 401 Unauthorized: Invalid or missing token.
    
    {
        "status": "UNAUTHORIZED",
        "message": "Invalid token",
        "data": null
    }
    

#### 5.2. Order Microservice API

##### 5.2.1. Create an Order

- Endpoint: POST /api/orders/create
- Description: Creates a new order for an authenticated user.
- Request:
  - Headers:
    - Authorization: Bearer {token}
    - Content-Type: application/
  - Body:
    
    {
        "userId": 1,
        "fromStationId": 1,
        "toStationId": 2,
        "status": 1
    }
    
  - Example:
    
    {
        "userId": 1,
        "fromStationId": 1,
        "toStationId": 2,
        "status": 1
    }
    
- Responses:
  - 201 Created:
    
    {
        "status": "CREATED",
        "message": "Order created successfully",
        "data": {
            "id": 1,
            "userId": 1,
            "fromStationId": 1,
            "toStationId": 2,
            "status": 1,
            "created": "2024-06-10T13:04:11.611+00:00"
        }
    }
    
  - 500 Internal Server Error: Failed to create order.
    
    {
        "status": "INTERNAL_SERVER_ERROR",
        "message": "Failed to create order",
        "data": null
    }
    

##### 5.2.2. Get Order Information

- Endpoint: GET /api/orders/{id}
- Description: Retrieves order information by order ID.
- Request:
  - Headers: Authorization: Bearer {token}
- Responses:
  - 200 OK:
    
    {
        "status": "OK",
        "message": "Order retrieved successfully",
        "data": {
            "id": 1,
            "userId": 1,
            "fromStationId": 1,
            "toStationId": 2,
            "status": 1,
            "created": "2024-06-10T13:04:11.611+00:00"
        }
    }
    
  - 404 Not Found: Order not found.
    
    {
        "status": "NOT_FOUND",
        "message": "Order not found",
        "data": null
    }
    

##### 5.2.3. Get All Orders

- Endpoint: GET /api/orders
- Description: Retrieves all orders.
- Request:
  - Headers: Authorization: Bearer {token}
- Responses:
  - 200 OK:
    
    {
        "status": "OK",
        "message": "Orders retrieved successfully",
        "data": [
            {
                "id": 1,
                "userId": 1,
                "fromStationId": 1,
                "toStationId": 2,
                "status": 1,
                "created": "2024-06-10T13:04:11.611+00:00"
            },
            ...
        ]
    }
    
  - 500 Internal Server Error: Failed to retrieve orders.
    
    {
        "status": "INTERNAL_SERVER_ERROR",
        "message": "Failed to retrieve orders",
        "data": null
    }



### 6. SQL Queries

#### 6.1. Authentication Microservice

##### 6.1.1. Create users Table


CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);


##### 6.1.2. Create session Table


CREATE TABLE session (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


##### 6.1.3. Insert Initial User Data


INSERT INTO users (nickname, email, password, created)
VALUES
    ('userone', 'userone@example.com', '$2a$10$e9GMOxpHXLmh3uIedIEp5.gXxIsU8uUmjCYReKp73eT/KQp4haXpS', CURRENT_TIMESTAMP()),
    ('usertwo', 'usertwo@example.com', '$2a$10$e9GMOxpHXLmh3uIedIEp5.gXxIsU8uUmjCYReKp73eT/KQp4haXpS', CURRENT_TIMESTAMP());


#### 6.2. Order Microservice

##### 6.2.1. Create stations Table


CREATE TABLE stations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    station VARCHAR(50) NOT NULL
);


##### 6.2.2. Create orders Table


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


##### 6.2.3. Insert Initial Stations Data


INSERT INTO stations (station) VALUES ('Station 1');
INSERT INTO stations (station) VALUES ('Station 2');



##### Verifying the Data in H2 Console

You can verify the data directly through the H2 console for both the Auth and Order services.

1. Access H2 Console for Auth Service:
   - URL: http://localhost:8082/h2-console
   - JDBC URL: jdbc:h2:mem:authdb
   - Username: sa
   - Password: password

2. Access H2 Console for Order Service:
   - URL: http://localhost:8083/h2-console
   - JDBC URL: jdbc:h2:mem:orderdb
   - Username: sa
   - Password: password

 Verifying Tables and Data

1. Auth Service:
   - users: Verify that users userone and usertwo are registered.
   - session: Verify that sessions are recorded for userone and usertwo.
   - SELECT * FROM users;
   - SELECT * FROM session;


2. Order Service:
   - users: Verify that users userone and usertwo are present.
   - stations: Verify the initial stations (Station 1, Station 2, Station 3).
   - orders: Verify that the orders created are recorded.
   - SELECT * FROM stations;
   - SELECT * FROM orders;



### 7. Detailed Example of API Usage

This section provides detailed examples of how to use the API endpoints for both the Authentication and Order microservices.

#### 7.1. Authentication Microservice

Register a User

Endpoint: POST /api/auth/register

Request:


curl -X POST http://localhost:8082/api/auth/register -H "Content-Type: application/json" -d '{
    "nickname": "userone",
    "email": "userone@example.com",
    "password": "Password1!"
}'


Response:


{
    "status": "CREATED",
    "message": "User registered successfully",
    "data": 1
}


Login a User

Endpoint: POST /api/auth/login

Request:


TOKEN=$(curl -X POST http://localhost:8082/api/auth/login -H "Content-Type: application/json" -d '{
    "email": "userone@example.com",
    "password": "Password1!"
}' | jq -r '.data')
echo $TOKEN


Response:


(Token received successfully, and will be displayed in the terminal)


Get User Information

Endpoint: GET /api/auth/user

Request:


curl -X GET http://localhost:8082/api/auth/user -H "Authorization: Bearer $TOKEN"


Response:


{
    "id": 1,
    "nickname": "userone",
    "email": "userone@example.com",
    "password": "$2a$10$...",
    "created": "2024-06-10T12:56:53.954+00:00"
}


#### 7.2. Order Microservice

Create an Order

Endpoint: POST /api/orders/create

Request:


curl -X POST http://localhost:8083/api/orders/create -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d '{
    "userId": 1,
    "fromStationId": 1,
    "toStationId": 2,
    "status": 1
}'


Response:


{
    "id": 1,
    "userId": 1,
    "fromStationId": 1,
    "toStationId": 2,
    "status": 1,
    "created": "2024-06-10T13:04:11.611+00:00"
}


Create an Order for a Second User

Register a User

Endpoint: POST /api/auth/register

Request:


curl -X POST http://localhost:8082/api/auth/register -H "Content-Type: application/json" -d '{
    "nickname": "usertwo",
    "email": "usertwo@example.com",
    "password": "Password2@"
}'


Response:


{
    "status": "CREATED",
    "message": "User registered successfully",
    "data": 2
}


Login a User

Endpoint: POST /api/auth/login

Request:


TOKEN=$(curl -X POST http://localhost:8082/api/auth/login -H "Content-Type: application/json" -d '{
    "email": "usertwo@example.com",
    "password": "Password2@"
}' | jq -r '.data')
echo $TOKEN


Response:


(Token received successfully, and will be displayed in the terminal)


Get User Information

Endpoint: GET /api/auth/user

Request:

curl -X GET http://localhost:8082/api/auth/user -H "Authorization: Bearer $TOKEN"


Response:


{
    "id": 2,
    "nickname": "usertwo",
    "email": "usertwo@example.com",
    "password": "$2a$10$...",
    "created": "2024-06-10T13:03:44.115+00:00"
}


Create an Order

Endpoint: POST /api/orders/create

Request:


curl -X POST http://localhost:8083/api/orders/create -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d '{
    "userId": 2,
    "fromStationId": 1,
    "toStationId": 2,
    "status": 1
}'


Response:


{
    "id": 2,
    "userId": 2,
    "fromStationId": 1,
    "toStationId": 2,
    "status": 1,
    "created": "2024-06-10T13:04:11.611+00:00"
}



### 8. API Specification

#### 8.1. Authentication Microservice API

1. Register a User

- Endpoint: POST /api/auth/register
- Request:
  - Method: POST
  - URL: http://localhost:8082/api/auth/register
  - Headers:
    - Content-Type: application/json
  - Body:
    json
    {
        "nickname": "string",
        "email": "string",
        "password": "string"
    }
    
- Response:
  - Status Code: 201 Created
  - Body:
    json
    {
        "status": "CREATED",
        "message": "User registered successfully",
        "data": 1
    }
    

2. Login a User

- Endpoint: POST /api/auth/login
- Request:
  - Method: POST
  - URL: http://localhost:8082/api/auth/login
  - Headers:
    - Content-Type: application/json
  - Body:
    json
    {
        "email": "string",
        "password": "string"
    }
    
- Response:
  - Status Code: 200 OK
  - Body:
    json
    {
        "status": "OK",
        "message": "Login successful",
        "data": "token"
    }
    

3. Get User Information

- Endpoint: GET /api/auth/user
- Request:
  - Method: GET
  - URL: http://localhost:8082/api/auth/user
  - Headers:
    - Authorization: Bearer token
- Response:
  - Status Code: 200 OK
  - Body:
    json
    {
        "id": 1,
        "nickname": "string",
        "email": "string",
        "password": "string",
        "created": "timestamp"
    }
    

#### 8.2. Order Microservice API

1. Create an Order

- Endpoint: POST /api/orders/create
- Request:
  - Method: POST
  - URL: http://localhost:8083/api/orders/create
  - Headers:
    - Content-Type: application/json
    - Authorization: Bearer token
  - Body:
    json
    {
        "userId": 1,
        "fromStationId": 1,
        "toStationId": 2,
        "status": 1
    }
    
- Response:
  - Status Code: 201 Created
  - Body:
    json
    {
        "id": 1,
        "userId": 1,
        "fromStationId": 1,
        "toStationId": 2,
        "status": 1,
        "created": "timestamp"
    }
    

2. Get Order by ID

- Endpoint: GET /api/orders/{id}
- Request:
  - Method: GET
  - URL: http://localhost:8083/api/orders/{id}
  - Headers:
    - Authorization: Bearer token
- Response:
  - Status Code: 200 OK
  - Body:
    json
    {
        "id": 1,
        "userId": 1,
        "fromStationId": 1,
        "toStationId": 2,
        "status": 1,
        "created": "timestamp"
    }
    

3. Get All Orders

- Endpoint: GET /api/orders
- Request:
  - Method: GET
  - URL: http://localhost:8083/api/orders
  - Headers:
    - Authorization: Bearer token
- Response:
  - Status Code: 200 OK
  - Body:
    json
    [
        {
            "id": 1,
            "userId": 1,
            "fromStationId": 1,
            "toStationId": 2,
            "status": 1,
            "created": "timestamp"
        },
        ...
    ]

### 9. Security Considerations


#### 9.1. JWT Authentication

- Token Generation: Upon successful login, a JSON Web Token (JWT) is generated using the JwtUtil class. This token includes claims such as the user's email, ID, and nickname.
  java
  fun generateToken(user: User): String {
      val claims = Jwts.claims().setSubject(user.email).apply {
          this["userId"] = user.id
          this["nickname"] = user.nickname
      }
      return Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(Date())
          .setExpiration(Date(System.currentTimeMillis() + 1000  60  60  10)) // 10 hours
          .signWith(secretKey)
          .compact()
  }
  
- Token Validation: The validateToken method ensures the token is not expired and checks its integrity.
  java
  fun validateToken(token: String): Boolean {
      return try {
          val claims = getClaimsFromToken(token)
          val expirationDate = claims.expiration
          !expirationDate.before(Date())
      } catch (e: Exception) {
          false
      }
  }
  
- Token Parsing: The email is extracted from the token to identify the user.
  java
  fun getEmailFromToken(token: String): String {
      return getClaimsFromToken(token).subject
  }
  
- Request Filtering: A custom filter (jwtAuthenticationFilter) is added to the security configuration to intercept requests and validate the JWT.
  java
  http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
  

#### 9.2. Password Hashing

- BCrypt Password Encoder: Passwords are hashed using BCrypt before being stored in the database. This ensures that even if the database is compromised, the actual passwords remain secure.
  java
  val encodedPassword = BCryptPasswordEncoder().encode(user.password)
  
- Password Verification: During login, the provided password is compared with the hashed password stored in the database using BCrypt's matches method.
  java
  if (!BCryptPasswordEncoder().matches(password, user.password)) {
      throw IllegalArgumentException("Invalid credentials")
  }

#### 10. Patterns and Practices

##### Design Patterns Used

1. Singleton Pattern:
   - Used in JwtUtil class to ensure a single instance of the JWT utility across the application.
   - Ensures centralized management of JWT token generation and validation.

2. Service Layer Pattern:
   - Used in both AuthService and OrderService classes.
   - Encapsulates business logic and provides a clear separation between the controller and the repository layers.

3. Repository Pattern:
   - Used for data access in UserRepository, SessionRepository, OrderRepository, and StationRepository.
   - Provides a cleaner data access layer and abstracts database interactions.

4. Factory Pattern:
   - Utilized in Spring Boot for the creation of beans, such as SecurityFilterChain in the SecurityConfig class.

##### Best Practices Followed

1. Dependency Injection:
   - Leveraged Spring's dependency injection to manage service and repository dependencies.
   - Promotes loose coupling and enhances testability.

2. Centralized Exception Handling:
   - Implemented a global exception handler using @ControllerAdvice to manage and format exceptions.
   - Provides consistent error responses across the application.

3. Validation:
   - Used annotations like @NotBlank, @Email, @Size, and @Pattern to enforce validation rules on model properties.
   - Ensures data integrity and prevents invalid data from entering the system.

4. Security:
   - Implemented JWT for secure authentication.
   - Passwords are hashed using BCryptPasswordEncoder to enhance security.

5. Logging:
   - Incorporated logging at various levels (INFO, ERROR) to track application behavior and errors.
   - Helps in debugging and monitoring the application.

6. Separation of Concerns:
   - Maintained a clear separation between different layers: controller, service, and repository.
   - Enhances maintainability and readability of the code.
  

### structure
<img width="395" alt="Screenshot 2024-06-10 at 8 43 27 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/1ae0ea0b-73cf-47bd-9ad2-a7f676af1ee9">
<img width="421" alt="Screenshot 2024-06-10 at 8 43 50 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/6de1e41c-3ebb-481a-8f77-bbdaae2004b9">
<img width="361" alt="Screenshot 2024-06-10 at 8 44 25 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/048c0276-676c-4859-b438-3bbdbdfe6888">


### Diagram

<img width="329" alt="Screenshot 2024-06-10 at 8 45 40 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/6a6ea03a-3f43-4bba-b841-b1ddb602e24c">
<img width="545" alt="Screenshot 2024-06-10 at 8 47 51 PM" src="https://github.com/Kxrma47/hw4--haque_munshi_mahidul---227-/assets/114881854/1eb7a7d7-565c-444b-b16b-5a34d0387522">


### References
#### Libraries and Frameworks Used
1. Spring Boot:
   - spring-boot-starter-data-jpa: Provides support for Java Persistence API (JPA) using Spring Data JPA.
   - spring-boot-starter-web: Builds web applications, including RESTful services, using Spring MVC.
   - spring-boot-starter-validation: Adds support for bean validation using Hibernate Validator.
   - spring-boot-starter-security: Provides security features for Spring applications.
   - spring-boot-starter-test: Adds support for testing Spring Boot applications with JUnit and Mockito.

2. Kotlin:
   - kotlin-stdlib-jdk8: Standard library for Kotlin with JDK 8 extensions.
   - kotlin-reflect: Kotlin reflection library.
   - kotlin-plugin-spring: Plugin for integrating Kotlin with Spring.

3. H2 Database:
   - com.h2database:h2: H2 in-memory database for development and testing.

4. JWT (JSON Web Token):
   - io.jsonwebtoken:jjwt-api: JWT API for creating and parsing JSON Web Tokens.
   - io.jsonwebtoken:jjwt-impl: Implementation for JJWT API.
   - io.jsonwebtoken:jjwt-jackson: Jackson serializer for JJWT.

5. Gradle:
   - Gradle build tool for managing project dependencies and tasks.

#### Additional Resources
1. Spring Documentation:
   - [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
   - [Spring Security Reference Guide](https://docs.spring.io/spring-security/reference/)

2. Kotlin Documentation:
   - [Kotlin Language Documentation](https://kotlinlang.org/docs/reference/)
   - [Kotlin and Spring Boot](https://spring.io/guides/tutorials/spring-boot-kotlin/)

3. JWT Documentation:
   - [JWT Introduction and Overview](https://jwt.io/introduction/)

4. H2 Database Documentation:
   - [H2 Database Engine](http://www.h2database.com/html/main.html)

5. Gradle Documentation:
   - [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)

6. Project Setup and Tutorials:
   - [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
   - [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
   - [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
  


NOTE: Every part of the code has been tested several times with very effort! I use mac os M2 silicon, if you face any kind of dependency issue , please go through the details again or reach out to me. This implimentation works perfectly with curl commands and postman api.
