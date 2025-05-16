package ru.nsu.abramenko.dsl.config;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.abramenko.dsl.model.Group;
import ru.nsu.abramenko.dsl.model.Student;
import ru.nsu.abramenko.dsl.task.Deadline;
import ru.nsu.abramenko.dsl.task.Task;

@EqualsAndHashCode(callSuper = true)
@Data
public class Course extends Configuration {
    List<Student> allStudents;
    List<Group> groups;
    List<Task> tasks;
    List<Deadline> deadlines;
    Settings settings;
}
