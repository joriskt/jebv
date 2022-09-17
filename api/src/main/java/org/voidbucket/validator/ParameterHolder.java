package org.voidbucket.validator;

import org.jetbrains.annotations.NotNull;
import org.voidbucket.validator.reflect.invoke.ConstraintInvoker;

/**
 * A simple object that holds parameters and their values by type or name. Used by {@link ConstraintInvoker}s.
 */
public interface ParameterHolder {

    /**
     * Checks whether a parameter with a name equals to {@code key} is contained within this holder.
     * @param key The parameter name to check.
     * @return Whether this named parameter is contained within this holder.
     */
    boolean has(String key);

    /**
     * Checks whether a parameter of the type {@code type}is contained within this holder.
     * @param type The parameter type to check.
     * @return Whether this typed parameter is contained within this holder.
     */
    boolean has(Class<?> type);

    void declare(String key);
    void declare(Class<?> type);

    <T> @NotNull T get(String key);
    <T> @NotNull T get(Class<T> type);

    <T> void put(String key, T value);
    <T> void put(@NotNull T value);
    <T> void put(Class<? super T> type, T value);
}
