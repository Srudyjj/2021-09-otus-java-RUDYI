package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final String className;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;


    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.className = initName();
        this.constructor = initConstructor();
        this.idField = initIdField();
        this.allFields = initAllFields();
        this.fieldsWithoutId = initFieldsWithoutId();
    }

    private String initName() {
        return clazz.getSimpleName().toLowerCase(Locale.ROOT);
    }

    private Constructor<T> initConstructor() {
        try {
            return clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Field initIdField() {
        for (Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return null;
    }

    private List<Field> initAllFields() {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    private List<Field> initFieldsWithoutId() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }

    @Override
    public String getName() {
        return this.className;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldsWithoutId;
    }
}
