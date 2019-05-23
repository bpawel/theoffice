package com.theoffice.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.theoffice.exceptions.reports.ReportJSONException;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "accountDetails")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = AccountDetails.class)
public class AccountDetails implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_details_id")
  private Long id;

  @JsonIgnore
  @OneToOne(mappedBy = "accountDetails")
  private Account account;

  @Column(name = "email")
  private String email;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "second_name")
  private String secondName;

  @Column(name = "age")
  private Integer age;

  @Column(name = "worked_hours")
  private Integer workedHours;

  @Column(name = "salary")
  private Double salary;

  @JsonIgnore
  @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
  @OrderBy("date asc")
  private List<Task> tasks;

  @Column(name = "is_salary_threshold_exceeded")
  private boolean isSalaryThresholdExceeded;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "department")
  private Department department;

  public AccountDetails() {
    this.tasks = new ArrayList<>();
    this.workedHours = 0;
    this.salary = 0D;
    this.isSalaryThresholdExceeded = false;
  }

  public AccountDetails(String email, String firstName, String secondName, Integer age, Integer workedHours, Double salary) {
    this.email = email;
    this.firstName = firstName;
    this.secondName = secondName;
    this.age = age;
    this.salary = salary;
    this.workedHours = workedHours;
    this.tasks = new ArrayList<>();
    this.isSalaryThresholdExceeded = false;
    this.department = null;
  }

  public void addTask(Task task) {
    this.tasks.add(task);
  }

  public String toString() {
  JSONObject json = new JSONObject();
    try {
      json.put("email", this.email);
      json.put("firstName", this.firstName );
      json.put("secondName", this.secondName);
      json.put("age", this.age);
      json.put("salary", this.salary);
      json.put("workedHours", this.workedHours);
      json.put("department", "");

      Optional<Department> department = Optional.ofNullable(this.department);
      department.ifPresent(department1 -> json.put("department", this.department.getName()));

      JSONArray taskArray = new JSONArray();
      if (!Optional.ofNullable(this.tasks).isPresent()) {
        this.tasks.forEach(task -> {
          JSONObject subJson = new JSONObject();
          try {
            subJson.put("name", task.getName());
            subJson.put("description", task.getDescription());
            subJson.put("date", task.getDate());
            subJson.put("hours", task.getHours());
            subJson.put("department", task.getDepartment());
          } catch (JSONException e) {
            throw new ReportJSONException();
          }
          taskArray.put(subJson);
        });
      }
      json.put("tasks", taskArray);
    } catch (JSONException e1) {
      throw new ReportJSONException();
    }
    return json.toString();
  }
}

