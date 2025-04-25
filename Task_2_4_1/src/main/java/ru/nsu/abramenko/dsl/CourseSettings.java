package ru.nsu.abramenko.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Course Settings.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseSettings extends Configuration {
    String branch;
    boolean disableLongTests;
}
