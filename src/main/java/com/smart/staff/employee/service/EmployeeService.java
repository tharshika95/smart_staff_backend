package com.smart.staff.employee.service;

import com.smart.staff.employee.dto.request.EmployeeRequest;
import com.smart.staff.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeService {
    Employee createEmployee(EmployeeRequest request);
    Employee getEmployeeById(Long id);
    Page<Employee> getAllEmployees(int page, int size);
    Employee updateEmployee(Long id, EmployeeRequest request);
    void deleteEmployee(Long id);
    String saveEmployeeImage(Long employeeId, MultipartFile file) throws IOException;
}
