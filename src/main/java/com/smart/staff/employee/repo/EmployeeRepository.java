package com.smart.staff.employee.repo;

import com.smart.staff.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findTopByOrderByEmpIdDesc();

    // JPQL method to find an employee by empId
    @Query("SELECT e FROM Employee e WHERE e.empId = :empId")
    Optional<Employee> findByEmpId(@Param("empId") String empId);
}
