package uz.tuit.portfolio.util;

import org.springframework.stereotype.Component;
import uz.tuit.portfolio.model.DurationUnit;

import java.time.LocalDate;

@Component
public class TimeUtil {

    public LocalDate toExpireDate(LocalDate date, DurationUnit durationUnit, Integer durationValue) {

        int days = durationUnit.getDays() * durationValue;
        return date.plusDays(days);

    }

    public Integer remainingDays(DurationUnit durationUnit, Integer durationValue) {

        return durationUnit.getDays() * durationValue;

    }
}
