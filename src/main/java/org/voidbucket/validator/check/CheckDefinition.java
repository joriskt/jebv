package org.voidbucket.validator.check;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.reflect.node.Node;

import java.lang.reflect.Method;
import java.util.List;

@Getter
@RequiredArgsConstructor
public final class CheckDefinition {
    private final Node node;
    private final Class<?> type;
    private final List<Method> methods;
}
