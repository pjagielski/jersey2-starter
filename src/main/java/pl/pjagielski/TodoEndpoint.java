package pl.pjagielski;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import pl.pjagielski.constraint.ValidForCreation;
import pl.pjagielski.constraint.ValidForModification;
import pl.pjagielski.model.Todo;

@Path("todo")
public class TodoEndpoint {

    private final TodoRepository service;

    @Inject
    public TodoEndpoint(TodoRepository service) {
        this.service = service;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response create(@ValidForCreation Todo todo) {
        service.create(todo);
        return Response.status(CREATED).type(APPLICATION_JSON)
            .entity(todo).build();
    }

    @PUT
    @Path("/{todoId}")
    @Consumes(APPLICATION_JSON)
    public Response update(@PathParam("todoId") Long todoId, @ValidForModification Todo todo) {
        Todo updatedTodo = service.update(todoId, todo);
        if (updatedTodo == null) {
            throw new NotFoundException("Todo [" + todoId + "] not found");
        }
        return Response.status(OK).type(APPLICATION_JSON)
            .entity(updatedTodo).build();
    }

}
