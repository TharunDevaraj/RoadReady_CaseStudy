# RoadReady - Car Rental System

This is a project for a full-stack Car Rental System. The application is designed using a microservices architecture with dedicated services for user management, vehicle management, and rental operations. The frontend is developed using Angular, while the backend is built using Spring Boot and MySQL. The application is containerized with Docker and orchestrated using Docker Compose.

## Project Structure

```
casestudy/
├── backend/
│   ├── userservice/        # Manages authentication, authorization, and user operations
│   ├── vehicleservice/     # Manages vehicle details and availability
│   └── rentalservice/      # Handles reservations, payments, feedback, and reports
├── frontend/               # Angular application for all user roles
└── docker-compose.yml      # Docker Compose configuration for the entire system
```

## Technologies Used

- Java 17
- Spring Boot
- Spring Security (JWT-based)
- Spring Data JPA
- MySQL
- Angular 15
- Docker & Docker Compose
- Maven

## Features

### User Service

- Register, update, and deactivate users
- Role-based access: Admin, Customer, Agent
- JWT-based authentication
- Change password functionality

### Vehicle Service

- Add, update, delete vehicle records
- Filter available cars by location, capacity, and date
- Update car status and pricing

### Rental Service

- Create, update, cancel reservations
- Check-in and check-out functionality
- Manage and process payments
- Submit and manage customer feedback
- Generate admin reports: total revenue, total reservations

### Frontend

- Customer: Search and reserve vehicles, make payments, view history, give feedback
- Admin: Manage users and cars, view feedback, access reports
- Agent: Assist in check-ins and check-outs, update car status

## Deployment

### Prerequisites

- Docker and Docker Compose installed
- Ports 8080, 8181, 8282, and 4200 available


### Start the Application

```bash
docker compose up --build
```

### Access Points

  If you want to enter as admin, use "RR_ADMIN" as asmin secret key

- Frontend: http://localhost:4200
- User Service: http://localhost:8080
- Vehicle Service: http://localhost:8181
- Rental Service: http://localhost:8282

Note: Internal service communication uses service names (userservice, vehicleservice, rentalservice) instead of localhost.

## Sample Endpoints

- GET /api/users/getAllCars – Retrieve all cars
- POST /api/users/makeReservation – Create a reservation
- POST /api/users/makePayment – Make a payment
- GET /api/users/total-revenue – Admin revenue report

## Author

Tharun D

## License

This project was developed as part of a student academic program and is intended for learning purposes only.
