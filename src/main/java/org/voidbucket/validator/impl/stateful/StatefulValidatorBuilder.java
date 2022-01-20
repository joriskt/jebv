package org.voidbucket.validator.impl.stateful;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.voidbucket.validator.check.discover.AnnotationCheckDiscoverer;
import org.voidbucket.validator.check.discover.CheckDiscoverer;

@Setter
@Accessors(chain = true, fluent = true)
public final class StatefulValidatorBuilder {

    private CheckDiscoverer checkDiscoverer;

    private StatefulValidatorBuilder() {
        this.checkDiscoverer = new AnnotationCheckDiscoverer();
    }

    public StatefulValidator build() {
        return new StatefulValidator(checkDiscoverer);
    }

}
