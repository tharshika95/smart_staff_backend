package com.smart.staff.department.controller;

import com.smart.staff.department.entity.Department;
import com.smart.staff.department.service.DepartmentService;
import com.smart.staff.dto.response.ApiResponse;
import com.smart.staff.dto.response.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Department>> createDepartment(@RequestBody Department department) {
        try {
            log.info("Creating new department: {}", department);
            Department savedDepartment = departmentService.createDepartment(department);
            return ResponseEntity.ok(ApiResponse.success(savedDepartment));
        } catch (Exception e) {
            log.error("Error while creating department: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while creating department",e.getMessage())));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> getDepartmentById(@PathVariable Long id) {
        try {
            log.info("Fetching department with id: {}", id);
            Department department = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(ApiResponse.success(department));
        } catch (Exception e) {
            log.error("Error while fetching department with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while fetching department with id",e.getMessage())));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
        try {
            log.info("Fetching all departments");
            List<Department> departments = departmentService.getAllDepartments();
            return ResponseEntity.ok(ApiResponse.success(departments));
        } catch (Exception e) {
            log.error("Error while fetching all departments: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while fetching all departments",e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        try {
            log.info("Updating department with id {}: {}", id, department);
            Department updatedDepartment = departmentService.updateDepartment(id, department);
            return ResponseEntity.ok(ApiResponse.success(updatedDepartment));
        } catch (Exception e) {
            log.error("Error while updating department with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while updating department with id",e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable Long id) {
        try {
            log.info("Deleting department with id: {}", id);
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok(ApiResponse.success("Department deleted successfully"));
        } catch (Exception e) {
            log.error("Error while deleting department with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while deleting department with id",e.getMessage())));
        }
    }
}
