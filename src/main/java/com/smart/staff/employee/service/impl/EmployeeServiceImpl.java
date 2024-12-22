package com.smart.staff.employee.service.impl;

import com.smart.staff.department.entity.Department;
import com.smart.staff.department.repo.DepartmentRepository;
import com.smart.staff.designation.Designation;
import com.smart.staff.designation.repo.DesignationRepository;
import com.smart.staff.employee.dto.request.EmployeeRequest;
import com.smart.staff.employee.entity.Employee;
import com.smart.staff.employee.exception.EmployeeNotFoundException;
import com.smart.staff.employee.repo.EmployeeRepository;
import com.smart.staff.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.smart.staff.employee.constants.EmployeeConstants.EMPLOYEE_NOT_FOUND_WITH_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;

    public Employee createEmployee(EmployeeRequest request) {
        log.info("Creating new employee: {}", request.getName());

        try {
            // Validate Department
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Department ID"));

            // Validate Designation
            Designation designation = designationRepository.findById(request.getDesignationId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Designation ID"));

            // Set validated entities
            Employee employee = new Employee();
            employee.setEmpId(request.getEmpId());
            employee.setName(request.getName());
            employee.setEmail(request.getEmail());
            employee.setDateOfJoining(request.getDateOfJoining());
            employee.setSalary(request.getSalary());
            employee.setActive(request.getIsActive());
            employee.setTemporaryAddress(request.getTemporaryAddress());
            employee.setPermanentAddress(request.getPermanentAddress());
            employee.setImagePath(request.getImagePath());
            employee.setContactNo1(request.getContactNo1());
            employee.setContactNo2(request.getContactNo2());

            employee.setDepartment(department);
            employee.setDesignation(designation);

            // Save the employee
            return employeeRepository.save(employee);
        } catch (Exception e) {
            log.error("Error creating employee: {}", e.getMessage());
            throw new RuntimeException("Error creating employee: " + e.getMessage());
        }
    }

    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(EMPLOYEE_NOT_FOUND_WITH_ID, id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });
    }

    public Page<Employee> getAllEmployees(int page, int size) {
        log.info("Fetching all employees with pagination - Page: {}, Size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending()); // Adjust sorting as needed
        return employeeRepository.findAll(pageable);
    }

    public Employee updateEmployee(Long id, EmployeeRequest updatedEmployee) {
        log.info("Updating employee with ID: {}", id);

        // Fetch the existing employee or throw exception if not found
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });

        try {
            // Validate and fetch the Department using departmentId
            if (updatedEmployee.getDepartmentId() != null) {
                Department department = departmentRepository.findById(updatedEmployee.getDepartmentId())
                        .orElseThrow(() -> {
                            log.error("Invalid Department ID: {}", updatedEmployee.getDepartmentId());
                            return new IllegalArgumentException("Invalid Department ID: " + updatedEmployee.getDepartmentId());
                        });
                existingEmployee.setDepartment(department);
            }

            // Validate and fetch the Designation using designationId
            if (updatedEmployee.getDesignationId() != null) {
                Designation designation = designationRepository.findById(updatedEmployee.getDesignationId())
                        .orElseThrow(() -> {
                            log.error("Invalid Designation ID: {}", updatedEmployee.getDesignationId());
                            return new IllegalArgumentException("Invalid Designation ID: " + updatedEmployee.getDesignationId());
                        });
                existingEmployee.setDesignation(designation);
            }

            // Update employee fields
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setDateOfJoining(updatedEmployee.getDateOfJoining());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            existingEmployee.setActive(updatedEmployee.getIsActive());
            existingEmployee.setTemporaryAddress(updatedEmployee.getTemporaryAddress());
            existingEmployee.setPermanentAddress(updatedEmployee.getPermanentAddress());
            existingEmployee.setImagePath(updatedEmployee.getImagePath());
            existingEmployee.setContactNo1(updatedEmployee.getContactNo1());
            existingEmployee.setContactNo2(updatedEmployee.getContactNo2());

            // Save the updated employee
            return employeeRepository.save(existingEmployee);
        } catch (Exception e) {
            log.error("Error updating employee with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error updating employee with ID " + id + ": " + e.getMessage());
        }
    }



    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(EMPLOYEE_NOT_FOUND_WITH_ID, id);
                    return new EmployeeNotFoundException("Employee not found with ID: " + id);
                });

        employeeRepository.delete(employee);
    }
    private static final String IMAGE_DIR = "static/employees/images";

    public String saveEmployeeImage(String employeeId, MultipartFile file) throws IOException {

        // Validate file type (only image formats allowed)
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty. Please upload a valid image file.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only image formats (e.g., JPEG, PNG) are allowed.");
        }

        // Define the base directory inside the resources -> images -> employees folder
        String baseDir = new File("uploads" + File.separator + "images" + File.separator + "employees").getAbsolutePath();
        Path imageDirectory = Paths.get(baseDir);

        // Ensure the directory exists
        if (!Files.exists(imageDirectory)) {
            Files.createDirectories(imageDirectory);
        }

        // Generate file name and save
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String imageFileName = employeeId + fileExtension;
        Path imagePath = imageDirectory.resolve(imageFileName);

        // Save the image file
        Files.write(imagePath, file.getBytes());

        // Return the image URL
        return "/employees/images/" + imageFileName;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("Invalid file name. Could not determine file extension.");
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    public String generateNextEmpId() {
        // Fetch the last employee ID in descending order of empId
        Optional<Employee> lastEmployee = employeeRepository.findTopByOrderByEmpIdDesc();

        String nextEmpId;

        if (lastEmployee.isPresent()) {
            // Extract the numeric part of the last empId
            String lastEmpId = lastEmployee.get().getEmpId();
            int lastIdNumber = Integer.parseInt(lastEmpId.substring(3));

            // Increment the numeric part and generate the next empId
            nextEmpId = "EMP" + (lastIdNumber + 1);
        } else {
            // If no records exist, start with EMP1001
            nextEmpId = "EMP1001";
        }

        return nextEmpId;
    }

    public String deleteEmployeeImage(String employeeId) throws IOException {

        // Define image formats to check
        List<String> imageExtensions = Arrays.asList("png", "jpg", "jpeg", "gif", "bmp");

        // Construct the directory path using platform-independent approach
        String directory = "uploads" + File.separator + "images" + File.separator + "employees" + File.separator + "images";

        // Check each image format
        for (String ext : imageExtensions) {
            Path filePath = Paths.get(directory + File.separator + employeeId + "." + ext);

            // Delete the image file if it exists
            if (Files.deleteIfExists(filePath)) {
                return "Image successfully removed for employee ID: " + employeeId + " (" + ext + ")";
            }
        }

        // If no image is found
        return "No image found for employee ID: " + employeeId;
    }

}
