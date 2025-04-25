package ru.nsu.abramenko.dsl;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Configuration {
    private URI scriptPath;
    private List<String> essentials = List.of("tasks", "allStudents", "groups", "settings");
    private Map<String, Object> parsedConfig;
    private Path basePath;

    @SneakyThrows
    public void runFrom(URI uri) {
        this.scriptPath = uri;
        this.basePath = Path.of(uri).getParent();
        String input = Files.readString(Path.of(uri));

        ConfigLexer lexer = new ConfigLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ConfigParser parser = new ConfigParser(tokens);

        ConfigV visitor = new ConfigV(basePath);
        parsedConfig = (Map<String, Object>) visitor.visit(parser.config());
    }

    @SneakyThrows
    public void postProcess() {
        // First verify we have the config block
        if (!parsedConfig.containsKey("config")) {
            throw new IllegalStateException("Missing 'config' block in configuration");
        }

        // Get the config block
        Map<String, Object> configBlock = (Map<String, Object>) parsedConfig.get("config");

        // Verify all essentials are present
        for (String essential : essentials) {
            if (!configBlock.containsKey(essential)) {
                throw new IllegalStateException("Missing essential configuration key: " + essential);
            }
            if (configBlock.get(essential) == null) {
                throw new IllegalStateException("Configuration value cannot be null for key: " + essential);
            }
        }

        // The parsedConfig should now contain all the merged data
        // You might want to copy everything from configBlock to parsedConfig
        parsedConfig.putAll(configBlock);
    }
}