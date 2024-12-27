package com.smart.staff.designation.service.impl;

import com.smart.staff.designation.Designation;
import com.smart.staff.designation.repo.DesignationRepository;
import com.smart.staff.designation.service.DesignationService;
import com.smart.staff.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    public DesignationServiceImpl(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    @Override
    public Designation createDesignation(Designation designation) {
        log.info("Creating new designation: {}", designation);
        return designationRepository.save(designation);
    }

    @Override
    public Designation getDesignationById(Long id) {
        log.info("Fetching designation with id: {}", id);
        return designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found with id: " + id));
    }

    @Override
    public List<Designation> getAllDesignations() {
        log.info("Fetching all designations");
        return designationRepository.findAll();
    }

    @Override
    public Designation updateDesignation(Long id, Designation designation) {
        log.info("Updating designation with id: {}", id);
        Designation existingDesignation = designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found with id: " + id));

        existingDesignation.setName(designation.getName());
        existingDesignation.setDescription(designation.getDescription());
        existingDesignation.setActive(designation.isActive());
        return designationRepository.save(existingDesignation);
    }

    @Override
    public void deleteDesignation(Long id) {
        log.info("Deleting designation with id: {}", id);
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Designation not found with id: " + id));
        designationRepository.delete(designation);
    }
}
