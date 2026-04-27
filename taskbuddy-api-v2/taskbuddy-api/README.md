# TaskBuddy API — Spring Boot + MongoDB

A RESTful backend for the TaskBuddy task management application.
Built with **Spring Boot 3**, **Spring Data MongoDB**, and **Lombok**.

---

## Prerequisites

| Tool | Version |
|------|---------|
| Java JDK | 17 or higher |
| Maven | 3.6+ (or use included `mvnw`) |
| MongoDB | Running locally on port 27017 |

---

## How to Run

### 1. Start MongoDB
Make sure MongoDB is running locally:
```bash
# macOS (Homebrew)
brew services start mongodb-community

# Windows — start from Services or run:
mongod

# Linux
sudo systemctl start mongod
```

### 2. Run the Spring Boot App
```bash
# Option A: Using Maven Wrapper (recommended)
./mvnw spring-boot:run

# Option B: Using installed Maven
mvn spring-boot:run

# Option C: Build jar then run
mvn clean package
java -jar target/taskbuddy-api-0.0.1-SNAPSHOT.jar
```

Server starts at: **http://localhost:8080**

---

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks?status=TODO` | Filter by status |
| GET | `/api/tasks?priority=HIGH` | Filter by priority |
| GET | `/api/tasks/{id}` | Get one task by ID |
| POST | `/api/tasks` | Create a new task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |

---

## Sample Request (Postman or curl)

### Create a Task — POST /api/tasks
```json
{
  "title": "Study Spring Boot",
  "description": "Learn REST API",
  "priority": "HIGH",
  "status": "TODO"
}
```

### Valid Values
- **priority**: `LOW`, `MEDIUM`, `HIGH`
- **status**: `TODO`, `IN_PROGRESS`, `DONE`

---

## Project Structure

```
src/main/java/com/taskbuddy/taskbuddyapi/
├── TaskbuddyApiApplication.java   ← Entry point (main method)
├── controller/
│   └── TaskController.java        ← HTTP routes (@GetMapping, @PostMapping …)
├── service/
│   └── TaskService.java           ← Business logic
├── repository/
│   └── TaskRepository.java        ← MongoDB CRUD (auto-implemented)
└── model/
    └── Task.java                  ← Data structure / MongoDB document

src/main/resources/
└── application.properties         ← MongoDB URL, server port
```

---

## Connecting to Vue.js Frontend

In your Vue `src/services/api.js`, set:
```js
const API = axios.create({
  baseURL: 'http://localhost:8080/api/tasks'
})
```

CORS is already enabled for `http://localhost:5173` (Vite default port).
