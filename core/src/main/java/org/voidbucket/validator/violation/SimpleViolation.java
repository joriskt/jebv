package org.voidbucket.validator.violation;

import java.util.HashMap;
import java.util.Map;

public final class SimpleViolation implements Violation {

    private final String messageTemplate;
    private final Map<String, Object> messageParameters;

    public SimpleViolation(final String messageTemplate) {
        this(messageTemplate, new HashMap<>());
    }

    public SimpleViolation(final String messageTemplate,
                           final Map<String, Object> messageParameters) {
        this.messageTemplate = messageTemplate;
        this.messageParameters = messageParameters;
    }

    @Override
    public String getMessageTemplate() {
        return messageTemplate;
    }

    @Override
    public Map<String, Object> getMessageParameters() {
        return messageParameters;
    }

}
