package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> classMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classMetaData) {
        this.classMetaData = classMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + classMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM " + classMetaData.getName() +
                " WHERE " +
                classMetaData.getIdField().getName() +
                " = ?";
    }

    @Override
    public String getInsertSql() {
        String columns = classMetaData
                .getFieldsWithoutId()
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        String values = classMetaData
                .getFieldsWithoutId()
                .stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));
        String sql = "INSERT INTO " + classMetaData.getName() +
                " ( " + columns + " ) " +
                " VALUES ( " + values + " )";
        return sql;
    }

    @Override
    public String getUpdateSql() {
        String values = classMetaData
                .getFieldsWithoutId()
                .stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));
        String sql = "UPDATE " + classMetaData.getName() +
                " SET " + values + " WHERE "
                + classMetaData.getIdField().getName() + " = ?";
        return sql;
    }
}
