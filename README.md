# CafeShop Microservice System

## Database Design and Entities
### Introduction

This document provides an overview of the database design and the entities used in the Coffee Shop application. The system is designed to manage customer orders, queue management, and franchise operations. The database design aims to support the application's requirements efficiently while maintaining data integrity and scalability.

### Database Design

![Databas Design](documents/database_design.png "Databas Design")

The Coffee Shop application uses a relational database design with the following primary entities:

1. **User**: Represents customers and shop owners.
2. **Franchise**: Represents different coffee shop locations.
3. **Merchant**: Represents the merchant or owner of multiple franchises.
4. **Queue**: Manages customer queues within each franchise.
5. **Order**: Represents customer orders, including multiple menu items.
6. **MenuItem**: Represents items available on the menu.
7. **OrderItem**: Represents the items in an order, including quantity.
8. **CustomerQueue**: Manages the relationship between customers and queues.
9. **CustomerScore**: Tracks customer scores based on their service history.

### Entities and Their Relationships

##### 1. User

Represents both customers and shop owners. The `User` entity includes:

- `id`: Unique identifier for the user (UUID).
- `username`: Unique username for login.
- `password`: User password (hashed).
- `firstName`: User's first name.
- `lastName`: User's last name.
- `email`: User's email address.
- `phone`: User's phone number.
- `role`: Role of the user (e.g., ADMIN, OPERATOR, CUSTOMER).
- `address`: Home or work address of the user.
- `merchant`: Optional link to the `Merchant` entity (nullable).

**Relationships:**
- Many `User` entities can be associated with one `Merchant`.

##### 2. Franchise

Represents a coffee shop location. The `Franchise` entity includes:

- `id`: Unique identifier for the franchise (UUID).
- `location`: Location of the franchise.
- `name`: Name of the franchise.
- `contactDetails`: Contact information for the franchise.
- `openingTime`: Opening time.
- `closingTime`: Closing time.
- `numberOfQueues`: Number of queues at the franchise.
- `maxQueueSize`: Maximum size of each queue.
- `merchant`: Associated `Merchant` entity.
- `latitude`: latitude/longitude
- `longitude`: latitude/longitude

**Relationships:**
- A `Franchise` can have multiple `Queue` entities.
- A `Franchise` can have multiple `MenuItem` entities.

##### 3. Merchant

Represents the owner of one or more franchises. The `Merchant` entity includes:

- `id`: Unique identifier for the merchant (UUID).
- `name`: Name of the merchant.

**Relationships:**
- A `Merchant` can own multiple `Franchise` entities.

#### 4. Queue

Manages the queue system within a franchise. The `Queue` entity includes:

- `id`: Unique identifier for the queue (UUID).
- `franchise`: Associated `Franchise` entity.
- `queueNumber`: Identifier for the queue within the franchise.
- `customerQueues`: Set of `CustomerQueue` entities for customers in the queue.
- `orders`: Set of `Order` entities associated with this queue.

**Relationships:**
- A `Queue` can have multiple `CustomerQueue` and `Order` entities.

##### 5. Order

Represents a customer's order, including multiple menu items. The `Order` entity includes:

- `id`: Unique identifier for the order (UUID).
- `orderNumber`: Unique order number.
- `customer`: Associated `User` entity.
- `franchise`: Associated `Franchise` entity.
- `queuePosition`: Position of the order in the queue.
- `queue`: Associated `Queue` entity (nullable).
- `status`: Status of order: NEW, PROCESS, FAILED, CANCEL, SUCCESS

**Relationships:**
- An `Order` can have multiple `OrderItem` entities.

##### 6. MenuItem

Represents items available on the menu. The `MenuItem` entity includes:

- `id`: Unique identifier for the menu item (UUID).
- `name`: Name of the menu item.
- `price`: Price of the menu item.
- `currency`: Currency for the price.
- `isAvailable`: Availability status of the menu item.
- `franchise`: Associated `Franchise` entity.

**Relationships:**
- A `MenuItem` is associated with a single `Franchise`.

##### 7. CustomerQueue

Manages the relationship between customers and queues. The `CustomerQueue` entity includes:

- `id`: Unique identifier for the customer queue (UUID).
- `queue`: Associated `Queue` entity.
- `customer`: Associated `User` entity.
- `orderNumber`: Reference to the order if any.
- `waitingSince`: Timestamp when the customer entered the queue.

**Relationships:**
- A `CustomerQueue` is linked to a single `User` and a single `Queue`.

##### 8. CustomerScore

Tracks customer scores based on their service history. The `CustomerScore` entity includes:

- `id`: Unique identifier for the customer score (UUID).
- `customer`: Associated `User` entity.
- `franchise`: Associated `Franchise` entity.
- `score`: Score indicating the number of times the customer has been served.

**Relationships:**
- A `CustomerScore` is linked to a single `User` and a single `Franchise`.

# Cafe Shop Microservices Deployment Guide

This guide provides step-by-step instructions on how to deploy the `cafe_shop_microservices` application. It covers building the JAR file, copying it to the deployment folder, and running `docker-compose` to set up and run the services.

## Prerequisites

Before starting the deployment process, ensure that you have the following installed on your system:

- **Java 17**: The application is built using Java 17.
- **Gradle**: For building the JAR file.
- **Docker**: For running the containerized application.
- **Docker Compose**: To orchestrate multiple services.

## Build the JAR File

1. Navigate to the root directory of the project.

2. Run the following Gradle command to build the JAR file:

```bash
cd deployment && sh build.sh
```
## Development

3. Deploy project on docker with docker-compose
```bash
docker-compose up -d
```

# System Outline
## Import API Document

Where is store API collections in postman:
```base
doucment/CafeShop.postman_collection.json
document/CafeShop.postman_environment.json
```

## Process setup franchise

![Process setup franchise](documents/setup_franchise.png "Process setup franchise")

### Explain workflow:
1. Login as an admin account with for demo:
```cmd
curl --location 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "admin",
    "password": "123456a@"
}'
```
After a successful login, a JWT token will be returned in the response body:
```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvcGVyYXRvciIsImlhdCI6MTcyNTI0NDYwNCwiZXhwIjoxNzI1MjQ4MjA0fQ.PRhOxeIaTjUar3Bxap0H6SRZRGtv3qXW3Qbsp1_fh_dR4TGLT-cP-pILrBl2vn9rSPwYHh34irGSsJC5RQcBRA"
}
```
2. Create a New Franchise with Example Payload:
```
curl --location 'http://localhost:8080/api/v1/franchises' \
    --header 'Authorization: Bearer *************' \
    --header 'Content-Type: application/json' \
    --data-raw '{
      "location": "123 Coffee Lane, Downtown",
      "name": "Central Coffee Shop",
      "contactDetails": "contact@centralcoffee.com, +1234567890",
      "openingTime": "07:00:00",
      "closingTime": "19:00:00",
      "numberOfQueues": 2,
      "maxQueueSize": 10,
      "latitude": 21,
      "longitude": 105
    }'
```
When the franchise is successfully created, a franchiseId will be returned, which we can use to set up menu items in the next step.

3. Create Menu Items:
```
curl --location 'http://localhost:8080/api/v1/franchises/4dd4ff9c-0ac8-4422-90ce-ad3531f74963/menu' \
    --header 'Authorization: Bearer *************' \
    --header 'Content-Type: application/json' \
    --data '[
      {
        "name": "Latte",
        "price": 4.50,
        "currency": "USD",
        "isAvailable": true
      },
      {
        "name": "Cappuccino",
        "price": 5.00,
        "currency": "USD",
        "isAvailable": true
      },
      {
        "name": "Espresso",
        "price": 3.00,
        "currency": "USD",
        "isAvailable": true
      },
      {
        "name": "Americano",
        "price": 3.50,
        "currency": "USD",
        "isAvailable": true
      },
      {
        "name": "Mocha",
        "price": 5.50,
        "currency": "USD",
        "isAvailable": false
      }
    ]
    '
```

4. Create a New Operator Account:
```
curl --location 'http://localhost:8080/api/v1/users' \
    --header 'Authorization: Bearer *************' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "username": "operator",
        "password": "123456a@",
        "role": "OPERATOR"
    }'
```

## Process order
![Order process](documents/order_process.png "Order process")

### Explain workflow
#### 1. Customer Creates an Account Using Their Phone:
```
curl --location 'http://localhost:8080/api/v1/customers/register' \
    --header 'Content-Type: application/json' \
    --data '{
        "mobile_number": "0123444889",
        "first_name": "Jackyie 2",
        "address": "Hanoi, Vietnam"
    }'
```
#### 2. Customer Logs In Using the Created Account:
```
curl --location 'http://localhost:8080/api/v1/auth/login' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "username": "0123444889",
        "password": "123456a@"
    }'
```
A token will be returned for use in the next steps:

#### 3. Customer Searches for a Franchise by Location (lat, lng):
```
curl --location 'http://localhost:8080/api/v1/franchises/nearby?latitude=40&longitude=-73' \
    --header 'Authorization: Bearer *****'
```
#### 4. Get Menu Items by Franchise Using franchiseId:
```
curl --location 'http://localhost:8080/api/v1/franchises/4dd4ff9c-0ac8-4422-90ce-ad3531f74963/menu' \
--header 'Authorization: Bearer *****'
```
#### 5 & 6. Request to Create a New Order and Set It Into Queue:

In this step, customers can pick items from the menu obtained from the previous API to create an order. The system will check the available queues based on the conditions set when the franchise was created (numberOfQueues, maxQueueSize...).

- If a queue is available, create a new order and set it into the queue.
- If a queue is unavailable, return a failure notification for the customers.

Example request to create an order:

```
curl --location 'http://localhost:8080/api/v1/orders' \
--header 'Authorization: Bearer *************' \
--header 'Content-Type: application/json' \
--data '{
    "franchiseId": "4dd4ff9c-0ac8-4422-90ce-ad3531f74963",
    "items": [
        {
            "menuItemId": "aa6e35d9-9282-42ff-ba7a-2ca9b8b7c5bb",
            "quantity": 1
        }
    ]
}'
```
Customers can also view order history:
```
http://localhost:8080/api/v1/orders?page=1&limit=10&status=PROCESS&sort=createdAt:asc
```
Or check their customer score via API:
```
http://localhost:8080/api/v1/customers/score
```
#### 8. Order pickup queue to serve order
First, the operator must log in with the account created when the admin set up the franchise. Then, they can get all orders in the queue: 

Example request:
```
curl --location 'http://localhost:8080/api/v1/queues/57c552c4-92f6-48d6-a496-fdf6075c5c67/orders' \
--header 'Authorization: Bearer *****'
```
Based on the list of orders, the operator can pick an order to service. After completing the customer’s order, they can finish the process and remove it from the queue:
```
curl --location 'http://localhost:8080/api/v1/queues/57c552c4-92f6-48d6-a496-fdf6075c5c67/service' \
--header 'Authorization: Bearer ****' \
--header 'Content-Type: application/json' \
--data '{
    "customerQueueId": "4ce7825a-a3bd-46ad-93e8-cf1dc68f66c9"
}'
```



