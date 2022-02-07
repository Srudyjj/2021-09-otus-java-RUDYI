package ru.otus.core.repository;

import org.hibernate.Session;
import ru.otus.cachehw.HwCache;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class DataTemplateHibernate<T> implements DataTemplate<T> {

    private final Class<T> clazz;
    private final HwCache<Long,T> cache;
    private final Field idField;

    public DataTemplateHibernate(Class<T> clazz, HwCache<Long,T> cache) {
        this.clazz = clazz;
        this.cache = cache;
        this.idField = getIdField(clazz);
    }

    @Override
    public Optional<T> findById(Session session, long id) {
        T fromCache = cache.get(id);
        if (fromCache != null) {
            return Optional.ofNullable(fromCache);
        }
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<T> findByEntityField(Session session, String entityFieldName, Object entityFieldValue) {
        var criteriaBuilder = session.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(clazz);
        var root = criteriaQuery.from(clazz);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(entityFieldName), entityFieldValue));

        var query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<T> findAll(Session session) {
        return session.createQuery(String.format("from %s", clazz.getSimpleName()), clazz).getResultList();
    }

    @Override
    public void insert(Session session, T object) {
        session.persist(object);
        try {
            if (idField != null) {
                cache.put((Long) idField.get(object), object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Session session, T object) {
        session.merge(object);
        try {
            if (idField != null) {
                cache.put((Long) idField.get(object), object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Field getIdField(Class<?> clazz) {
        for (Field f: clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                f.setAccessible(true);
                return f;

            }
        }
        return null;
    }
}
