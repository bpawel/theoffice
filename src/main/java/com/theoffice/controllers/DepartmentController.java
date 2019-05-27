package com.theoffice.controllers;

import com.theoffice.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.theoffice.services.department.DepartmentService;
import com.theoffice.services.department.DepartmentServiceImpl;


import java.util.List;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('MANAGER')")
@RestController
@RequestMapping(value = "/api/departments")
public class DepartmentController {

  private DepartmentService departmentService;

  @Autowired
  public DepartmentController(DepartmentServiceImpl departmentService) {
      this.departmentService = departmentService;
  }

  @GetMapping
  public List<DepartmentDTO> getAll() {
    return this.departmentService.getAll().stream().map(DepartmentDTO::convert).collect(Collectors.toList());
  }

  @PutMapping
  public void add(@RequestBody List<DepartmentDTO> departmentsDTO) {
    departmentsDTO.stream().map(DepartmentDTO::convert).forEach(departmentService::add);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    this.departmentService.delete(id);
  }
}
