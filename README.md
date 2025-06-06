# Task Management Application

## Overview

The Task Management application is a web-based system. It enables users to create, read, update, delete, and search tasks through a responsive web interface. The application follows the Model-View-Controller (MVC) pattern and is built using **Spring Boot** for rapid development and robust backend functionality. Key features include:

- **CRUD Operations**: Create, view, edit, and delete tasks with fields: Title, Description, Due Date, Status, Remarks, Created On, Last Updated On, Created By, and Last Updated By.
- **Search Functionality**: Search tasks by title with case-insensitive partial matching.
- **Data Persistence**: Stores task and user data in a MySQL database with relational integrity and optimized queries.
- **User Tracking**: Tracks the user who created and last updated each task, along with timestamps.

The application uses **Spring Boot** with **JPA/Hibernate** for the backend, **Thymeleaf** with **HTML/CSS/JavaScript** (styled with Bootstrap) for the frontend, and **MySQL** for data storage. It is a **Multi-Page Application (MPA)** with server-side rendering, packaged as a JAR file, and hosted on a public GitHub repository for evaluation.

## Database Design

### ER Diagram

The database consists of two tables: `Users` and `Tasks`, with a one-to-many relationship.

**Text-Based ER Diagram**:

```
[Users] --(1:N)-- [Tasks]
Users: id (PK), name
Tasks: id (PK), title, description, due_date, status, remarks, created_on, last_updated_on, created_by (FK), last_updated_by (FK)
```

- **Users**: Stores user details with a unique `id` (primary key) and `name`.
- **Tasks**: Stores task details with a unique `id` (primary key), task attributes, and foreign keys `created_by` and `last_updated_by` referencing `Users.id`.
- **Relationship**: One user can create and update multiple tasks.

### Data Dictionary

| Table | Column Name | Data Type | Description | Constraints |
| --- | --- | --- | --- | --- |
| Users | id | BIGINT | Unique identifier for user | Primary Key, Auto-increment |
| Users | name | VARCHAR(100) | Name of the user | NOT NULL |
| Tasks | id | BIGINT | Unique identifier for task | Primary Key, Auto-increment |
| Tasks | title | VARCHAR(100) | Title of the task | NOT NULL |
| Tasks | description | TEXT | Detailed description of the task |  |
| Tasks | due_date | DATE | Due date for the task |  |
| Tasks | status | VARCHAR(50) | Status (e.g., Open, In Progress, Done) | NOT NULL |
| Tasks | remarks | TEXT | Additional remarks |  |
| Tasks | created_on | DATETIME | Timestamp of task creation | NOT NULL |
| Tasks | last_updated_on | DATETIME | Timestamp of last update |  |
| Tasks | created_by | BIGINT | ID of user who created the task | Foreign Key (Users.id), NOT NULL |
| Tasks | last_updated_by | BIGINT | ID of user who last updated the task | Foreign Key (Users.id) |

### Indexes Used

- **idx_task_title**: On `Tasks.title` to optimize search queries by title.

  ```sql
  CREATE INDEX idx_task_title ON tasks(title);
  ```
- **idx_task_status**: On `Tasks.status` to enhance filtering performance for status-based queries.

  ```sql
  CREATE INDEX idx_task_status ON tasks(status);
  ```

### Approach: DB-First vs. Code-First

- **Approach Used**: Code-First
- **Reason**: The Code-First approach was chosen to leverage Spring Boot’s JPA/Hibernate capabilities, which automatically generate the database schema from Java entity classes (`User` and `Task`). This approach accelerates development, ensures consistency between the application and database, and simplifies schema management through annotations (e.g., `@Entity`, `@Column`). It aligns with Spring Boot’s conventions, making it ideal for rapid prototyping and maintaining a clean, maintainable codebase. Hibernate also handles foreign key relationships and indexes efficiently, reducing manual SQL scripting.

## Structure of the Application

### Architecture

- **Type**: Multi-Page Application (MPA) with standard MVC server-side page rendering.
- **Reason**: An MPA was selected over a Single Page Application (SPA) for its simplicity, alignment with the assignment’s requirements, and compatibility with Thymeleaf’s server-side rendering. This approach ensures seamless integration with Spring Boot’s MVC framework, reduces client-side complexity, and provides a traditional web experience suitable for task management. The MVC pattern is implemented as follows:
  - **Model**: Java entities (`User`, `Task`) managed by Spring Data JPA repositories (`UserRepository`, `TaskRepository`).
  - **View**: Thymeleaf templates (`index.html`, `create-task.html`, `view-tasks.html`, `edit-task.html`, `search-tasks.html`) render the UI.
  - **Controller**: `TaskController` handles HTTP requests, processes business logic, and returns Thymeleaf views.

### Directory Structure

```
TaskManagementApp/
├── src/
│   ├── main/
│   │   ├── java/com/oritso/taskmanagement/
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   └── Task.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── TaskRepository.java
│   │   │   ├── controller/
│   │   │   │   ├── TaskController.java
│   │   │   ├── TaskManagementApplication.java
│   │   ├── resources/
│   │   │   ├── templates/
│   │   │   │   ├── index.html
│   │   │   │   ├── create-task.html
│   │   │   │   ├── view-tasks.html
│   │   │   │   ├── edit-task.html
│   │   │   │   └── search-tasks.html
│   │   │   ├── static/
│   │   │   │   ├── css/style.css
│   │   │   │   └── js/script.js
│   │   │   └── application.properties
├── pom.xml
└── README.md
```

## Frontend Structure

### Frontend Technologies Used

- **Technologies**: HTML, CSS (Bootstrap 5.3 via CDN), JavaScript, and Thymeleaf for server-side rendering.
- **Components**:
  - **HTML**: Structures the content of Thymeleaf templates, dynamically populated with data.
  - **CSS**: Bootstrap provides responsive design and consistent styling. Custom styles are defined in `static/css/style.css` for minor tweaks.
  - **JavaScript**: Client-side validation and interactivity are implemented in `static/js/script.js`, ensuring fields like title and due date are validated before submission.
  - **Thymeleaf**: Integrates with Spring Boot to render dynamic content, using attributes like `th:each` for iterating over task lists and `th:value` for form binding.
- **Pages**:
  - `index.html`: Homepage with navigation to view, create, and search tasks.
  - `create-task.html`: Form to create a new task.
  - `view-tasks.html`: Table displaying all tasks with edit and delete options.
  - `edit-task.html`: Form to update an existing task.
  - `search-tasks.html`: Form to search tasks by title with results displayed in a table.

### Reason for Choice

- **Web-Based Frontend**: A web page frontend was chosen over a mobile application for its universal accessibility, ease of deployment, and alignment with the assignment’s focus on web technologies. Thymeleaf was selected for its tight integration with Spring Boot, enabling server-side rendering within the MVC pattern, which simplifies development and enhances maintainability.
- **Bootstrap**: Used to accelerate UI development of a professional, responsive UI, minimizing custom CSS.
- **JavaScript**: Improves user experience with validation and interactivity, ensuring data integrity before server submission.

## Build and Install

### Environment Details and Dependencies

- **Prerequisites**:
  - Java 17 or later)
  - Maven (3.8+)
  - MySQL (8.0+)
  - Git
- **Dependencies** (defined in `pom.xml`):
  - `spring-boot-starter-web`: For MVC and REST support.
  - `spring-boot-starter-data-jpa`: For database access with Hibernate.
  - `spring-boot-starter-thymeleaf`: For server-side rendering.
  - `mysql-connector-j:8.0.33`: MySQL driver.
  - `spring-boot-starter-test`: For testing (optional for production).
- **Operating System**: Tested on Linux (Ubuntu 22.04) and Windows 10. Compatible with other systems supporting Java.

### Instructions to Compile or Build the Project

1. Clone the repository:

   ```bash
   git clone https://github.com/<your-username>/TaskManagementApp.git
   cd TaskManagementApp
   ```
2. Build the project using Maven:

   ```bash
   mvn clean package
   ```
3. The build process generates an executable JAR file: `target/task-management-0.0.1-SNAPSHOT.jar`.

### Instructions to Run or Install the Project

1. **Set Up MySQL**:
   - Create the database:

     ```sql
     CREATE DATABASE task_management;
     ```
   - Configure database connection in `src/main/resources/application.properties`:

     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/task_management?useSSL=false&serverTimezone=UTC
     spring.datasource.username=root
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```
   - JPA will automatically create tables based on entity classes.
2. **Run the Application**:
   - Execute the JAR file:

     ```bash
     java -jar target/task-management-0.0.1-SNAPSHOT.jar
     ```
   - Alternatively, run with Maven:

     ```bash
     mvn spring-boot:run
     ```
3. **Access the Application**:
   - Open a browser and navigate to `http://localhost:8080`.
   - Use the homepage to create, view, edit, delete, or search tasks.

## General Documentation

### Deployment Architecture

- **Local Deployment**: Runs as a standalone Spring Boot application with an embedded Tomcat server and MySQL database. The JAR file is executed directly, connecting to a local or managed MySQL instance.
- **Production Consideration**: Deploy on a cloud platform (e.g., AWS Elastic Beanstalk) with MySQL (e.g., AWS RDS). Use Nginx as a reverse proxy for load balancing, and configure auto-scaling for high availability.

### Data Migration

- **Approach**: Spring Boot’s JPA/Hibernate manages schema creation and updates via `spring.jpa.hibernate.ddl-auto=update`. For production, use Flyway or Liquibase for controlled migrations.
- **Current State**: The Code-First approach generates the schema automatically. No manual migrations are required for the initial setup.

### Site Readiness

- **UI**: Tested for responsiveness with Bootstrap on Chrome, Firefox, and Edge. Forms include client-side (JavaScript) and server-side (Spring validation) checks.
- **Dependencies**: Bootstrap is loaded via CDN, and MySQL connector is included in the JAR.
- **Security**: Basic input validation is implemented. For production, add Spring Security for authentication and CSRF protection.

### User Acceptance Testing

- **Test Cases**:
  - **Create Task**: Verify task creation with all fields, correct user assignment, and timestamps.
  - **Read Tasks**: Ensure all tasks are displayed with accurate details.
  - **Update Task**: Confirm updates reflect new values and timestamps.
  - **Delete Task**: Verify task removal from database and UI.
  - **Search Task**: Test partial, case-insensitive title search with correct results.
- **Tools**: Manual testing with browsers and MySQL queries. Automated tests can be added using JUnit and Spring Boot Test.

### Go Live / Production

- **Steps**:
  1. Deploy the JAR to a cloud server (e.g., AWS EC2 or Elastic Beanstalk).
  2. Configure MySQL on a managed service.
  3. Set environment variables for database credentials.
  4. Use Nginx for reverse proxy and load balancing.
  5. Monitor with Spring Boot Actuator and MySQL metrics.
- **Considerations**:
  - Ensure Bootstrap CDN is accessible or host static assets locally.
  - Implement HTTPS with SSL/TLS.
  - Schedule regular database backups.

### Risks and Issues

- **Risk**: Lack of authentication simplifies the demo but is unsuitable for production.
