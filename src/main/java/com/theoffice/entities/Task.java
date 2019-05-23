package com.theoffice.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tasks")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Task.class)
public class Task implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "hours")
  private Integer hours;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(name = "department")
  private String department;

  public Task() {

  }

  public Task(String name, String description, LocalDate date, Integer hours, Account account, String department) {
    this.name = name;
    this.description = description;
    this.date = date;
    this.hours = hours;
    this.account = account;
    this.department = department;
  }
}
