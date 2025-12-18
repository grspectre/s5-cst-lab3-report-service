package ru.rksp.shanaurin.report_service;

import org.springframework.stereotype.Service;
//import ru.rksp.shanaurin.data_service.UserEventRepository;
import ru.rksp.shanaurin.data.model.UserEvent;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserEventService {

    private final UserEventRepository repository;
    private final AtomicLong seq = new AtomicLong(1);

    public UserEventService(UserEventRepository repository) {
        this.repository = repository;
    }

    public int writeEvents(List<UserEvent> events) {
        var rows = events.stream().map(e -> {
            OffsetDateTime odt = e.getEventTime(); // тип зависит от генерации; может быть OffsetDateTime
            assert odt != null;
            var ldt = odt.toLocalDateTime();
            return new UserEventRepository.Row(
                    ldt.toLocalDate(),
                    ldt,
                    seq.getAndIncrement(),
                    e.getEventType()
            );
        }).toList();

        int[][] res = repository.insertBatch(rows);
        return res[0].length;
    }
}