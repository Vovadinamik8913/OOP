package ru.nsu.abramenko.dsl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Group extends Configuration {
    String name;
    List<Student> students;
    Integer tasks;
}
