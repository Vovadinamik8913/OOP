tasks = import "tasks.dsl";
students = import "students.dsl";
groups = import "groups.dsl";
settings = import "settings.dsl";

config {
    tasks = $tasks;
    allStudents = $students.allStudents;
    groups = $groups.groups;
    settings = $settings;
};