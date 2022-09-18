package org.voidbucket.validator.spring;

import org.springframework.stereotype.Component;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;

import java.util.List;
import java.util.Set;

@Component
public class ValidatorBeanConstraintDiscoverer implements ConstraintDiscoverer {

    private final List<ValidatorBean> validatorBeans;

    public ValidatorBeanConstraintDiscoverer(final List<ValidatorBean> validatorBeans) {
        this.validatorBeans = validatorBeans;
    }

    @Override
    public Set<? extends Constraint> discover(final Class<?> subject) {
        return null;
    }

}
