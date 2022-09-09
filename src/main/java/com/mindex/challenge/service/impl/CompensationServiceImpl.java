package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CompensationService.
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Creates a new Compensation object
     * @param compensation
     * @return the newly created Compensation object
     */
    @Override
    public Compensation create(Compensation compensation) {
        // fetching the employee object to check if the user entered a valid UUID
        Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId());

        // throwing an error if the employee with the specified UUID does not exist
        if(employee == null) {
            throw new IllegalArgumentException("ERROR: No employee with that ID does not exist.");
        }
        LOG.debug("Creating compensation for employee [{}]", compensation.getEmployee());

        // fetching the Compensation object for the existing employee to prevent accidental overwriting
        Compensation existingCompensation = compensationRepository.findCompensationByEmployee(employee.getEmployeeId());

        // throwing an error if the employee already has a Compensation object
        if(existingCompensation != null) {
            throw new RuntimeException("ERROR: This employee already has an existing Compensation.");
        }

        // correcting the employee object to make sure that the user did not "accidentally" enter the right id but
        // wrong name, position, etc.
        compensation.setEmployee(employee);

        return compensationRepository.insert(compensation);
    }

    /**
     * Fetches an existing Compensation object for an employee
     * @param employeeId
     * @return existing Compensation object for the desired employee
     */
    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Fetching compensation for employee with id [{}]", employeeId);

        // fetching the employee object to check if the user entered a valid UUID
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        // throwing an error if the employee with the specified UUID does not exist
        if(employee == null) {
            throw new IllegalArgumentException("ERROR: No employee with that ID does not exist.");
        }

        // fetching the compensation object
        Compensation compensation = compensationRepository.findCompensationByEmployee(employeeId);

        // throwing an exception if the employee does not have a compensation
        if(compensation == null) {
            throw new RuntimeException("This employee does not have a Compensation.");
        }

        return compensation;
    }
}
