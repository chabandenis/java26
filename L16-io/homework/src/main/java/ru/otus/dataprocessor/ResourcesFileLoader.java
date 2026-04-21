package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper objectMapper;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<Measurement> load() {
        try {
            // Получаем путь к ресурсу
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new IOException("Файл не найден: " + fileName);
            }
            return getMeasurements(inputStream);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }

    private List<Measurement> getMeasurements(InputStream inputStream) throws IOException {
        String jsonFromFile;
        List<Measurement> measurements;
        // Читаем весь файл в строку
        jsonFromFile = new String(inputStream.readAllBytes());
        try {
            measurements = objectMapper.readValue(jsonFromFile, new TypeReference<List<Measurement>>() {});
        } catch (JsonProcessingException e) {
            throw new FileProcessException(e);
        }
        return measurements;
    }
}
