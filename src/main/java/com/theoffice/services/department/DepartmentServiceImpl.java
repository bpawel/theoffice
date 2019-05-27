package com.theoffice.services.department;

import com.theoffice.entities.Department;
import com.theoffice.exceptions.departments.DepartmentNotFoundException;
import com.theoffice.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
  private DepartmentRepository departmentRepository;

  public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  @Override
  public List<Department> getAll() {
    return this.departmentRepository.findAll();
  }

  @Override
  public Department getById(Long id) {
    Optional<Department> optional = Optional.ofNullable(departmentRepository.findById(id).orElse(null));
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new DepartmentNotFoundException();
    }
  }

  @Override
  public void delete(Long id) {
    Optional<Department> optional = Optional.ofNullable(departmentRepository.findById(id).orElse(null ));
    if (optional.isPresent()) {
      departmentRepository.delete(optional.get());
    } else {
      throw new DepartmentNotFoundException();
    }
  }

  @Override
  public void add(Department department) {
    this.departmentRepository.save(department);
  }
}
