package org.voidbucket.validator.example;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.voidbucket.validator.Context;
import org.voidbucket.validator.annotation.Check;
import org.voidbucket.validator.annotation.Depends;
import org.voidbucket.validator.annotation.Provides;

import static org.voidbucket.validator.constraint.Readiness.*;
import static org.voidbucket.validator.constraint.ConstraintStatus.*;

public class Example {

    @Data
    @Builder
    public static class ExampleSubject {

        private String name;
        private String email;

        private ChildClass childClass;

        @Data
        private final static class ChildClass {

            @NotNull
            private String notNullableString;

            @Nullable
            private String nullableString;

        }

    }

    public static class Check1 {

        @Check
        @Provides({
            String.class,
        })
        public void check(final Context ctx,
                          final ExampleSubject subject) {
            // assertNotNull(subject)
        }

    }

    public static class Check2 {

        public boolean cond(final Context context) {
            return context.getParams().has(String.class);
        }

        @Check
        @Depends(value = Check1.class, permitted = { SKIPPED, PASSED }, otherwise = FAIL)
        @Depends(value = { Check1.class })
        public void check(final Context context) {

        }

    }

    public static class Check3 {

    }

}
