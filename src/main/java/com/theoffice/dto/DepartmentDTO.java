package com.theoffice.dto;

import com.theoffice.entities.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDTO {

    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private String description;

    @Min(0)
    private Double minSalary;

    @Min(0)
    private Double maxSalary;

    @NotNull
    @Size(min = 1)
    private Double hourlyRate;

    @NotNull
    @Size(min = 1)
    private Integer numberOfEmployeesAssigned;

    public static Department convert(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        department.setMaxSalary(departmentDTO.getMaxSalary());
        department.setMinSalary(departmentDTO.getMinSalary());
        department.setHourlyRate(departmentDTO.getHourlyRate());
        return department;
    }

    public static DepartmentDTO convert(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getId());
        departmentDTO.setName(department.getName());
        departmentDTO.setDescription(department.getDescription());
        departmentDTO.setMaxSalary(department.getMaxSalary());
        departmentDTO.setMinSalary(department.getMinSalary());
        departmentDTO.setHourlyRate(department.getHourlyRate());
        departmentDTO.setNumberOfEmployeesAssigned(department.getNumberOfEmployeesAssigned());
        return departmentDTO;
    }
}
