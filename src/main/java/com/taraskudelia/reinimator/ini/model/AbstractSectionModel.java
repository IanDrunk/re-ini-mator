package com.taraskudelia.reinimator.ini.model;

import lombok.Getter;
import lombok.Setter;
import org.ini4j.Profile;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public abstract class AbstractSectionModel implements Profile.Section {

    private String name;
    private Map<String, String> keyValueMap;

    /* Constructors */
    private AbstractSectionModel() {}

    public AbstractSectionModel(String name) {
        this.name = name;
        this.keyValueMap = new HashMap<>();
    }

    public AbstractSectionModel(String name, Map<String, String> keyValueMap) {
        this.name = name;
        this.keyValueMap = keyValueMap;
    }

    /* HashMap methods */
    @Override
    public void clear() {
        keyValueMap.clear();
    }
    @Override
    public Set<String> keySet() {
        return keyValueMap.keySet();
    }
    @Override
    public Collection<String> values() {
        return keyValueMap.values();
    }
    @Override
    public Set<Entry<String, String>> entrySet() {
        return new HashSet<>(keyValueMap.entrySet());
    }
    @Override
    public int size() {
        return keyValueMap.size();
    }
    @Override
    public boolean isEmpty() {
        return keyValueMap.isEmpty();
    }
    @Override
    public boolean containsKey(Object key) {
        return keyValueMap.containsKey(key);
    }
    @Override
    public boolean containsValue(Object value) {
        return keyValueMap.containsValue(value);
    }
    @Override
    public String get(Object key) {
        return keyValueMap.get(key);
    }
    @Override
    public String put(String key, String value) {
        return keyValueMap.put(key, value);
    }
    @Override
    public String remove(Object key) {
        return keyValueMap.remove(key);
    }
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        keyValueMap.putAll(m);
    }

    /* Ignored methods */
    @Override public Profile.Section getChild(String s) { return null; }
    @Override public Profile.Section getParent() { return null; }
    @Override public String getSimpleName() { return null; }
    @Override public Profile.Section addChild(String s) { return null; }
    @Override public String[] childrenNames() { return new String[0]; }
    @Override public Profile.Section lookup(String... strings) { return null; }
    @Override public void removeChild(String s) { }
    @Override public <T> T getAll(Object o, Class<T> aClass) { return null; }
    @Override public void add(String s, Object o) { }
    @Override public void add(String s, Object o, int i) { }
    @Override public <T> T as(Class<T> aClass) { return null; }
    @Override public <T> T as(Class<T> aClass, String s) { return null; }
    @Override public String fetch(Object o) { return null; }
    @Override public String fetch(Object o, String s) { return null; }
    @Override public String fetch(Object o, int i) { return null; }
    @Override public <T> T fetch(Object o, Class<T> aClass) { return null; }
    @Override public <T> T fetch(Object o, Class<T> aClass, T t) { return null; }
    @Override public <T> T fetch(Object o, int i, Class<T> aClass) { return null; }
    @Override public <T> T fetchAll(Object o, Class<T> aClass) { return null; }
    @Override public void from(Object o) { }
    @Override public void from(Object o, String s) { }
    @Override public String get(Object o, String s) { return null; }
    @Override public <T> T get(Object o, Class<T> aClass) { return null; }
    @Override public <T> T get(Object o, Class<T> aClass, T t) { return null; }
    @Override public <T> T get(Object o, int i, Class<T> aClass) { return null; }
    @Override public String put(String s, Object o) { return null; }
    @Override public String put(String s, Object o, int i) { return null; }
    @Override public void putAll(String s, Object o) { }
    @Override public void to(Object o) { }
    @Override public void to(Object o, String s) { }
    @Override public String getComment(Object o) { return null; }
    @Override public String putComment(String s, String s2) { return null; }
    @Override public String removeComment(Object o) { return null; }
    @Override public List<String> getAll(Object o) { return null; }
    @Override public void add(String s, String s2) { }
    @Override public void add(String s, String s2, int i) { }
    @Override public String get(Object o, int i) { return null; }
    @Override public int length(Object o) { return 0; }
    @Override public String put(String s, String s2, int i) { return null; }
    @Override public List<String> putAll(String s, List<String> list) { return null; }
    @Override public String remove(Object o, int i) { return null; }

}
