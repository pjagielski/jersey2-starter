package pl.pjagielski.model;

import org.joda.time.DateTime;

public class TodoBuilder {

    private String description;
    private DateTime dueDate;
    private Boolean completed;
    private Long id;

    public TodoBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TodoBuilder withDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public TodoBuilder withCompleted(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public TodoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public Todo build() {
        Todo todo = new Todo();
        todo.setDescription(description);
        todo.setDueDate(dueDate);
        todo.setCompleted(completed);
        todo.setId(id);
        return todo;

    }
}