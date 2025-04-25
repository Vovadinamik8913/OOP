package ru.nsu.abramenko.dsl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Student.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends Configuration {
    String username;
    String name;
    String repo;
    List<TaskAssignment> assignments;
    String activityPercentage;
}
