package ru.nsu.abramenko.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Task Information.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskInformation extends Configuration {
    String id;
    String title;
    Integer points;
}
