package org.voidbucket.validator.model;

import org.voidbucket.validator.annotation.Checks;
import org.voidbucket.validator.check.ExampleCheck;
import org.voidbucket.validator.check.ExampleChildCheck;

@Checks({
    ExampleCheck.class
})
public class Root {

    @Checks(ExampleChildCheck.class)
    private Child child;

}
