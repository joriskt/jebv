package org.voidbucket.validator.reflect.invoke;

import lombok.extern.slf4j.Slf4j;
import org.voidbucket.validator.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class NoArgsConstraintInvoker extends AbstractConstraintInvoker {

    @Override
    protected Object _invoke(final Object instance, final Method method, final Context context)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(instance);
    }

}
