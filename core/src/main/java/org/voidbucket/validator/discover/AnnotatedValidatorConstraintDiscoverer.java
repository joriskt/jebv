package org.voidbucket.validator.discover;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.voidbucket.validator.annotate.Check;
import org.voidbucket.validator.constraint.Constraint;
import org.voidbucket.validator.constraint.ConstraintDiscoverer;
import org.voidbucket.validator.constraint.ConstraintValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Discovers {@link Constraint}s by looking at annotations on {@link ConstraintValidator} methods.
 */
@Slf4j
public class AnnotatedValidatorConstraintDiscoverer implements ConstraintDiscoverer {

    @Override
    public Set<? extends Constraint> discoverFromValidators(final Set<? extends ConstraintValidator> validators) {
        final Set<Constraint> constraints = new HashSet<>();
        for (final ConstraintValidator validator : validators) {
            constraints.addAll(scanType(validator));
        }
        return constraints;
    }

    /**
     * Scan a type for @{@link Check}-annotated methods. Each method is converted to
     * a {@link AnnotatedValidatorConstraint}. Other annotations on said method are checked
     * for mutators to the constraint.
     */
    private Set<? extends Constraint> scanType(final ConstraintValidator validator) {
        final Class<? extends ConstraintValidator> type = validator.getClass();
        final Set<Constraint> results = new HashSet<>();

        // Find all public methods.
        final Method[] methods = type.getMethods();
        for (final Method method : methods) {
            final Constraint constraint = scanMethod(validator, method);
            if (constraint != null) {
                results.add(constraint);
            }
        }

        return results;
    }

    private @Nullable Constraint scanMethod(final ConstraintValidator validator,
                                            final Method method) {
        // Determine if this is a check method.
        final Check check = getCheckAnnotation(method);
        if (check == null) {
            return null;
        }

        // Ignore methods that are not invokable.
        if (!isInvokable(method)) {
            log.warn("Detected non-invokable method (must be public and not abstract): {}#{}, ignoring",
                     method.getDeclaringClass().getCanonicalName(),
                     method.getName());
            return null;
        }

        // Instantiate the constraint & it's dependency node.
        final AnnotatedValidatorConstraint constraint =
            new AnnotatedValidatorConstraint(validator, method);

        // Scan all annotations on the method to find all mutations, and apply them to the constraint.
        final Annotation[] annotations = method.getAnnotations();
        for (final Annotation annotation : annotations) {
            final Class<? extends Annotation> type = annotation.annotationType();

            // Ignore the Check annotation since we already fetched it.
            if (type.equals(Check.class)) {
                continue;
            }

            // Find a mutator for this annotation type.
            final BiConsumer<Constraint, Annotation> mutator =
                AnnotatedValidatorConstraint.getMutator(type);
            if (mutator == null) {
                log.trace("No mutator for: {}", type.getCanonicalName());
                continue;
            }

            // Apply the mutator.
            log.trace("Processing: {}", type.getSimpleName());
            mutator.accept(constraint, annotation);
        }

        return constraint;
    }

    private Check getCheckAnnotation(final Method method) {
        return method.getAnnotation(Check.class);
    }

    private boolean isInvokable(final Method method) {
        int mods = method.getModifiers();
        return Modifier.isPublic(mods) && !Modifier.isAbstract(mods);
    }

}
