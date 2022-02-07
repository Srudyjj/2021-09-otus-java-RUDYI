package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K,V> storage = new WeakHashMap<>();
    private final List<HwListener<K, V>> listenerList = new ArrayList<>();

    private void notify(K key, V value, String action) {
        for(HwListener<K, V> listener: listenerList) {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void put(K key, V value) {
        storage.put(key, value);
        notify(key, value, "put");
    }


    @Override
    public void remove(K key) {
        V value = storage.remove(key);
        notify(key, value, "remove");
    }

    @Override
    public V get(K key) {
        V value = storage.get(key);
        notify(key, value, "get");
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }
}
