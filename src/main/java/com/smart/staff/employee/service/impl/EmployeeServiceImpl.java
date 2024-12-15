package com.smart.staff.employee.service.impl;

import com.smart.staff.employee.entity.Employee;
import com.smart.staff.employee.exception.EmployeeNotFoundException;
import com.smart.staff.employee.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.smart.staff.employee.constants.EmployeeConstants.EMPLOYEE_NOT_FOUND_WITH_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl {

    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        log.info("Creating new employee: {}", employee.getName());
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(EMPLOYEE_NOT_FOUND_WITH_ID, id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });
    }

    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        log.info("Updating employee with ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(EMPLOYEE_NOT_FOUND_WITH_ID, id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setDateOfJoining(updatedEmployee.getDateOfJoining());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(EMPLOYEE_NOT_FOUND_WITH_ID, id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });

        employeeRepository.delete(employee);
    }
}
