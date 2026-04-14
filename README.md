# Ride Hailing System (GoComet DAW Assignment)

A scalable ride-hailing backend system built using Java 21, Spring Boot, Redis, and MySQL.

---

## Features

- Real-time driver location tracking using Redis GEO
- Driver rider matching based on proximity
- Sequential driver dispatch with timeout
- Driver state management (AVAILABLE, BUSY, OFFLINE)
- Trip lifecycle management
- Payment simulation
- Concurrency safe ride assignment
- Redis based locking and queueing
- Simple frontend for testing
- New Relic integration for monitoring

---

## Tech Stack

- Java 21
- Spring Boot
- Maven
- MySQL
- Redis
- HTML, CSS, JavaScript
- New Relic

---

## System Design Highlights

### Driver Matching
Driver locations are stored in Redis using GEO indexing. Nearby drivers are fetched using radius search.

### Ride Dispatch Flow
1. Rider creates ride
2. Nearby drivers fetched from Redis
3. Drivers pushed to Redis queue
4. Ride offered to one driver at a time for 60 seconds
5. If rejected or timeout, next driver is selected
6. If accepted, ride is assigned

### Concurrency Handling
- Redis lock prevents multiple drivers accepting the same ride
- Database conditional update ensures atomic assignment

### Driver State Management
- AVAILABLE when idle
- BUSY during offer and ride
- AVAILABLE after timeout or trip completion

---

## Project Structure

ride-hailing/
 ├── controller/
 ├── service/
 ├── repository/
 ├── entity/
 ├── dto/
 ├── config/
 └── resources/

---

## Setup Instructions

### 1. Clone Repository

git clone <repo-url>
cd ride-hailing

---

### 2. Start Redis using Docker

docker run -d -p 6379:6379 redis

---

### 3. Configure MySQL

Update application.yml

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ride_db
    username: root
    password: root

---

### 4. Run Application

mvn clean install
java -jar target/ride-hailing.jar

---

## API Endpoints

### Ride APIs

POST /v1/rides - Create ride
GET /v1/rides/{id} - Get ride status
GET /v1/rides/{id}/current-driver - Current driver

---

### Driver APIs

POST /v1/drivers - Create driver
GET /v1/drivers - Get all drivers
POST /v1/drivers/{id}/location - Update location
POST /v1/drivers/{id}/accept - Accept ride

---

### Trip and Payment APIs

POST /v1/trips/{id}/end - End trip
POST /v1/payments - Make payment

---

## Testing Flow

1. Create drivers
2. Update driver locations
3. Create ride
4. Accept ride
5. End trip
6. Make payment

---

## Monitoring with New Relic

- Integrated using Java agent
- Tracks API latency and database performance
- Alerts configured for slow response times

---

## Limitations

- Uses Thread.sleep for timeout handling
- No real payment gateway integration
- No real-time updates using WebSockets

---

## Future Improvements

- Kafka based event driven system
- WebSocket live updates
- Surge pricing
- Driver rejection API
- Multi-region deployment

---

## Key Design Decisions

- Redis used for fast and temporary data like location and queues
- MySQL used for consistent and transactional data
- Sequential dispatch ensures fairness
- Atomic database updates prevent race conditions


  ![Untitled Diagram](https://github.com/user-attachments/assets/69a25813-b993-402e-b5e3-e6f3be4b9af1)

## New Relic Dashboards:

<img width="1916" height="1018" alt="newralic1" src="https://github.com/user-attachments/assets/b7971440-f9db-43ce-ab94-e7cc4be4b813" />
<img width="1918" height="1011" alt="image" src="https://github.com/user-attachments/assets/a1d57378-a014-49d5-bf27-95ac0a159980" />
<img width="1918" height="1015" alt="image" src="https://github.com/user-attachments/assets/7034535e-a19b-437e-b03b-6a1baf83a0fa" />
<img width="1918" height="978" alt="image" src="https://github.com/user-attachments/assets/87488689-e5e9-420d-b738-190153db4136" />

### documentation link:
https://drive.google.com/drive/folders/1J2JtfQZd0UFnX04OCrnz143rQJSE3G4b?usp=drive_link
---

## Author

Krishnendra Sharma
