package org.voidbucket.validator.reflect.inspect;

public class Inspector<T> {

    private final Class<T> root;

    public Inspector(final Class<T> type) {
        this.root = type;
    }




    public static <T> Inspector<T> of(final Class<T> type) {
        return new Inspector<>(type);
    }

//    private static void test() {
//
//        // How do we want to inspect types?
//        Inspector.of(String.class)
//            .constraints().maxDepth(4)
//            .methods()
//            .annotatedWith()
//
//    }


}
