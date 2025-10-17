# PoisePMS - Project Management System

This is a project management system, built for an engineering company called Poise. It is a full-stack web application for managing construction projects, built with Java Spring Boot and PostgreSQL. This project demonstrates evolution from a console-based application to a modern web application with a focus on clean architecture and code reusability.

**Live Demo:** [https://poisepms.onrender.com](https://poisepms.onrender.com)

## Overview

This application allows the engineering firm to:

- Manage project details, deadlines, and financial information
- Manage people associated with projects, including: architects, contractors, customers, engineers and managers
- Monitor and update the status of projects (complete or incomplete), and identify any overdue projects
- Peform CRUD operations on both projects and people

This application was built as a capstone project demonstrating database design, Java programming, and software architecture principles.

## Table of Contents

- [Features](#features)
- [Testing](#testing)
- [Project Evolution](#project-evolution)
- [Technology Stack](#technology-stack)
- [Database Schema](#database-schema)
- [Future Enhancements](#future-enhancements)

## Features

### Project Management

- **Create Projects**: Add new projects with details including: name, building type, address, fees, deadlines
- **Update Projects**: Modify any project information as the project progresses
- **Delete Projects**: Remove projects from the system with confirmation prompts
- **Search Projects**: Find projects by ID or name with options of a summary, or detailed information display
- **Finalise Projects**: Mark projects as complete with completion date tracking

### Personnel Management

- **Add Personnel**: Add people associated with projetcs, including: architects, contractors, customers, engineers, and managers
- **Update Personnel**: Modify contact information and role assignments
- **Delete Personnel**: Remove personnel, with checks regarding the projects they are associated with before deletion
- **Role-Based Search**: Find personnel by specific roles for project assignment

### Project Monitoring

- **Incomplete Projects**: View all projects that are incomplete
- **Overdue Projects**: Identify projects past their deadlines
- **Project Summary**: Quick overview of all projects with status indicators

### Data Integrity

- **Relationship Management**: Checks deletion of personnel assigned to active projects. Cannot delete customer that are associated with live projects.
- **Input Validation**: Email format checking and required field enforcement
- **SQL Security**: Prepared statements prevent SQL injection attacks

## Testing

This project includes comprehensive test coverage across all architectural layers, demonstrating professional testing practices and quality assurance.

### Test Coverage Summary

- **Repository Layer**: 100% coverage - Database operations tested against PostgreSQL
- **Service Layer**: 100% coverage - Business logic validated with mocked dependencies
- **Controller/Web Layer**: 80% coverage - HTTP endpoints tested with MockMvc
- **Overall Project Coverage**: ~93%

### Service Layer Tests (15 Unit Tests)

The service layer contains the core business logic and has complete test coverage using JUnit 5 and Mockito:

**PersonService Tests (6 tests)**

- `updatePerson()` - Validates partial updates and handles not found scenarios
- `deletePerson()` - Ensures safe deletion with existence validation
- `createPerson()` - Tests creation success and repository failure handling

**ProjectService Tests (9 tests)**

- `deleteProject()` - Validates deletion with existence checks
- `updateProject()` - Tests complex partial updates across 14 fields
- `createProject()` - Validates creation with multiple related entities
- `finaliseProject()` - Enforces business rules (prevents double finalisation)

### Testing Strategy

**Unit Tests (Service Layer)**

- Use Mockito to mock repository dependencies
- Test business logic in isolation without database
- Validate both success paths and failure scenarios
- Verify edge cases and business rule enforcement
- Fast execution (no database startup required)

**Integration Tests (Repository Layer)**

- Test against real PostgreSQL database
- Validate SQL queries and data persistence
- Ensure database constraints work correctly
- Test foreign key relationships and cascading behaviour

**Web Layer Tests (Controller Layer)**

- Use MockMvc for HTTP endpoint testing
- Validate request/response handling
- Test form validation and error responses
- Ensure proper redirects and status codes

### CI/CD Integration

Tests are automatically executed on every push through GitHub Actions:

- All 15+ tests must pass before deployment
- Failed tests block the deployment pipeline
- Ensures production quality with every release
- View test results in GitHub Actions tab

This testing approach demonstrates:

- **Test-Driven Development** principles
- **Mocking strategies** for isolated unit testing
- **Integration testing** for data layer verification
- **Professional CI/CD practices** used in production environments

## Project Evolution

This project showcases my development journey from console application to production web application:

### Phase 1: Console Application (`console/` package)

- Command-line interface with menu-driven navigation
- Original implementation demonstrating core Java and JDBC skills
- Direct database operations with user input validation
- Files: `MainMenu.java`, `ProjectsManager.java`, `PeopleManager.java`

### Phase 2: Web Application (Current - `web/` package)

- Spring Boot framework with MVC architecture
- Thymeleaf server-side rendering for dynamic HTML
- RESTful URL routing conventions
- Bootstrap-powered responsive UI
- Enhanced user experience with form validation and error handling

### Key Architectural Achievement

**Code Reusability:** The same `repository/` and `service/` layers power both the console and web versions—demonstrating proper separation of concerns and clean architecture principles.

## Technology Stack

### Backend

- **Java 17** - Core programming language with modern features
- **Spring Boot 3.x** - Web framework with dependency injection
- **Thymeleaf** - Server-side templating engine
- **JDBC (Java Database Connectivity)** - Database connectivity and operations
- **PostgreSQL Connector** - PostgreSQL database driver

### Frontend

- **HTML & CSS** - Structure and styling
- **Bootstrap 5.3** - Responsive UI framework
- **Thymeleaf** - Dynamic content rendering with form binding

### Development Tools

- **Maven** - Dependency management and build automation
- **Git & GitHub** - Version control and collaboration
- **Render** - Cloud platform for deployment
- **PostgreSQL (Render)** - Managed database hosting
- **IntelliJ IDEA** - IDE

### Architecture Patterns

- **Repository Pattern** - Abstracts data access logic
- **Service Layer Pattern** - Encapsulates business logic
- **MVC Pattern** - Separates concerns in web layer (Model-View-Controller)
- **DTO Pattern** - Data Transfer Objects for API boundaries
- **Dependency Injection** - Loose coupling between components

**Separation of Concerns:** Each package has a single, well-defined responsibility:

- `console/` - User interaction via command line
- `web/` - User interaction via HTTP/HTML
- `model/` - Domain entities representing business concepts
- `repository/` - Database operations (CRUD)
- `service/` - Business rules and validation

**Testability:** Each layer can be tested independently

**Flexibility:** Can add REST API controllers without changing service/repository layers

## Database Schema

The system uses a PostgreSQL database with two main tables designed to handle project and personnel management efficiently.

```sql
-- People table
CREATE TABLE IF NOT EXISTS people (
    person_id SERIAL PRIMARY KEY,
    person_name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(255),
    address VARCHAR(500),
    role VARCHAR(100) NOT NULL
);

-- Projects table
CREATE TABLE IF NOT EXISTS projects (
    project_id SERIAL PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    building_type VARCHAR(100) NOT NULL,
    project_address VARCHAR(500),
    ERF_number INTEGER,
    total_fee DECIMAL(15, 2),
    amount_paid_to_date DECIMAL(15, 2),
    project_deadline DATE,
    architect_id INTEGER,
    contractor_id INTEGER,
    customer_id INTEGER NOT NULL,
    engineer_id INTEGER,
    manager_id INTEGER,
    project_finalised BOOLEAN DEFAULT FALSE,
    completion_date DATE,
    FOREIGN KEY (architect_id) REFERENCES people(person_id) ON DELETE SET NULL,
    FOREIGN KEY (contractor_id) REFERENCES people(person_id) ON DELETE SET NULL,
    FOREIGN KEY (customer_id) REFERENCES people(person_id) ON DELETE RESTRICT,
    FOREIGN KEY (engineer_id) REFERENCES people(person_id) ON DELETE SET NULL,
    FOREIGN KEY (manager_id) REFERENCES people(person_id) ON DELETE SET NULL
);
```

### Relationships

- **One-to-Many**: One person can be assigned to multiple projects in the same or different roles
- **Required Relationship**: Every project must have a customer (customer_id is NOT NULL)
- **Optional Relationships**: During project creation, projects can optionally have architects, contractors, engineers, and managers

## Future Enhancements

### Advanced Features

- **User Authentication**: Role-based access control for different user types
- **Reporting System**: Generate PDF reports for project summaries and financial statements
