# GinDHO - Medical Assistance System

## Overview
GinDHO is a comprehensive medical assistance platform that connects patients, doctors, and administrators through desktop and web interfaces.

## Quick Start

### Prerequisites
- Java 21
- Maven 3.8+
- Node.js 18+ (for frontend)
- PostgreSQL 15+

### Backend Setup
```bash
cd backend
mvn spring-boot:run
```

### JavaFX Client Setup
```bash
cd javafx-client
mvn javafx:run
```

### React Web Setup
```bash
cd react-web
npm install
npm start
```

## Features

### Core Functionality
- **User Management**: Registration, authentication, role-based access
- **Patient Records**: Complete medical history and personal information
- **Doctor Scheduling**: Availability management and appointment booking
- **Appointment System**: Conflict detection, automatic reminders
- **Medical Records**: Consultation notes, prescriptions, history
- **Real-time Notifications**: WebSocket-powered updates
- **Email Reminders**: Automated appointment notifications

### User Roles
- **Admin**: Full system access, user management
- **Doctor**: Schedule management, patient consultations
- **Patient**: Appointment booking, medical record access
- **Secondary User**: Limited access (family members, assistants)

## Architecture

### Backend (Spring Boot)
- RESTful API with Spring MVC
- JWT-based authentication
- Spring Security for authorization
- Spring Data JPA for data access
- PostgreSQL database
- WebSocket for real-time communication
- Scheduled tasks for reminders

### Frontend (JavaFX)
- Desktop application for doctors and admins
- Native look and feel
- Offline capability

### Frontend (React)
- Web application for patients
- Responsive design
- Modern user experience

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Patients
- `GET /api/patients` - Search patients (paginated)
- `POST /api/patients` - Create new patient
- `PUT /api/patients/{id}` - Update patient
- `GET /api/patients/{id}/dossier` - Get medical record

### Appointments
- `POST /api/rendezvous` - Book appointment
- `DELETE /api/rendezvous/{id}` - Cancel appointment
- `GET /api/rendezvous/patient/{id}` - Get patient appointments
- `GET /api/rendezvous/medecin/{id}` - Get doctor appointments

## Configuration

### Database
Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gindho
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### JWT
```properties
jwt.secret=your-secret-key-here
jwt.expiration=86400000
```

### Email
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

## Development

### Project Structure
```
backend/
├── src/main/java/com/gindho/
│   ├── model/          # JPA Entities
│   ├── repository/     # Data Access Layer
│   ├── service/        # Business Logic
│   ├── controller/     # REST Controllers
│   ├── security/       # Authentication & Authorization
│   ├── scheduler/      # Background Tasks
│   ├── dto/            # Data Transfer Objects
│   └── config/         # Configuration Classes
```

### Running Tests
```bash
cd backend
mvn test
```

## Deployment

### Backend
1. Build: `mvn clean package`
2. Run: `java -jar target/gindho-backend-1.0.0.jar`

### Frontend
Build static files and serve with web server

## Contributing
1. Create feature branch
2. Make changes
3. Run tests
4. Update documentation
5. Submit pull request

## License
MIT License

## Contact
For questions, please open an issue in the repository.
