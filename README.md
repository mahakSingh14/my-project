# Secure Asset Management System 🚀

A robust, enterprise-grade full-stack web application designed for secure user asset tracking and management. Built during BScIT (Final Year) and optimized for production standards.

## 🛠️ Tech Stack
- **Backend:** Java (Spring Boot) / Maven
- **Database:** MongoDB Atlas Cluster
- **Security:** Bcrypt Cryptographic Hashing, JWT Authentication
- **Build Tool:** Maven (`pom.xml`)

## 🔒 Key Features & Security Enhancements
- **Cryptographic Hashing:** Enforced strict protection pipelines using Bcrypt password hashing to eliminate plain-text breaches.
- **RESTful Endpoints:** Structured secure, stateful, and role-based REST APIs.
- **Robust Database Layer:** Data securely coupled into MongoDB Atlas with optimized connection pooling.
- **OWASP Top 10 Mitigation:** Built-in safeguards against brute-force attacks and unauthorized endpoint access.

## 📂 Project Structure
```text
├── .mvn/               # Maven wrapper configuration
├── src/                # Main application source code
│   ├── main/java       # Controllers, Services, Models, and Security Config
│   └── main/resources  # Application properties and database configs
└── pom.xml             # Project dependencies and build metadata
```

## ⚙️ Installation & Setup
1. Clone the repository: `git clone https://github.com`
2. Update database credentials in `application.properties` or `.env`.
3. Build and run the project using Maven: `./mvnw spring-boot:run`
