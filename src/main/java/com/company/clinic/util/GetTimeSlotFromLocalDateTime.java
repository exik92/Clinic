package com.company.clinic.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

@Component
public class GetTimeSlotFromLocalDateTime implements Function<LocalDateTime, Pair<LocalDateTime>> {

    @Override
    public Pair<LocalDateTime> apply(LocalDateTime value) {
        LocalTime localTime = value.toLocalTime();
        LocalTime min = localTime.truncatedTo(ChronoUnit.HOURS);
        LocalTime max = localTime.truncatedTo(ChronoUnit.HOURS).plusHours(1);
        return new Pair<>(LocalDateTime.of(
                value.toLocalDate(), min),
                LocalDateTime.of(value.toLocalDate(), max));
    }
}
