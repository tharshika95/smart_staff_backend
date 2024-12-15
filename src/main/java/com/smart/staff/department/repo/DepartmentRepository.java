package com.smart.staff.department.repo;

import com.smart.staff.department.entity.Department;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}