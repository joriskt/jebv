package org.voidbucket.validator.check.discover;

import java.util.ArrayList;
import java.util.List;

public class StaticCheckDiscoverer implements CheckDiscoverer {

    private final List<CheckReference> references;

    public StaticCheckDiscoverer() {
        this.references = new ArrayList<>();
    }

    public StaticCheckDiscoverer register(final CheckReference reference) {
        this.references.add(reference);
        return this;
    }

    @Override
    public List<CheckReference> discover(Class<?> subjectType) {
        return references;
    }

}
