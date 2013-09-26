package pl.pjagielski;

import org.joda.time.DateTime;

public class ResponseMessage {

    private String message;
    private DateTime created = DateTime.now();

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public DateTime getCreated() {
        return created;
    }

}
