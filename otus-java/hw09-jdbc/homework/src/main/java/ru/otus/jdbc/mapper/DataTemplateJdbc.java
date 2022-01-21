package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> classMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> classMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.classMetaData = classMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id),
                resultSet ->  {
                    try {
                        if(resultSet.next()) {
                            return createInstance(resultSet);
                        }
                        return null;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(), resultSet -> {
            try {
                List<T> res = new ArrayList<>();
                while(resultSet.next()) {
                    res.add(createInstance(resultSet));
                }
                return res;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).orElse(List.of());
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> values = new ArrayList<>();
        for (Field field : classMetaData.getFieldsWithoutId()) {
            try {
                String fieldName = field.getName();
                Field declaredField = client.getClass().getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                values.add(declaredField.get(client));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), values);
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }

    private T getInstance() throws Exception {
        Constructor<T> constructor = classMetaData.getConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    private Field getField(Class<?> instanceClass, String fieldName) throws NoSuchFieldException {
        Field declaredField = instanceClass.getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        return declaredField;
    }

    private T createInstance(ResultSet resultSet) throws Exception {
        T instance = getInstance();
        Class<?> instanceClass = instance.getClass();
        for (Field field : classMetaData.getAllFields()) {
            String fieldName = field.getName();
            Field declaredField = getField(instanceClass, fieldName);
            declaredField.set(instance, resultSet.getObject(fieldName));
        }
        return instance;
    }
}
