package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.repository.DataTemplate;
import ru.otus.repository.DataTemplateException;
import ru.otus.repository.executor.DbExecutor;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {
    private static final Logger log = LoggerFactory.getLogger(DataTemplateJdbc.class);

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String findById = entitySQLMetaData.getSelectByIdSql();
        log.info("insertSql {}", findById);

        try {
            return dbExecutor.executeSelect(connection, findById, List.of(id), x -> {
                try {
                    if (x.next()) {
                        List<Field> fields = entitySQLMetaData.getEntityClassMetaDataClient().getAllFields().stream()
                                .toList();

                        T obj = null;
                        try {
                            obj = (T) entitySQLMetaData
                                    .getEntityClassMetaDataClient()
                                    .getConstructor()
                                    .newInstance();
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }

                        for (Field field : fields) {
                            field.setAccessible(true);
                            try {
                                field.set(
                                        obj,
                                        field.getType() == Long.class
                                                ? x.getLong(field.getName())
                                                : x.getString(field.getName()));
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return obj;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return null;
            });
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {
        String insertSql = entitySQLMetaData.getInsertSql();
        log.info("insertSql {}", insertSql);

        List fieldsValue = entitySQLMetaData.getEntityClassMetaDataClient().getFieldsWithoutId().stream()
                .map(x -> {
                    ((Field) x).setAccessible(true);
                    try {
                        return ((Field) x).get(client);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        try {
            return dbExecutor.executeStatement(connection, insertSql, fieldsValue);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }
}
