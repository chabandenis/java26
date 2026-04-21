package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * "Разбирает" объект на составные части
 */
public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private final Class clazz;

    private static final Logger log = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        return null;
    }

    @Override
    public Field getIdField() {
        try {
            return clazz.getDeclaredField("id");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return List.of();
    }
}
