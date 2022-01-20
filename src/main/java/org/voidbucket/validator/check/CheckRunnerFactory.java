package org.voidbucket.validator.check;

public interface CheckRunnerFactory {
    CheckRunner createRunner(final CheckDefinition definition);
}
