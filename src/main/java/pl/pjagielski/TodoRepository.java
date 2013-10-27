package pl.pjagielski;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import pl.pjagielski.model.Todo;

@Component
public class TodoRepository {

    private final static Logger logger = LoggerFactory.getLogger(TodoRepository.class);

    private long counter = 1L;
    private Map<Long, Todo> store = Maps.newConcurrentMap();

    public Todo create(Todo todo) {
        logger.info("Saving {}", todo);
        Long id = counter++;
        todo.setId(id);
        store.put(id, todo);
        return todo;
    }

    public Todo update(Long todoId, Todo newTodo) {
        Todo todo = store.get(todoId);
        if (todo == null) {
            return null;
        }
        logger.info("Updating {}", newTodo);
        if (newTodo.getDueDate() != null) {
            todo.setDueDate(newTodo.getDueDate());
        }
        if (newTodo.isCompleted() != null) {
            todo.setCompleted(newTodo.isCompleted());
        }
        if (newTodo.getDescription() != null) {
            todo.setDescription(newTodo.getDescription());
        }
        return todo;
    }

}
