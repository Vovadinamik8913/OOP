package ru.nsu.abramenko;


import java.io.IOException;
import java.nio.file.Files;
import com.google.gson.Gson;
import java.nio.file.Paths;
import lombok.Getter;

/** class for configurating pizzeria.
 *
 */
@Getter
public class PizzeriaConfig {
    private static final String CONFIG_FILE = "config.json";
    private int bakers;
    private int deliverymen;
    private int storageCapacity;

    /** load config.
     *
     * @return new config
     * @throws IOException error
     */
    public static PizzeriaConfig loadConfig() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(CONFIG_FILE)));
        return new Gson().fromJson(json, PizzeriaConfig.class);
    }
}
