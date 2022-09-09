package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

/**
 * Service for Compensation
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
public interface CompensationService {
    Compensation create(Compensation compensation);
    Compensation read(String employeeId);
}
