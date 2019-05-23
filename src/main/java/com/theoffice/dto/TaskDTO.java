package com.theoffice.dto;

import com.theoffice.entities.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {

    @NotNull
    private Long id;

  @NotNull
  @Size(min = 1)
    private String name;

  @NotNull
  @Size(min = 1)
    private String description;

    @NotNull
    private String date;

  @NotNull
  @Size(min = 1)
    private Integer hours;

    private String department;

    public static Task convert(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        task.setDate(LocalDate.parse(taskDTO.getDate(), format));
        task.setHours(taskDTO.getHours());
        return task;
    }

    public static TaskDTO convert(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setHours(task.getHours());
        taskDTO.setDate(task.getDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
        taskDTO.setDepartment(task.getDepartment());
        return taskDTO;
    }
}
