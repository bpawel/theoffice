package com.theoffice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "department_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "min_salary")
  private Double minSalary;

  @Column(name = "max_salary")
  private Double maxSalary;

  @JsonIgnore
  @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
  private Set<AccountDetails> accountDetails;

  @Column(name = "hourly_rate")
  private Double hourlyRate;

  @Column(name = "number_of_employees_assigned")
  private Integer numberOfEmployeesAssigned;

  public Department() {
    numberOfEmployeesAssigned = 0;
  }

  public Department(String name, String description, Double minSalary, Double maxSalary, Double hourlyRate) {
    this.name = name;
    this.description = description;
    this.minSalary = minSalary;
    this.maxSalary = maxSalary;
    this.accountDetails = new HashSet<>();
    this.hourlyRate = hourlyRate;
    this.numberOfEmployeesAssigned = 0;
  }

  public void addAccount(AccountDetails accountDetails) {
    this.accountDetails.add(accountDetails);
  }
}
