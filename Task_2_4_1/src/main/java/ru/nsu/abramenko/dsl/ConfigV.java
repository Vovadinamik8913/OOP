package ru.nsu.abramenko.dsl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class ConfigV extends ConfigBaseVisitor<Object> {
    private final Map<String, Object> globalContext = new HashMap<>();
    private Map<String, Object> currentContext;
    private final Path basePath;

    public ConfigV(Path basePath) {
        this.basePath = basePath;
        this.currentContext = globalContext;
    }

    @Override
    public Object visitConfig(ConfigParser.ConfigContext ctx) {
        ctx.statement().forEach(this::visit);
        return globalContext;
    }

    @Override
    public Object visitImportStatement(ConfigParser.ImportStatementContext ctx) {
        try {
            String filePath = ctx.STRING().getText().replaceAll("\"", "");
            Path fullPath = basePath.resolve(filePath);

            String input = Files.readString(fullPath);
            ConfigLexer lexer = new ConfigLexer(CharStreams.fromString(input));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ConfigParser parser = new ConfigParser(tokens);

            ConfigV visitor = new ConfigV(basePath);
            return visitor.visit(parser.config());
        } catch (Exception e) {
            throw new RuntimeException("Failed to process import: " + ctx.STRING().getText(), e);
        }
    }

    @Override
    public Object visitAssignment(ConfigParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        Object value = visit(ctx.value());
        currentContext.put(id, value);
        return value;
    }

    @Override
    public Object visitBlockDefinition(ConfigParser.BlockDefinitionContext ctx) {
        String id = ctx.ID().getText();
        Object value = visit(ctx.block());
        currentContext.put(id, value);
        return value;
    }

    @Override
    public Object visitBlock(ConfigParser.BlockContext ctx) {
        Map<String, Object> savedContext = currentContext;
        currentContext = new HashMap<>();

        try {
            ctx.statement().forEach(this::visit);
            return currentContext;
        } finally {
            currentContext = savedContext;
        }
    }

    @Override
    public Object visitArray(ConfigParser.ArrayContext ctx) {
        List<Object> array = new ArrayList<>();
        ctx.value().forEach(value -> array.add(visit(value)));
        return array;
    }

    @Override
    public Object visitDateValue(ConfigParser.DateValueContext ctx) {
        int year = Integer.parseInt(ctx.NUMBER(0).getText());
        int month = Integer.parseInt(ctx.NUMBER(1).getText());
        int day = Integer.parseInt(ctx.NUMBER(2).getText());
        return LocalDate.of(year, month, day);
    }

    @Override
    public Object visitStringValue(ConfigParser.StringValueContext ctx) {
        String text = ctx.STRING().getText();
        return text.substring(1, text.length() - 1);
    }

    @Override
    public Object visitNumberValue(ConfigParser.NumberValueContext ctx) {
        return Integer.parseInt(ctx.NUMBER().getText());
    }

    @Override
    public Object visitIdReference(ConfigParser.IdReferenceContext ctx) {
        String id = ctx.ID().getText();
        // Handle array indexing like tasks[0]
        if (id.matches(".+\\[\\d+\\]")) {
            String[] parts = id.split("\\[|\\]");
            Object obj = currentContext.get(parts[0]);
            if (obj instanceof List) {
                int index = Integer.parseInt(parts[1]);
                return ((List<?>) obj).get(index);
            }
        }
        return currentContext.get(id);
    }
}