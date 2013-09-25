package pl.pjagielski;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Path("/")
public class NotFoundResource {

    @Path("/{all:.*}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage handle404() {
        throw new WebApplicationException(
            status(NOT_FOUND).entity(new ResponseMessage("Not found")).build());
    }

}
