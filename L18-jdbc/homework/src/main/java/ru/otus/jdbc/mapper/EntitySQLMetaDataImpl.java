package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private final EntityClassMetaData<T> entityClassMetaDataClient;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaDataClient) {
        this.entityClassMetaDataClient = entityClassMetaDataClient;
    }

    @Override
    public String getSelectAllSql() {
        return " select " + entityClassMetaDataClient.getAllFields().stream().map(x -> x.getName()).collect(Collectors.joining(", "))
                + " from " + entityClassMetaDataClient.getName().toLowerCase();
    }

    @Override
    public String getSelectByIdSql() {
        return " select " + entityClassMetaDataClient.getAllFields().stream().map(x -> x.getName()).collect(Collectors.joining(", "))
                + " from " + entityClassMetaDataClient.getName().toLowerCase()
                + " where " + entityClassMetaDataClient.getIdField().getName() + "=?";
    }

    @Override
    public String getInsertSql() {
        return " insert into " + entityClassMetaDataClient.getName().toLowerCase()
                + "(" + entityClassMetaDataClient.getAllFields().stream().map(x -> x.getName()).collect(Collectors.joining(", ")) + ")"
                + " values ( " + entityClassMetaDataClient.getAllFields().stream().map(x -> "?").collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public String getUpdateSql() {
        return " update " + entityClassMetaDataClient.getName().toLowerCase()
                + "set " + entityClassMetaDataClient.getAllFields().stream().map(x -> x.getName()).collect(Collectors.joining(" = ?, ")) + ")"
                + " where " + entityClassMetaDataClient.getIdField().getName() + " = ?";
    }
}
