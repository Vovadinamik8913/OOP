package ru.nsu.abramenko.dsl.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.abramenko.dsl.config.Configuration;

@EqualsAndHashCode(callSuper = true)
@Data
public class Group extends Configuration {
    String name;
    List<Student> students;
    Integer tasks;
}
