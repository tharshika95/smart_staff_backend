package com.smart.staff.employee.controller;

import com.smart.staff.dto.response.ApiResponse;
import com.smart.staff.dto.response.ErrorDetails;
import com.smart.staff.employee.dto.request.EmployeeRequest;
import com.smart.staff.employee.entity.Employee;
import com.smart.staff.employee.exception.EmployeeNotFoundException;
import com.smart.staff.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody EmployeeRequest request) {
        log.info("API: Create employee");
        try {
            Employee createdEmployee = employeeService.createEmployee(request);
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
    public ResponseEntity<ApiResponse<Page<Employee>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("API: Get all employees with pagination - Page: {}, Size: {}", page, size);
        try {
            Page<Employee> employees = employeeService.getAllEmployees(page, size);
            return ResponseEntity.ok(ApiResponse.success(employees));
        } catch (Exception e) {
            log.error("Error fetching employees: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error fetching employees", e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        log.info("API: Update employee");
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, request);
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

    @GetMapping("/generateEmpId")
    public ResponseEntity<ApiResponse<String>> generateNextEmpId() {

        try {
            String imagePath = employeeService.generateNextEmpId();
            return ResponseEntity.ok(ApiResponse.success(imagePath));
        } catch (Exception e) {
            log.error("Error occurred at generateNextEmpId method", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure(new ErrorDetails("Image Upload Failed", e.getMessage())));
        }
    }


    @PostMapping("/{employeeId}/upload-image")
    public ResponseEntity<ApiResponse<String>> uploadEmployeeImage(
            @PathVariable String employeeId,
            @RequestParam("file") MultipartFile file) {

        try {
            String imagePath = employeeService.saveEmployeeImage(employeeId, file);
            return ResponseEntity.ok(ApiResponse.success(imagePath));
        } catch (Exception e) {
            log.error("Error uploading image for employee {}: {}", employeeId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure(new ErrorDetails("Image Upload Failed", e.getMessage())));
        }
    }

    @DeleteMapping("/{employeeId}/remove-image")
    public ResponseEntity<ApiResponse<String>> removeEmployeeImage(@PathVariable String employeeId) {
        try {
            String message = employeeService.deleteEmployeeImage(employeeId);
            return ResponseEntity.ok(ApiResponse.success(message));
        } catch (Exception e) {
            log.error("Error removing image for employee {}: {}", employeeId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.failure(new ErrorDetails("Image Removal Failed", e.getMessage())));
        }
    }

    @GetMapping("/employees/images/{employeeId}.png")
    public ResponseEntity<?> getEmployeeImage(@PathVariable String employeeId) {
        // Define the folder where images are stored outside the resources
        String imageFolderPath = "uploads/images/employees"; // This folder is outside resources

        // Construct the full path to the image
        Path imagePath = Paths.get(imageFolderPath, employeeId + ".png");

        // Check if the image file exists
        if (!imagePath.toFile().exists()) {
            return new ResponseEntity<>("Image not found for employee ID: " + employeeId, HttpStatus.NOT_FOUND);
        }

        // Serve the image as a resource
        Resource resource = new FileSystemResource(imagePath);

        // Set headers to disable caching (optional)
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-store, no-cache, must-revalidate, proxy-revalidate");
        headers.setPragma("no-cache");

        // Return the image as part of the response
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    }
