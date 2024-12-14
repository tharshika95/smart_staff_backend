package com.smart.staff.entity;

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

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String designation;

    private LocalDate dateOfJoining;

    private Double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; // Relationship with Department
}
