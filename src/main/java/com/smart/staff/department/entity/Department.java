package com.smart.staff.department.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smart.staff.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private boolean isActive;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonIgnore // Prevent recursion during serialization/deserialization
    private List<Employee> employees; // One-to-Many relationship with Employee
}

