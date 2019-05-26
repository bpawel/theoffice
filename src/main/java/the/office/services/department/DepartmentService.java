package the.office.services.department;

import com.theoffice.entities.Department;

import java.util.List;

public interface DepartmentService {
  List<Department> getAll();

  Department getById(Long id);

  void add(Department department);

  void delete(Long id);
}
