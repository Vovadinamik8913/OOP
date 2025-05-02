package ru.nsu.abramenko.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends Configuration {
    String id;
    String title;
    Integer points;
    Assignment assignment;
}
