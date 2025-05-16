package ru.nsu.abramenko.dsl.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.nsu.abramenko.dsl.config.Configuration;

@EqualsAndHashCode(callSuper = true)
@Data
public class Assignment extends Configuration {
    Task task;
    String build = "";
    String docs = "";
    String style = "";
    int testsTotal;
    int testsPassed;
    int testsIgnored;
    double points;
    String deadline = "On time";
}
