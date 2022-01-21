package org.voidbucket.validator;

public interface Validator {
    <T> Result validate(T subject);
}
