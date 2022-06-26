package org.voidbucket.validator.reflect.invoke;

import java.util.Optional;

public interface InvocationResult {

    Optional<Object> getResult();
    Optional<Throwable> getThrowable();

    boolean isOk();
    boolean isError();

}
