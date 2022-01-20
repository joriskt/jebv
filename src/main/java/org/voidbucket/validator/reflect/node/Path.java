package org.voidbucket.validator.reflect.node;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class Path {

    private final Path parent;
    private final String key;

    /**
     * Constructs a Path root.
     */
    public Path() {
        this(null);
    }

    /**
     * Constructs a named Path root.
     * @param key The name of the root.
     */
    public Path(final String key) {
        this(null, key);
    }

    /**
     * Constructs a child Path.
     * @param parent The parent of this new Path
     * @param key The key of this Path
     */
    private Path(final Path parent, final String key) {
        if (parent != null) {
            Objects.requireNonNull(key);
        }
        this.parent = parent;
        this.key = key;
    }

    public Path createChild(final String key) {
        return new Path(this, key);
    }

    public boolean isRoot() {
        return parent == null;
    }

    private void buildString(final StringBuilder stringBuilder, final String separator) {
        if (isRoot()) {
            stringBuilder.append(key == null ? "" : key);
            return;
        }

        parent.buildString(stringBuilder, separator);
        stringBuilder.append(separator);
        stringBuilder.append(key);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        buildString(stringBuilder, ".");
        return stringBuilder.toString();
    }

    public static Path create(final Path parent, final String key) {
        if (parent == null) {
            return new Path(key);
        }
        return parent.getParent().createChild(key);
    }

}
