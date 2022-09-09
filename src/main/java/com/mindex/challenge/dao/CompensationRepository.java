package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Compensation entity
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface CompensationRepository extends MongoRepository<Compensation, Employee> {

    @Query(" {'employee.employeeId' :  ?0 }")
    Compensation findCompensationByEmployee(String employeeId);
}
