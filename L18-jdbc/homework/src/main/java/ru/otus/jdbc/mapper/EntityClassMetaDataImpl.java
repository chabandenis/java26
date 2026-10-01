package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.IdField;

/**
 * "Разбирает" объект на составные части
 */
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private final Class<T> clazz;
    private List<Field> allFields;

    private static final Logger log = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        allFields = getAllFields();
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        return allFields.stream()
                .filter(field -> field.isAnnotationPresent(IdField.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Field with IdField annotation not found"));
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return allFields.stream()
                .filter(field -> !field.isAnnotationPresent(IdField.class))
                .toList();
    }
}
