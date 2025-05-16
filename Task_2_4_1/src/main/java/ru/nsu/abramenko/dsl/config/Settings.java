package ru.nsu.abramenko.dsl.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Settings extends Configuration {
    String branch;
    boolean disableLongTests;
    boolean checkStyle;
    boolean checkTests;
    boolean checkDocsAndBuild;
}
