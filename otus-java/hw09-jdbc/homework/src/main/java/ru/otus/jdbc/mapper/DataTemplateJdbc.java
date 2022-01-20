package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
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
                        Constructor<T> constructor = classMetaData.getConstructor();
                        constructor.setAccessible(true);
                        T instance = constructor.newInstance();
                        Class<?> instanceClass = instance.getClass();
                        for (Field field : classMetaData.getAllFields()) {
                            String fieldName = field.getName();
                            Field declaredField = instanceClass.getDeclaredField(fieldName);
                            declaredField.setAccessible(true);
                            declaredField.set(instance, resultSet.getObject(fieldName));
                        }
                        return instance;

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
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
}
