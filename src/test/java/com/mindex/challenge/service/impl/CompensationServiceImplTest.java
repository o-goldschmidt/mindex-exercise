package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

/**
 * This class tests the functionality of the Compensation service.
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String employeeUrl;
    private String compensationCreateUrl;
    private String compensationReadUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        compensationReadUrl = "http://localhost:" + port + "/compensation/{employeeId}";
        compensationCreateUrl = "http://localhost:" + port + "/compensation";
    }

    /**
     * Tests the functionality of the create and read methods for the Compensation.
     */
    @Test
    public void testCreateRead() {
        Compensation testCompensation = new Compensation();
        Date date = new Date();

        // Creating a new employee - a new employee MUST be created to generate a new UUID and test the functionality
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Saving the new employee
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        // Adding the data to the new Compensation object
        testCompensation.setEmployee(createdEmployee);
        testCompensation.setSalary(100000.00);
        testCompensation.setEffectiveDate(date);

        // Testing the insert method
        Compensation createdCompensation = restTemplate.postForEntity(compensationCreateUrl, testCompensation,
                Compensation.class).getBody();
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Testing the read method
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class,
                createdEmployee.getEmployeeId()).getBody();
        assertEquals(readCompensation.getEmployee().getEmployeeId(), createdCompensation.getEmployee().getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation);
    }

    /**
     * Asserts the equality of all the fields between the expected and actual Compensation objects
     * @param expected
     * @param actual
     */
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getSalary(), expected.getSalary());
    }
}
