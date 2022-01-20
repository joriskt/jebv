package org.voidbucket.validator.exception;

import lombok.Getter;
import org.voidbucket.validator.check.CheckDefinition;
import org.voidbucket.validator.check.CheckRunnerFactory;

@Getter
public class CheckRunnerConstructionFailedException extends ValidatorConfigurationException {

    private final CheckRunnerFactory checkRunnerFactory;
    private final CheckDefinition checkDefinition;

    public CheckRunnerConstructionFailedException(final CheckRunnerFactory checkRunnerFactory,
                                                  final CheckDefinition checkDefinition,
                                                  final Throwable throwable) {
        super(throwable);
        this.checkRunnerFactory = checkRunnerFactory;
        this.checkDefinition = checkDefinition;
    }

    public CheckRunnerConstructionFailedException(final CheckRunnerFactory checkRunnerFactory,
                                                  final CheckDefinition checkDefinition,
                                                  final String message) {
        super(message);
        this.checkRunnerFactory = checkRunnerFactory;
        this.checkDefinition = checkDefinition;
    }

    public CheckRunnerConstructionFailedException(final CheckRunnerFactory checkRunnerFactory,
                                                  final CheckDefinition checkDefinition,
                                                  final String message,
                                                  final Throwable cause) {
        super(message, cause);
        this.checkRunnerFactory = checkRunnerFactory;
        this.checkDefinition = checkDefinition;
    }

}
