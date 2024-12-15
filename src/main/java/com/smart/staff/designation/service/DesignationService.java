package com.smart.staff.designation.service;

import com.smart.staff.designation.Designation;

import java.util.List;

public interface DesignationService {
    Designation createDesignation(Designation designation);
    Designation getDesignationById(Long id);
    List<Designation> getAllDesignations();
    Designation updateDesignation(Long id, Designation designation);
    void deleteDesignation(Long id);
}
