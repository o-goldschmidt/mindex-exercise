package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * Controller for ReportingStructure
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    /**
     * Retrieves the ReportingStructure for employee by id
     * @param employeeId
     * @return the ReportingStructure object for the employee
     */
    @GetMapping("/reportingStructure/{employeeId}")
    @NotBlank
    public ReportingStructure read(@PathVariable String employeeId) {
        LOG.debug("Received ReportingStructure for employee with id [{}]", employeeId);

        return reportingStructureService.get(employeeId);
    }
}
