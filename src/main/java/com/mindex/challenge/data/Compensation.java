package com.mindex.challenge.data;

import java.util.Date;

/**
 * Compensation entity is used to track the salary of an employee.
 *
 * @author Otto Goldschmidt
 * @version 1.0
 * @since 1.0
 */
public class Compensation {
    private Employee employee;
    private double salary;
    private Date effectiveDate;

    public Compensation(Employee employee, double salary, Date effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public Compensation() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
