package org.voidbucket.validator.impl.stateful;

import org.voidbucket.validator.Result;
import org.voidbucket.validator.Validator;
import org.voidbucket.validator.ValidatorContext;
import org.voidbucket.validator.check.discover.CheckDiscoverer;

import java.util.function.Function;

public class StatefulValidator implements Validator {

    private final CheckDiscoverer checkDiscoverer;
    private final Function<Object, StatefulValidatorContext> contextSupplier;

    StatefulValidator(final CheckDiscoverer checkDiscoverer) {
        this.checkDiscoverer = checkDiscoverer;
        this.contextSupplier = (state) -> new StatefulValidatorContext(this, state);
    }

    private ValidatorContext createContext(final Object state) {

    }

    @Override
    public <T> Result validate(T subject) {


        return null;
    }

}
