package pl.pjagielski;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

@Path("/")
public class NotFoundResource {

    @Path("/{all:.*}")
    @Produces(APPLICATION_JSON)
    public ResponseMessage handle404() {
        throw new WebApplicationException(
            status(NOT_FOUND).type(APPLICATION_JSON_TYPE).entity(new ResponseMessage("Not found")).build());
    }

}
