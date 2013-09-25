package pl.pjagielski;

import org.springframework.stereotype.Component;

@Component
public class MyService {

    public String getMessage() {
        return "Got it!";
    }

}
