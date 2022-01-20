package org.voidbucket.validator.impl.stateful;

import lombok.Getter;
import org.voidbucket.validator.ValidatorContext;
import org.voidbucket.validator.check.CheckDefinition;
import org.voidbucket.validator.check.CheckRunner;
import org.voidbucket.validator.check.CheckRunnerFactory;
import org.voidbucket.validator.reflect.param.ParameterProvider;

import java.util.Objects;
import java.util.function.Supplier;

@Getter
public class StatefulCheckRunnerFactory implements CheckRunnerFactory {

    private final ParameterProvider parameterProvider;
    private final Supplier<Object> stateProvider;

    public StatefulCheckRunnerFactory(final ParameterProvider parameterProvider,
                                      final Supplier<Object> stateProvider) {
        this.parameterProvider = Objects.requireNonNull(parameterProvider);
        this.stateProvider = Objects.requireNonNull(stateProvider);
    }

    @Override
    public CheckRunner createRunner(final CheckDefinition definition) {
        return new StatefulCheckRunner(this, definition);
    }

    private static class StatefulCheckRunner implements CheckRunner {

        private final StatefulCheckRunnerFactory runnerFactory;
        private final CheckDefinition checkDefinition;
        private ParameterProvider parameterProvider;

        StatefulCheckRunner(final StatefulCheckRunnerFactory runnerFactory,
                            final CheckDefinition checkDefinition) {
            this.runnerFactory = runnerFactory;
            this.checkDefinition = checkDefinition;
        }

        private ParameterProvider buildParameterProvider(final ValidatorContext validatorContext) {
            final Object state = validatorContext.getState();
            return ParameterProvider.builder()
                .type(state.getClass(), state)
                .build();
        }

        @Override
        public void prepare(final ValidatorContext validatorContext) {
            validatorContext.setState(runnerFactory.getStateProvider().get());
        }

        @Override
        public void execute(final ValidatorContext validatorContext) {
            final Object state = validatorContext.getState();
            final ParameterProvider parameterProvider = buildParameterProvider(validatorContext);




        }

    }

}
