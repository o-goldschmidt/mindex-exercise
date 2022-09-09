package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * Controller for Compensation
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /**
     * Retrieves an existing compensation for an employee
     * @param employeeId
     * @return an existing Compensation object
     */
    @GetMapping("/compensation/{employeeId}")
    @NotBlank
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for id [{}]", employeeId);

        return compensationService.read(employeeId);
    }

    /**
     * Creates a new compensation object for the desired employee
     * @param compensation
     * @return the newly created Compensation object
     */
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee [{}]", compensation.getEmployee());

        return compensationService.create(compensation);
    }
}
