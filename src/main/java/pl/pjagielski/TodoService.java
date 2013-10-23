package pl.pjagielski;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.pjagielski.model.Todo;

@Component
public class TodoService {

    private final static Logger logger = LoggerFactory.getLogger(TodoService.class);

    private long counter = 1L;

    public void create(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(counter++);
        }
        logger.info("Saving {}", todo);
    }

    public void update(Todo todo) {
        logger.info("Updating {}", todo);
    }

}
