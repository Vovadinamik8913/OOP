package ru.nsu.abramenko.dsl.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.abramenko.dsl.config.Configuration;

@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends Configuration {
    String id;
    String title;
    Integer points;
    Assignment assignment;
}
