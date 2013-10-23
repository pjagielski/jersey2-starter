package pl.pjagielski.constraint;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import pl.pjagielski.model.Todo;

@Target({TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidForModification.Validator.class)
public @interface ValidForModification {

    String message() default "{pl.pjagielski.constraint.ValidForModification}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ValidForModification, Todo> {

        @Override
        public void initialize(ValidForModification validForCreation) {}

        @Override
        public boolean isValid(Todo todo, ConstraintValidatorContext constraintValidatorContext) {
            return todo != null
                && todo.getId() == null
                && (todo.getDescription() != null || todo.getDueDate() != null || todo.isCompleted() != null);
        }
    }

}
