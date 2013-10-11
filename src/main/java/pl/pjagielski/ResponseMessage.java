package pl.pjagielski;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseMessage {

    private String message;
    private DateTime created = DateTime.now();

    @JsonCreator
    public ResponseMessage(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public DateTime getCreated() {
        return created;
    }

}
