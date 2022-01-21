package org.voidbucket.validator.check.discover;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.voidbucket.validator.annotation.UseChecks;
import org.voidbucket.validator.reflect.Node;

@Getter
@RequiredArgsConstructor
public class AnnotationCheckReference implements CheckReference {
    private final Node node;
    private final UseChecks annotation;
    private final Class<?> type;
}
