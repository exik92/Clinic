package com.company.function;

import com.company.clinic.util.GetTimeSlotFromLocalDateTime;
import com.company.clinic.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class GetTimeSlotFromLocalDateTimeTest {

    private final GetTimeSlotFromLocalDateTime slotGetter = new GetTimeSlotFromLocalDateTime();

    @Test
    public void shouldReturnTimeSlot() {
        // given
        LocalDateTime visitDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 40));

        // when
        Pair<LocalDateTime> timePair = slotGetter.apply(visitDate);

        // then
        Assertions.assertEquals(timePair.getMin().toLocalTime(), LocalTime.of(12, 00));
        Assertions.assertEquals(timePair.getMax().toLocalTime(), LocalTime.of(13, 00));
    }

}
