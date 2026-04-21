package ru.otus.jdbc.mapper;

import ru.otus.model.Client;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    public EntitySQLMetaDataImpl(EntityClassMetaData<Client> entityClassMetaDataClient) {}

    @Override
    public String getSelectAllSql() {
        return "";
    }

    @Override
    public String getSelectByIdSql() {
        return "";
    }

    @Override
    public String getInsertSql() {
        return "";
    }

    @Override
    public String getUpdateSql() {
        return "";
    }
}
