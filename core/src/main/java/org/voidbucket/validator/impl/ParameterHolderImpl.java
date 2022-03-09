package org.voidbucket.validator.impl;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.ParameterHolder;

import java.util.HashMap;
import java.util.Map;

public class ParameterHolderImpl implements ParameterHolder {

    private final Map<String, Object> namedValues;
    private final Map<Class<?>, Object> typedValues;

    public ParameterHolderImpl() {
        this.namedValues = new HashMap<>();
        this.typedValues = new HashMap<>();
    }

    @Override
    public boolean has(String key) {
        return namedValues.containsKey(key);
    }

    @Override
    public boolean has(Class<?> type) {
        return typedValues.containsKey(type);
    }

    @Override
    public void declare(String key) {
        namedValues.putIfAbsent(key, null);
    }

    @Override
    public void declare(Class<?> type) {
        typedValues.putIfAbsent(type, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) namedValues.getOrDefault(key, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        return (T) typedValues.getOrDefault(type, null);
    }

    @Override
    public <T> void put(String key, T value) {
        namedValues.put(key, value);
    }

    @Override
    public <T> void put(@NotNull T value) {
        typedValues.put(value.getClass(), value);
    }

    @Override
    public <T> void put(Class<? super T> type, T value) {
        typedValues.put(type, value);
    }

}
