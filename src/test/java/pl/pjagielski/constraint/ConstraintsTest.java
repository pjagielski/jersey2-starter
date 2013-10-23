package pl.pjagielski.constraint;

import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import pl.pjagielski.model.Todo;
import pl.pjagielski.model.TodoBuilder;

@RunWith(JUnitParamsRunner.class)
public class ConstraintsTest {

    @Test
    @Parameters(method = "provideTodosForModification")
    public void shouldValidateForModification(Todo todo, boolean expectedResult) {
        ValidForModification.Validator validator = new ValidForModification.Validator();

        assertThat(validator.isValid(todo, null)).isEqualTo(expectedResult);
    }

    private Object[] provideTodosForModification() {
        return $(
            $(new TodoBuilder().withDescription("test").withDueDate(DateTime.now()).build(), true),
            $(new TodoBuilder().withDescription("test").build(), true),
            $(new TodoBuilder().withDueDate(DateTime.now()).build(), true),
            $(new TodoBuilder().withId(123L).build(), false),
            $(new TodoBuilder().build(), false)
        );
    }

    @Test
    @Parameters(method = "provideTodosForCreation")
    public void shouldValidateForCreation(Todo todo, boolean expectedResult) {
        ValidForCreation.Validator validator = new ValidForCreation.Validator();

        assertThat(validator.isValid(todo, null)).isEqualTo(expectedResult);
    }

    private static Object[] provideTodosForCreation() {
        return $(
            $(new TodoBuilder().withDescription("test").withDueDate(DateTime.now()).build(), true),
            $(new TodoBuilder().withDescription("test").build(), false),
            $(new TodoBuilder().withDueDate(DateTime.now()).build(), false),
            $(new TodoBuilder().withId(123L).build(), false),
            $(new TodoBuilder().build(), false)
        );
    }

}
