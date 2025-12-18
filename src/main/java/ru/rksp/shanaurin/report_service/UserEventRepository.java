package ru.rksp.shanaurin.report_service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserEventRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserEventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[][] insertBatch(List<Row> rows) {
        String sql = """
            INSERT INTO analytics.user_events (event_date, event_time, id, event_type)
            VALUES (?, ?, ?, ?)
        """;

        return jdbcTemplate.batchUpdate(
                sql,
                rows,
                1000,
                (ps, row) -> {
                    ps.setDate(1, Date.valueOf(row.eventDate()));
                    ps.setTimestamp(2, Timestamp.valueOf(row.eventTime()));
                    ps.setLong(3, row.id());
                    ps.setString(4, row.eventType());
                }
        );
    }

    public record Row(LocalDate eventDate, LocalDateTime eventTime, long id, String eventType) {}
}