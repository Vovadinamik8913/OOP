package ru.nsu.abramenko.dsl.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.abramenko.dsl.config.Configuration;
import ru.nsu.abramenko.dsl.task.Assignment;

@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends Configuration {
    String username;
    String name;
    String repo;
    List<Assignment> assignments;
}
