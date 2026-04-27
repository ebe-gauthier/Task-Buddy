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

##  screenshoots

## Frontend
<img width="788" height="374" alt="image" src="https://github.com/user-attachments/assets/24603fcf-17d0-418b-bef0-d320163ef8cc" />

<img width="950" height="479" alt="image" src="https://github.com/user-attachments/assets/3f672a14-898e-40c3-83d0-94f1f91c5ffb" />

<img width="949" height="475" alt="image" src="https://github.com/user-attachments/assets/7ebd8ffc-502e-403c-abea-7524af0b681b" />

<img width="956" height="465" alt="image" src="https://github.com/user-attachments/assets/a4c31a9a-eec0-4e65-9134-fef75a75c355" />

<img width="956" height="497" alt="image" src="https://github.com/user-attachments/assets/543b108a-c2ca-4acf-a7e5-e47258ca1c3d" />

<img width="956" height="437" alt="image" src="https://github.com/user-attachments/assets/83d16ac5-055d-49f8-a7b1-ca378a3f91c2" />


## backend


## DATA
<img width="936" height="333" alt="image" src="https://github.com/user-attachments/assets/7c0c0273-175e-4753-accb-67847d835217" />
<img width="953" height="345" alt="image" src="https://github.com/user-attachments/assets/011cdf4a-133c-4904-8f14-8568b8c980b7" />

