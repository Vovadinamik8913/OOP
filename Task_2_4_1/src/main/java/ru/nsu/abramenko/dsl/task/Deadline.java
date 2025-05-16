package ru.nsu.abramenko.dsl.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.abramenko.dsl.config.Configuration;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Deadline extends Configuration {
    Task info;
    LocalDate softDeadline;
    LocalDate hardDeadline;
}
