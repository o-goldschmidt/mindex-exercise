package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the ReportingStructureService.
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // Creating a variable to store the total number of direct reports
    int directReportsTotal = 0;

    /**
     * Fetches the ReportingStructure from the database.
     *
     * @param employeeId
     * @return the ReportingStructure object associated with the employee
     */
    @Override
    public ReportingStructure get(String employeeId) {
        LOG.debug("Fetching the ReportingStructure for an employee with id [{}]", employeeId);

        // Creating the ReportingStructure object
        ReportingStructure rs = new ReportingStructure();

        // Fetching the employee
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        // making sure that the employee exists
        if(employee == null) {
            throw new RuntimeException("ERROR: No employee with that ID does not exist.");
        }
        LOG.debug("Fetching the employee with id [{}]", employeeId);

        // Setting the fields
        rs.setEmployee(employee);
        rs.setNumberOfReports(getNumOfDirectReports(employee));

        // resetting the total number of reports
        directReportsTotal = 0;

        // Returning the ReportingStructure object
        return rs;
    }

    /**
     * Fetches all direct reports and recursively goes through each subsequent employee to determine if they have
     * direct reports.
     *
     * @param employee
     * @return the total number of direct reports, including direct reports of direct reports
     */
    private int getNumOfDirectReports(Employee employee) {
        // Getting the initial direct reports for an employee
        List<Employee> directReports = employee.getDirectReports();

        // Returning 0 if there are no direct reports
        if (directReports == null) {
            return directReportsTotal;
        }

        // going through each direct report employee to check if they also have direct reports
        for (Employee e : directReports) {
            // increasing the total number of direct reports
            directReportsTotal++;

            // fetching the employee by their id
            Employee emp = employeeRepository.findByEmployeeId(e.getEmployeeId());
            LOG.debug("Fetching an employee with id [{}]", e.getEmployeeId());
            // if the employee also has a direct report, repeat the method
            if (emp.getDirectReports() != null) {
                getNumOfDirectReports(emp);
            }
        }

        // returning the final number of reports
        return directReportsTotal;
    }
}
