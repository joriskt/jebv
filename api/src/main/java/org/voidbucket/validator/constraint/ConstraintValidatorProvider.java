package org.voidbucket.validator.constraint;

public interface ConstraintValidatorProvider {

    <T extends ConstraintValidator> T provide(Class<T> type);

}
