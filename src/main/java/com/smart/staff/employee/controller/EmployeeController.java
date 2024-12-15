package com.smart.staff.employee.controller;

import com.smart.staff.dto.response.ApiResponse;
import com.smart.staff.dto.response.ErrorDetails;
import com.smart.staff.employee.entity.Employee;
import com.smart.staff.employee.exception.EmployeeNotFoundException;
import com.smart.staff.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        log.info("API: Create employee");
        try {
            Employee createdEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.ok(ApiResponse.success(createdEmployee));
        } catch (Exception e) {
            log.error("Error creating employee: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error creating employee", e.getMessage())));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id) {
        log.info("API: Get employee by ID");
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(ApiResponse.success(employee));
        } catch (EmployeeNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(ApiResponse.failure(new ErrorDetails("Not Found", e.getMessage())));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
        log.info("API: Get all employees");
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(ApiResponse.success(employees));
        } catch (Exception e) {
            log.error("Error fetching employees: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error fetching employees", e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        log.info("API: Update employee");
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(ApiResponse.success(updatedEmployee));
        } catch (EmployeeNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(ApiResponse.failure(new ErrorDetails("Not Found", e.getMessage())));
        } catch (Exception e) {
            log.error("Error updating employee: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error updating employee", e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        log.info("API: Delete employee");
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (EmployeeNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(ApiResponse.failure(new ErrorDetails("Not Found", e.getMessage())));
        } catch (Exception e) {
            log.error("Error deleting employee: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error deleting employee", e.getMessage())));
        }
    }
}
