package org.voidbucket.validator.violation;

import java.util.Map;

public interface Violation {
    String getMessageTemplate();
    Map<String, Object> getMessageParameters();
}
