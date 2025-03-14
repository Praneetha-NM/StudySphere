# StudySphere

StudySphere is a collaborative group study platform designed to help students manage group discussions, schedule assignments, and stay organized with a shared calendar. Built using JavaFX, Spring Boot, and MongoDB, StudySphere provides an interactive environment for study groups to communicate and plan effectively.

## Features
- **User Authentication**: Secure login and registration for users.
- **Group Creation & Management**: Create and manage study groups.
- **Messaging Platform**: Real-time messaging within groups.
- **Calendar & Task Scheduling**: Schedule assignments and view deadlines.
- **UI Components**: JavaFX-based interface for smooth navigation.

## Tech Stack
- **Frontend**: JavaFX
- **Backend**: Spring Boot
- **Database**: MongoDB
- **Build Tool**: Maven

## Installation
### Prerequisites
- Java 17+
- MongoDB installed and running
- Maven installed

### Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/Praneetha-NM/StudySphere.git
   cd StudySphere
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Start the backend server:
   ```sh
   cd backend
   mvn spring-boot:run
   ```
4. Run the JavaFX application:
   ```sh
   cd frontend
   mvn javafx:run
   ```

## API Endpoints
| Method | Endpoint            | Description                 |
|--------|--------------------|-----------------------------|
| POST   | `/api/auth/register` | Register a new user        |
| POST   | `/api/auth/login`    | Login user                 |
| POST   | `/api/groups`        | Create a new study group   |
| GET    | `/api/groups`        | Fetch all study groups     |
| POST   | `/api/messages`      | Send a message in a group  |

## Usage
- Create an account and log in.
- Join or create a study group.
- Communicate with peers using the messaging feature.
- Schedule and view assignments in the calendar.


## Contact
For any queries, feel free to reach out:
- GitHub: [Praneetha-NM](https://github.com/Praneetha-NM)
- Email: [praneetha7597@gmail.com](mailto:praneetha7597@gmail.com)

