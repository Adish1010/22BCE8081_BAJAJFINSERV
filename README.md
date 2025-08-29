# Bajaj Finserv Java Challenge Solution

This project is a Spring Boot application designed to solve the Bajaj Finserv Java Challenge. It includes a service to execute a SQL query (for odd registration numbers) and interact with an external API for webhook generation and solution submission.

## Setup Instructions

### Step 1: Set up GitHub Repository (Manual)
1. Go to GitHub and create a new repository (e.g., `bajaj-finserv-java-challenge`).
2. Make it public.
3. Initialize with a README (this file).

### Step 2: Local Project Setup
1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd bajaj-finserv-java-challenge
   ```
2. **Install Java Development Kit (JDK 17 or higher):** Ensure JDK 17 is installed and configured in your system's PATH.
3. **Install Apache Maven:**
   * Download Maven from [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi).
   * Extract the archive to a directory (e.g., `D:\Apace\apache-maven-3.9.11`).
   * Set `JAVA_HOME` environment variable to your JDK path.
   * Create `M2_HOME` environment variable and set it to your Maven installation directory (e.g., `D:\Apace\apache-maven-3.9.11`).
   * Add `%M2_HOME%\bin` to your system's `Path` environment variable.
   * **Important:** After setting environment variables, open a *new* terminal/command prompt.
   * Verify installation: `mvn -version`

### Step 3: Build the Project
Navigate to the project's root directory in your terminal and run:
```bash
D:\Apace\apache-maven-3.9.11\bin\mvn.cmd clean install
```
This command will download all necessary dependencies and build the project.

### Step 4: Run the Application
You can run the Spring Boot application using the Maven Spring Boot plugin:
```bash
D:\Apace\apache-maven-3.9.11\bin\mvn.cmd spring-boot:run
```
The application will start on the port configured in `src/main/resources/application.properties` (default is 8080).

## Project Directory Structure

```
bajaj-finserv-java-challenge/
├── pom.xml
├── README.md
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── bajajfinserv/
│       │           └── javachallenge/
│       │               ├── JavaChallengeApplication.java
│       │               ├── dto/
│       │               │   ├── WebhookRequest.java
│       │               │   ├── WebhookResponse.java
│       │               │   └── SolutionRequest.java
│       │               └── service/
│       │                   ├── ApiClientService.java
│       │                   ├── SqlSolutionService.java
│       │                   └── ChallengeService.java
│       └── resources/
│           └── application.properties
├── target/
└── .gitignore
```

## Application Properties (`src/main/resources/application.properties`)

```properties
server.port=8080
logging.level.com.bajajfinserv=INFO
logging.level.org.springframework.web.client=DEBUG

# API Configuration
api.base.url=https://bfhldevapigw.healthrx.co.in/hiring
api.generate.webhook=/generateWebhook/JAVA
api.test.webhook=/testWebhook/JAVA
```

## SQL Question 1 Solution (for Odd Registration Numbers)

The `SqlSolutionService` implements the solution for SQL Question 1.

**Problem:** Find the highest salary that was credited to an employee, but only for transactions that were not made on the 1st day of any month. Display the `SALARY`, `NAME` (First Name + Last Name), `AGE` (in years based on current date), and `DEPARTMENT_NAME`.

**Generated SQL Query:**
```sql
SELECT 
    p.AMOUNT AS SALARY,
    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME,
    FLOOR(DATEDIFF(CURDATE(), e.DOB) / 365.25) AS AGE,
    d.DEPARTMENT_NAME
FROM PAYMENTS p
JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE DAY(p.PAYMENT_TIME) != 1
ORDER BY p.AMOUNT DESC
LIMIT 1
```

**Explanation:**
1. `SELECT` clause retrieves the `AMOUNT` (aliased as `SALARY`), `FIRST_NAME` and `LAST_NAME` concatenated as `NAME`, the calculated `AGE` (using `DATEDIFF` and `FLOOR` for full years), and `DEPARTMENT_NAME`.
2. `FROM PAYMENTS p`: Starts the query from the `PAYMENTS` table.
3. `JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID`: Joins with the `EMPLOYEE` table on `EMP_ID` to get employee details.
4. `JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID`: Joins with the `DEPARTMENT` table on `DEPARTMENT_ID` to get the department name.
5. `WHERE DAY(p.PAYMENT_TIME) != 1`: Filters out any payments made on the 1st day of the month.
6. `ORDER BY p.AMOUNT DESC`: Sorts the results in descending order based on the `AMOUNT` to get the highest salary first.
7. `LIMIT 1`: Restricts the output to only the top record, which will be the highest salary satisfying the conditions.