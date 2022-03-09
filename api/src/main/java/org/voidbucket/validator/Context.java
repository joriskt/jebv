package org.voidbucket.validator;

public interface Context {

    ValidatorState getState();
    Object getSubject();
    ParameterHolder getParams();

}
