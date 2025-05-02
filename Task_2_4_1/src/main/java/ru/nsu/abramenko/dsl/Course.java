package ru.nsu.abramenko.dsl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Course extends Configuration {
    List<Student> allStudents;
    List<Group> groups;
    List<Task> tasks;
    List<Deadline> deadlines;
    Settings settings;
}
