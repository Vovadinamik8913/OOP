<!DOCTYPE html>
<html lang="en">
<head>
    <title>Students Table Chart</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 8px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .passed {
            color: #2da919;
        }
        .failed {
            color: #f66262;
        }
        .partial {
            color: #e6a037;
        }
        .disabled {
            color: #cccccc;
        }
    </style>
</head>
<body>

<#list groups as group>
    <#list group.students as student>
        <#list student.assignments as assignment>
        <table border="1">
            <tr>
                <th colspan="7">${assignment.task.title}</th>
            </tr>
            <tr>
                <th>Студент</th>
                <#if settings.checkDocsAndBuild>
                    <th>Сборка</th>
                    <th>Документация</th>
                </#if>
                <#if settings.checkStyle>
                    <th>Style guide</th>
                </#if>
                <#if settings.checkTests>
                    <th>Тесты</th>
                </#if>
                <th>Общий балл</th>
            </tr>
            <tr>
                <td>${student.name}</td>
                <#if settings.checkDocsAndBuild>
                    <td class="${(assignment.build == 'Successfully')?then('passed', 'failed')}">
                        ${(assignment.build == 'Successfully')?then('+', '-')}
                    </td>
                    <td class="${(assignment.docs == 'Generated')?then('passed', 'failed')}">
                        ${(assignment.docs == 'Generated')?then('+', '-')}
                    </td>
                </#if>
                <#if settings.checkStyle>
                    <td class="${(assignment.style == 'Passed')?then('passed', 'failed')}">
                        ${(assignment.style == 'Passed')?then('+', '-')}
                    </td>
                </#if>
                <#if settings.checkTests>
                    <td class="${(assignment.testsPassed == assignment.testsTotal)?then('passed', 'failed')}">
                        ${assignment.testsPassed}/${assignment.testsTotal}
                        <#if settings.disableLongTests>[long disabled]</#if>
                    </td>
                </#if>
                <td class="${(assignment.points > 0)?then('passed', 'failed')}">
                    ${assignment.points}
                </td>
            </tr>
        </table>
        </#list>
    </#list>
</#list>

<#-- Таблица общей статистики -->
<table border="1">
    <tr>
        <th colspan="${(groups[0].students[0].assignments?size) + 2}">Общая статистика группы ${groups[0].name!"Unknown"}</th>
    </tr>
    <tr>
        <th>Студент</th>
        <#list groups[0].students[0].assignments as assignment>
            <th>${assignment.task.title}</th>
        </#list>
        <th>Сумма</th>
    </tr>
    <#list groups[0].students as student>
    <tr>
        <td>${student.name}</td>
        <#assign totalPoints = 0>
        <#list student.assignments as assignment>
            <td>${assignment.points}</td>
            <#assign totalPoints = totalPoints + assignment.points>
        </#list>
        <td>${totalPoints}</td>
    </tr>
    </#list>
</table>

</body>
</html>