package ru.nsu.abramenko.util;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Cleanup;
import lombok.SneakyThrows;
import ru.nsu.abramenko.dsl.Group;
import ru.nsu.abramenko.dsl.Settings;

public class TableBuild {
    public static final String resultDir = "src/main/resources/results/";
    public static final String templatePath = "template.ftl";

    @SneakyThrows
    public static void generateHtmlTableChart(List<Group> groups, Settings settings) {
        if (groups == null || groups.isEmpty()) {
            throw new IllegalArgumentException("Groups list cannot be null or empty");
        }

        Configuration configuration = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setClassForTemplateLoading(TableBuild.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        File out = new File(resultDir, "output.html");
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }

        @Cleanup Writer fileWriter = new FileWriter(out);
        Template template = configuration.getTemplate(templatePath);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("groups", groups);
        dataModel.put("settings", settings);

        template.process(dataModel, fileWriter);
    }
}