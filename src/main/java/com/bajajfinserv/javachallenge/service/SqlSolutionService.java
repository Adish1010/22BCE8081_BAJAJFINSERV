package com.bajajfinserv.javachallenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SqlSolutionService {
    
    private static final Logger logger = LoggerFactory.getLogger(SqlSolutionService.class);
    
    public String generateSqlSolution() {
        logger.info("Generating SQL solution for Question 1 (Odd registration number)");
        
        // SQL Query to find the highest salary that was credited to an employee,
        // but only for transactions that were not made on the 1st day of any month
        String sqlQuery = """
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
            """;
        
        logger.info("Generated SQL query: {}", sqlQuery.trim());
        return sqlQuery.trim();
    }
    
    public String getSolutionExplanation() {
        return """
            SQL Query Explanation:
            1. SELECT clause gets: Amount as SALARY, concatenated name, calculated age, department name
            2. FROM PAYMENTS p - start with payments table
            3. JOIN EMPLOYEE e - get employee details
            4. JOIN DEPARTMENT d - get department name
            5. WHERE DAY(p.PAYMENT_TIME) != 1 - exclude 1st day of month payments
            6. ORDER BY p.AMOUNT DESC - sort by amount descending
            7. LIMIT 1 - get only the highest salary record
            """;
    }
}