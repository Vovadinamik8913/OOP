package ru.nsu.abramenko.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Deadline extends Configuration {
    Task info;
    LocalDate softDeadline;
    LocalDate hardDeadline;
}
