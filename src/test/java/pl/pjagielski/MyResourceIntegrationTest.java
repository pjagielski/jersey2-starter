package pl.pjagielski;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.fest.assertions.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class MyResourceIntegrationTest {

    private static EmbeddedJetty embeddedJetty;
    private Client client = createClient();

    @Test
    public void shouldHandleMyResource() {
        ResponseMessage response = getResource();

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Got it!");
        assertThat(response.getCreated().isBeforeNow()).isTrue();
        assertThat(response.getCreated().isAfter(DateTime.now().minusMinutes(1))).isTrue();
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

    private ResponseMessage getResource() {
        WebTarget path = client.target(embeddedJetty.getBaseUri())
            .path("myresource");
        return path
            .request(APPLICATION_JSON)
            .get(ResponseMessage.class);
    }

    private Client createClient() {
        JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        jacksonJsonProvider.setMapper(objectMapper);
        return ClientBuilder.newClient().register(jacksonJsonProvider);
    }

}
