package com.smart.staff.repo;

import com.smart.staff.entity.Department;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
