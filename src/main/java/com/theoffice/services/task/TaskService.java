package com.theoffice.services.task;

import com.theoffice.entities.Task;

import java.util.List;

public interface TaskService {
  Task getOne(Long id);

  List<Task> getAll();

  List<Task> getByAccountId(Long id);

  void add(Task taks);

  void update(Task task);

  void delete(long id);
}
