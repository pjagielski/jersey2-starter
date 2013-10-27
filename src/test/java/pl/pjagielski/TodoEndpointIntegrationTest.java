package pl.pjagielski;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static junitparams.JUnitParamsRunner.$;
import static org.fest.assertions.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import pl.pjagielski.model.Todo;
import pl.pjagielski.model.TodoBuilder;

@RunWith(JUnitParamsRunner.class)
public class TodoEndpointIntegrationTest {

    private static EmbeddedJetty embeddedJetty;
    private Client client = createClient();

    private TodoEndpoint proxyClient;
    private Todo existingTodo;

    @Before
    public void setup() throws Exception {
        existingTodo = createTodo();
    }

    @Test
    @Parameters(method = "provideValidTodosForCreation")
    public void shouldCreateTodo(Todo todo) {
        Response response = createTarget().request().post(Entity.json(todo));

        assertThat(response.getStatus()).isEqualTo(CREATED.getStatusCode());
        Todo createdTodo = response.readEntity(Todo.class);
        assertThat(createdTodo.getId()).isNotNull();
    }

    private static Object[] provideValidTodosForCreation() {
        return $(
            new TodoBuilder().withDescription("test").withDueDate(DateTime.now()).build()
        );
    }

    @Test
    @Parameters(method = "provideInvalidTodosForCreation")
    public void shouldRejectInvalidTodoWhenCreate(Todo todo) {
        Response response = createTarget().request().post(Entity.json(todo));

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    private static Object[] provideInvalidTodosForCreation() {
        return $(
            new TodoBuilder().withDescription("test").build(),
            new TodoBuilder().withDueDate(DateTime.now()).build(),
            new TodoBuilder().withId(123L).build(),
            new TodoBuilder().build()
        );
    }

    @Test
    @Parameters(method = "provideValidTodosForModification")
    public void shouldUpdateTodo(Todo todo) {
        DateTime now = DateTime.now();

        Response response = createTarget().path(existingTodoId()).request().put(Entity.json(todo));

        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        Todo updatedTodo = response.readEntity(Todo.class);
        assertThat(updatedTodo.getDueDate().isAfter(now));
    }

    private Object[] provideValidTodosForModification() {
        return $(
            new TodoBuilder().withDescription("test").withDueDate(DateTime.now()).build(),
            new TodoBuilder().withDescription("test").build(),
            new TodoBuilder().withDueDate(DateTime.now()).build()
        );
    }

    @Test
    @Parameters(method = "provideInvalidTodosForModification")
    public void shouldRejectInvalidTodoWhenUpdate(Todo todo) {
        Response response = createTarget().request().post(Entity.json(todo));

        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    private Object[] provideInvalidTodosForModification() {
        return $(
            new TodoBuilder().withId(123L).build(),
            new TodoBuilder().build()
        );
    }

    private WebTarget createTarget() {
        return client.target(embeddedJetty.getBaseUri()).path("todo");
    }

    private Client createClient() {
        JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        jacksonJsonProvider.setMapper(objectMapper);
        return ClientBuilder.newClient().register(jacksonJsonProvider);
    }

    private Todo createTodo() {
        Todo todo = new TodoBuilder()
            .withDescription("before")
            .withDueDate(DateTime.now().plusDays(30))
            .build();
        Response response = createTarget().request().post(Entity.json(todo));
        return response.readEntity(Todo.class);
    }

    private String existingTodoId() {
        return String.format("%d", existingTodo.getId());
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
