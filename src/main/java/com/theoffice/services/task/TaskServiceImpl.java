package com.theoffice.services.task;

import com.theoffice.entities.Account;
import com.theoffice.entities.Task;
import com.theoffice.exceptions.tasks.TaskNotFoundException;
import com.theoffice.repository.AccountRepository;
import com.theoffice.repository.TaskRepository;
import com.theoffice.services.accounts.AccountService;
import com.theoffice.services.authentication.AuthenticationServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

  private TaskRepository taskRepository;
  private AccountService accountService;
  private AccountRepository accountRepository;
  private AuthenticationServiceImpl authenticationServiceImpl;

  public TaskServiceImpl(TaskRepository taskRepository, AccountService accountService, AccountRepository accountRepository, AuthenticationServiceImpl authenticationServiceImpl) {
    this.taskRepository = taskRepository;
    this.accountService = accountService;
    this.accountRepository = accountRepository;
    this.authenticationServiceImpl = authenticationServiceImpl;
  }


  @Override
  public Task getOne(Long id) {
    Optional<Task> optional = Optional.ofNullable(taskRepository.findById(id).orElse(null));
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new TaskNotFoundException();
    }
  }

  @Override
  public List<Task> getAll() {
    return this.taskRepository.findAll();
  }

  @Override
  public List<Task> getByAccountId(Long id) {
    Account account = this.accountService.getById(id);
    return this.taskRepository.findByAccount(account);
  }

  @Override
  public void add(Task task) {
    task.setAccount(authenticationServiceImpl.getCurrent());
    task.setDepartment(task.getAccount().getAccountDetails().getDepartment().getName());

    this.taskRepository.save(task);
  }

  @Override
  public void update(Task task) {
    Account account = authenticationServiceImpl.getCurrent();
    task.setAccount(account);
    task.setDepartment(account.getAccountDetails().getDepartment().getName());
    this.taskRepository.save(task);
  }

  @Override
  public void delete(long id) {
    Optional<Task> optional = Optional.ofNullable(taskRepository.findById(id).orElse(null));
    if (optional.isPresent()) {
      taskRepository.delete(optional.get());
    } else {
      throw new TaskNotFoundException();
    }
  }
}
