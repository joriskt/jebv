package org.voidbucket.validator;

import org.jetbrains.annotations.NotNull;

public interface ParameterHolder {
    boolean has(String key);
    boolean has(Class<?> type);

    void declare(String key);
    void declare(Class<?> type);

    <T> T get(String key);
    <T> T get(Class<T> type);

    <T> void put(String key, T value);
    <T> void put(@NotNull T value);
    <T> void put(Class<? super T> type, T value);
}
