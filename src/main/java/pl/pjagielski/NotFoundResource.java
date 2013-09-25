package pl.pjagielski;

import com.google.common.collect.ImmutableMap;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.status;

@Path("/")
public class NotFoundResource {

    @Path("/{all:.*}")
    @Produces(MediaType.APPLICATION_JSON)
    public String handle404() {
        throw new WebApplicationException(status(NOT_FOUND).entity(ImmutableMap.of("message", "Not found")).build());
    }

}
