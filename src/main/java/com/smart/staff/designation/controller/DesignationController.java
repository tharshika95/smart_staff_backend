package com.smart.staff.designation.controller;

import com.smart.staff.designation.Designation;
import com.smart.staff.designation.service.DesignationService;
import com.smart.staff.dto.response.ApiResponse;
import com.smart.staff.dto.response.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
@Slf4j
public class DesignationController {

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Designation>> createDesignation(@RequestBody Designation designation) {
        try {
            log.info("Creating new designation: {}", designation);
            Designation savedDesignation = designationService.createDesignation(designation);
            return ResponseEntity.ok(ApiResponse.success(savedDesignation));
        } catch (Exception e) {
            log.error("Error while creating designation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while creating designation",e.getMessage())));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Designation>> getDesignationById(@PathVariable Long id) {
        try {
            log.info("Fetching designation with id: {}", id);
            Designation designation = designationService.getDesignationById(id);
            return ResponseEntity.ok(ApiResponse.success(designation));
        } catch (Exception e) {
            log.error("Error while fetching designation with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while fetching designation with id", e.getMessage())));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Designation>>> getAllDesignations() {
        try {
            log.info("Fetching all designations");
            List<Designation> designations = designationService.getAllDesignations();
            return ResponseEntity.ok(ApiResponse.success(designations));
        } catch (Exception e) {
            log.error("Error while fetching all designations: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while fetching all designations",e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Designation>> updateDesignation(@PathVariable Long id, @RequestBody Designation designation) {
        try {
            log.info("Updating designation with id {}: {}", id, designation);
            Designation updatedDesignation = designationService.updateDesignation(id, designation);
            return ResponseEntity.ok(ApiResponse.success(updatedDesignation));
        } catch (Exception e) {
            log.error("Error while updating designation with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("Error while updating designation with id",e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDesignation(@PathVariable Long id) {
        try {
            log.info("Deleting designation with id: {}", id);
            designationService.deleteDesignation(id);
            return ResponseEntity.ok(ApiResponse.success("Designation deleted successfully"));
        } catch (Exception e) {
            log.error("Error while deleting designation with id {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.failure(new ErrorDetails("", e.getMessage())));
        }
    }
}
