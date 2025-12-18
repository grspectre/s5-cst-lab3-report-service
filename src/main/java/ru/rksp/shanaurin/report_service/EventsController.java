package ru.rksp.shanaurin.report_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rksp.shanaurin.data.api.DefaultApi;
import ru.rksp.shanaurin.data.model.UserEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EventsController implements DefaultApi {

    private final UserEventService service;

    public EventsController(UserEventService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Object> eventsPost(List<UserEvent> userEvent) {
        int written = service.writeEvents(userEvent);

        Map<String, Object> body = new HashMap<>();
        body.put("inserted", written);

        return ResponseEntity.status(201).body(body);
    }
}