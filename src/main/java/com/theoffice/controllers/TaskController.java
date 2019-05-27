package com.theoffice.controllers;

import com.theoffice.dto.TaskDTO;
import com.theoffice.services.task.TaskService;
import com.theoffice.services.task.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController {
  private TaskService taskService;

  @Autowired
  public TaskController(TaskServiceImpl taskService) {
    this.taskService = taskService;
  }

  @PreAuthorize("hasRole('MANAGER') OR hasRole('EMPLOYEE')")
  @GetMapping(value = "/account/{id}")
  public List<TaskDTO> getByAccountId(@PathVariable Long id) {
    return taskService.getByAccountId(id).stream().map(TaskDTO::convert).collect(Collectors.toList());
  }

  @PreAuthorize("hasRole('EMPLOYEE')")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void add(@RequestBody TaskDTO taskDTO) {
    this.taskService.update(TaskDTO.convert(taskDTO));
  }

  @PreAuthorize("hasRole('EMPLOYEE')")
  @PutMapping
  public void update(@RequestBody List<TaskDTO> taskDTO) {
    taskDTO.stream().map(TaskDTO::convert).forEach(taskService::update);
  }

  @PreAuthorize("hasRole('EMPLOYEE') OR hasRole('MANAGER')")
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    this.taskService.delete(id);
  }
}
