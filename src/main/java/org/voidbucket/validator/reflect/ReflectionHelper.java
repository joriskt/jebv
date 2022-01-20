package org.voidbucket.validator.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public final class ReflectionHelper {

    private ReflectionHelper() {
    }

    private static boolean isAccessible(final int mod) {
        return Modifier.isPublic(mod) && !Modifier.isAbstract(mod) && !Modifier.isInterface(mod);
    }

    public static boolean isClassAccessible(final Class<?> type) {
        return isAccessible(type.getModifiers());
    }

    public static boolean isClassConstructible(final Class<?> type) {
        return findDefaultConstructor(type) != null;
    }

    public static <T> Constructor<T> findDefaultConstructor(final Class<T> type) {
        if (!isClassAccessible(type)) {
            return null;
        }

        try {
            Constructor<T> noArgConstructor = type.getConstructor();
            if (!isAccessible(noArgConstructor.getModifiers())) {
                return null;
            }
            return noArgConstructor;
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    public static <T> boolean hasDefaultConstructor(final Class<T> type) {
        return findDefaultConstructor(type) != null;
    }

}
