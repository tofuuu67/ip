package heimerdinger.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import heimerdinger.HeimerdingerException;

public class DateTimeParser {

    private static final DateTimeFormatter DATE_ONLY =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_HH_MM =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final LocalDateTime date;
    private final DateTimeFormatter format;

    public DateTimeParser(LocalDateTime date, DateTimeFormatter format) {
        this.date = date;
        this.format = format;
    }

    public static DateTimeParser parse(String input) throws HeimerdingerException {
        if (isDateOnly(input)) {
            return new DateTimeParser(LocalDate.parse(input, DATE_ONLY).atStartOfDay(), DATE_ONLY);
        }

        if (isDateWithTime(input)) {
            return new DateTimeParser(LocalDateTime.parse(input, DATE_HH_MM), DATE_HH_MM);
        }

        throw new HeimerdingerException("Invalid datetime provided!");
    }

    private static boolean isDateOnly(String input) {
        try {
            LocalDate.parse(input, DATE_ONLY);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isDateWithTime(String input) {
        try {
            LocalDateTime.parse(input, DATE_HH_MM);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LocalDateTime getLocalDateTime() {
        return this.date;
    }

    public DateTimeFormatter getFormat() {
        return this.format;
    }

    public boolean isBefore(DateTimeParser d) {
        if (this.date.isBefore(d.date)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        if (this.format.equals(DATE_ONLY)) {
            DateTimeFormatter displayDate = DateTimeFormatter.ofPattern("MMM d yyyy");
            return date.format(displayDate);
        } else {
            DateTimeFormatter displayDateTime = DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm");
            return date.format(displayDateTime) + " hrs";
        }
    }

    public String encode() {
        if (this.format.equals(DATE_ONLY)) {
            DateTimeFormatter displayDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(displayDate);
        } else {
            DateTimeFormatter displayDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return date.format(displayDateTime);
        }
    }
}
