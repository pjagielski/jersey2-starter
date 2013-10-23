package pl.pjagielski;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.fest.assertions.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import pl.pjagielski.model.Todo;

public class TodoEndpointIntegrationTest {

    private static EmbeddedJetty embeddedJetty;
    private Client client = createClient();

    @Test
    public void shouldCreateTodo() {
        Todo todo = sampleTodo();

        WebTarget target = createResourceTarget();
        Response response = target.request().post(Entity.json(todo));

        Todo persitedTodo = response.readEntity(Todo.class);
        assertThat(response.getStatus()).isEqualTo(CREATED.getStatusCode());
        assertThat(persitedTodo.getId()).isNotNull();
    }

    @Test
    public void shouldUpdateTodo() {
        Todo todo = sampleTodo();
        DateTime now = DateTime.now();
        todo.setCompleted(false);

        WebTarget target = createResourceTarget();
        Response response = target.request().put(Entity.json(todo));

        Todo updatedTodo = response.readEntity(Todo.class);
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(updatedTodo.getDueDate().isAfter(now));
    }

    private Todo sampleTodo() {
        return new Todo("integration test!", DateTime.now().plusDays(10));
    }

    private WebTarget createResourceTarget() {
        return client.target(embeddedJetty.getBaseUri())
            .path("todo");
    }

    private Client createClient() {
        JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        jacksonJsonProvider.setMapper(objectMapper);
        return ClientBuilder.newClient().register(jacksonJsonProvider);
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        embeddedJetty = new EmbeddedJetty();
        embeddedJetty.start();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        embeddedJetty.stop();
    }

}
