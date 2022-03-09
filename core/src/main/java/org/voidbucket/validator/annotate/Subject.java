package org.voidbucket.validator.annotate;

public @interface Subject {
    Class<?>[] value();

    /**
     * <p>Whether this should match the subject at any depth.</p>
     * <ul>
     *     <li>If <b>false</b>, only the top-level subject will match.</li>
     *     <li>If <b>true</b>, it will match this subject at any recursive depth.</li>
     * </ul>
     * @return whether this subject is to be matched recursively
     */
    boolean recursive() default false;
}
