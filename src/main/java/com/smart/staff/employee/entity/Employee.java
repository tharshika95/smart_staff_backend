package com.smart.staff.employee.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smart.staff.department.entity.Department;
import com.smart.staff.designation.Designation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String empId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate dateOfJoining;

    private Double salary;

    private boolean isActive;

    private String temporaryAddress;

    private String permanentAddress;

    private String imagePath;

    private String contactNo1;

    private String contactNo2;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; // Relationship with Department

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation; // Relationship with Designation
}
