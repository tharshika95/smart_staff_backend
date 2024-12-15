package com.smart.staff.department.service.impl;

import com.smart.staff.department.entity.Department;
import com.smart.staff.department.repo.DepartmentRepository;
import com.smart.staff.department.service.DepartmentService;
import com.smart.staff.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(Department department) {
        log.info("Saving new department: {}", department);
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        log.info("Fetching department with id: {}", id);
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    @Override
    public List<Department> getAllDepartments() {
        log.info("Fetching all departments");
        return departmentRepository.findAll();
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        log.info("Updating department with id: {}", id);
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        existingDepartment.setName(department.getName());
        existingDepartment.setDescription(department.getDescription());
        return departmentRepository.save(existingDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        log.info("Deleting department with id: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        departmentRepository.delete(department);
    }
}