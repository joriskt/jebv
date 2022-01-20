package org.voidbucket.validator.check;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.annotation.Check;

import java.lang.reflect.Method;

@Getter
@RequiredArgsConstructor
class CheckInstance {
    private final Check annotation;
    private final Object owner;
    private final Method method;
}
