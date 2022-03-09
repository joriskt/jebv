package org.voidbucket.validator.constraint.invoker;

import lombok.extern.slf4j.Slf4j;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.constraint.Constraint;

@Slf4j
public class NoArgsConstraintInvoker extends AbstractConstraintInvoker {

    @Override
    protected Object _invoke(final Constraint constraint, final Context context) throws Throwable {
        return constraint.getMethod().invoke(constraint);
    }

}
