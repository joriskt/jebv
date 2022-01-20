package org.voidbucket.validator.check.runner;

import lombok.extern.slf4j.Slf4j;
import org.voidbucket.validator.ValidatorContext;
import org.voidbucket.validator.check.CheckDefinition;
import org.voidbucket.validator.check.CheckRunner;
import org.voidbucket.validator.check.CheckRunnerFactory;
import org.voidbucket.validator.exception.CheckRunnerConstructionFailedException;
import org.voidbucket.validator.exception.ValidatorRunException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class EmptyConstructorCheckRunnerFactory implements CheckRunnerFactory {

    @Override
    public CheckRunner createRunner(final CheckDefinition definition) {
        final Class<?> type = definition.getType();

        try {
            final Constructor<?> constr = type.getConstructor();
            final Object instance = constr.newInstance();

            final List<Method> methods = definition.getMethods()
                .stream()
                .filter(method -> {
                    if (method.getParameterCount() != 0) {
                        log.warn(
                            "Ignoring @Check method with incorrect number of arguments: {}#{}",
                            method.getDeclaringClass().getCanonicalName(),
                            method.getName());
                        return false;
                    }
                    return true;
                })
                .toList();

            return new SimpleCheckRunner(instance, methods);
        } catch (Exception ex) {
            throw new CheckRunnerConstructionFailedException(this, definition, ex);
        }
    }

    private static class SimpleCheckRunner implements CheckRunner {

        private final Object instance;
        private final List<Method> methods;

        private SimpleCheckRunner(final Object instance, final List<Method> methods) {
            this.instance = instance;
            this.methods = methods;
        }

        @Override
        public void prepare(final ValidatorContext validatorContext) {
        }

        @Override
        public void execute(final ValidatorContext validatorContext) {
            try {
                for (Method method : methods) {
                    method.invoke(instance);
                }
            } catch (Exception ex) {
                throw new ValidatorRunException(ex);
            }
        }

    }

}
