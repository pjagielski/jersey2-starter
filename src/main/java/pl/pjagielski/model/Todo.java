package pl.pjagielski.model;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Todo {

    private Long id;
    private String description;
    private Boolean completed;
    private DateTime dueDate;

    @JsonCreator
    public Todo(@JsonProperty("description") String description, @JsonProperty("dueDate") DateTime dueDate) {
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(DateTime dueDate) {
        this.dueDate = dueDate;
    }

    public DateTime getDueDate() {
        return dueDate;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("description", description)
            .add("completed", completed)
            .add("dueDate", dueDate)
            .toString();
    }
}
