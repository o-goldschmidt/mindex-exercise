package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * This class tests the functionality of the ReportingStructure service.
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureUrl;
    private String employeeUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{employeeId}";
    }

    /**
     * Tests the read functionality for the ReportingStructure.
     */
    @Test
    public void testRead() {
        ReportingStructure testReportingStructure = new ReportingStructure();

        // Creating a new employee - a new employee MUST be created to generate a new UUID and test the functionality
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Creating a new employee
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        // Adding the new employee to the report structure
        testReportingStructure.setEmployee(createdEmployee);

        // Checking the read functionality
        ReportingStructure createdReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class,
                createdEmployee.getEmployeeId()).getBody();
        assertEquals(testReportingStructure.getEmployee().getEmployeeId(), createdReportingStructure.getEmployee().getEmployeeId());
        assertReportingStructureEquivalence(testReportingStructure, createdReportingStructure);
    }

    /**
     * Asserts the equality of all the fields between the expected and actual ReportingStructure objects
     * @param expected
     * @param actual
     */
    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
        assertEquals(expected.getEmployee().getLastName(), actual.getEmployee().getLastName());
        assertEquals(expected.getEmployee().getDepartment(), actual.getEmployee().getDepartment());
        assertEquals(expected.getEmployee().getPosition(), actual.getEmployee().getPosition());
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
    }
}
