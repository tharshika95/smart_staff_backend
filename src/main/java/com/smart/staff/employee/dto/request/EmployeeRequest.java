package com.smart.staff.employee.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequest {

    private String empId;
    private String name;
    private String email;
    private LocalDate dateOfJoining;
    private Double salary;
    private Boolean isActive;
    private String temporaryAddress;
    private String permanentAddress;
    private String imagePath;
    private String contactNo1;
    private String contactNo2;
    private Long departmentId;
    private Long designationId;
}