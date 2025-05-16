package ru.nsu.abramenko.tools;

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
import ru.nsu.abramenko.dsl.model.Group;
import ru.nsu.abramenko.dsl.config.Settings;

public class TableBuild {
    public static final String RESULT_DIR;
    public static final String TEMPLATE_PATH = "template.ftl";

    static {
        RESULT_DIR = ClassLoader.getSystemResource("").toString() + File.separator + "results";
    }

    @SneakyThrows
    public static void generateHtmlTableChart(List<Group> groups, Settings settings) {
        if (groups == null || groups.isEmpty()) {
            throw new IllegalArgumentException("Groups list cannot be null or empty");
        }

        Configuration configuration = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setClassForTemplateLoading(TableBuild.class, "/");
        configuration.setDefaultEncoding("UTF-8");

        File out = new File(RESULT_DIR, "output.html");
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }

        @Cleanup Writer fileWriter = new FileWriter(out);
        Template template = configuration.getTemplate(TEMPLATE_PATH);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("groups", groups);
        dataModel.put("settings", settings);

        template.process(dataModel, fileWriter);
    }
}