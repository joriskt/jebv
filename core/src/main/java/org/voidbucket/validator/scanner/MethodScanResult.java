package org.voidbucket.validator.scanner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;

@Getter
@RequiredArgsConstructor
public class MethodScanResult {

    private final Class<?> type;
    private final Method method;

}
