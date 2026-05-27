package ru.otus.jdbc.mapper;

import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private final EntityClassMetaData<T> entityClassMetaDataClient;

    private String getSelectFields() {
        return entityClassMetaDataClient.getAllFields().stream()
                .map(x -> x.getName())
                .collect(Collectors.joining(", "));
    }

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaDataClient) {
        this.entityClassMetaDataClient = entityClassMetaDataClient;
    }

    @Override
    public String getSelectAllSql() {
        return " select "
                + getSelectFields()
                + " from " + entityClassMetaDataClient.getName().toLowerCase();
    }

    @Override
    public String getSelectByIdSql() {
        return " select "
                + getSelectFields()
                + " from " + entityClassMetaDataClient.getName().toLowerCase()
                + " where " + entityClassMetaDataClient.getIdField().getName() + "=?";
    }

    private String getFieldsWithoutId() {
        return entityClassMetaDataClient.getFieldsWithoutId().stream()
                .map(x -> x.getName())
                .collect(Collectors.joining(", "));
    }

    private String getValuesForInsert() {
        return entityClassMetaDataClient.getFieldsWithoutId().stream()
                .map(x -> "?")
                .collect(Collectors.joining(", "));
    }

    @Override
    public String getInsertSql() {
        return " insert into " + entityClassMetaDataClient.getName().toLowerCase()
                + "("
                + getFieldsWithoutId()
                + ")"
                + " values ( "
                + getValuesForInsert()
                + ")";
    }

    @Override
    public String getUpdateSql() {
        return " update " + entityClassMetaDataClient.getName().toLowerCase()
                + "set "
                + entityClassMetaDataClient.getAllFields().stream()
                .map(x -> x.getName())
                .collect(Collectors.joining(" = ?, "))
                + ")"
                + " where " + entityClassMetaDataClient.getIdField().getName() + " = ?";
    }

    @Override
    public EntityClassMetaData getEntityClassMetaDataClient() {
        return entityClassMetaDataClient;
    }
}
