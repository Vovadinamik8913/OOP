import java.time.LocalDate

tasks = [
        {
            id = "Task_1_1_1"
            title = "Heapsort"
            points = 1
        },
        {
            id = "Task_1_1_2"
            title = "21"
            points = 1
        },
        {
            id = "Task_1_1_3"
            title = "Operations"
            points = 1
        },
        {
            id = "Task_1_2_2"
            title = "HashTable"
            points = 1
        }
]

deadlines = [
        {
            info = tasks[0]
            softDeadline = LocalDate.of(2024, 9, 10)
            hardDeadline = LocalDate.of(2024, 9, 10)
        },
        {
            info = tasks[1]
            softDeadline = LocalDate.of(2024, 9, 17)
            hardDeadline = LocalDate.of(2024, 9, 24)
        },
        {
            info = tasks[2]
            softDeadline = LocalDate.of(2024, 10, 1)
            hardDeadline = LocalDate.of(2024, 10, 8)
        },
        {
            info = tasks[3]
            softDeadline = LocalDate.of(2024, 10, 29)
            hardDeadline = LocalDate.of(2024, 11, 5)
        }
]

allStudents = [
        {
            username = "Vovadinamik 8913"
            name = "Vovik"
            repo = "https://github.com/Vovadinamik8913/OOP/"
        }
]

groups = [
        {
            name = "23213"
            students = [
                    allStudents[0]
            ]
        }
]

settings {
    branch = "main"
    disableLongTests = true
    checkStyle = true
    checkTests = true
    checkDocsAndBuild = true
}


