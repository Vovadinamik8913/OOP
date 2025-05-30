package ru.nsu.abramenko;

/**
 * Main class for starting the coordinator.
 */
public class Main {
    /**
     * Main method for starting the coordinator.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        /*int[] mid = new int[1000000];
        Arrays.fill(mid, 999999937);

        // Формируем JSON-структуру (как в вашем curl-запросе)
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("numbers", mid);
        jsonData.put("workingPower", 2);  // Пример значения

        // Записываем в файл
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("request.json"), jsonData);
            System.out.println("JSON успешно записан в файл 'request.json'");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Coordinator coordinator = new Coordinator();
        coordinator.start();
    }
}