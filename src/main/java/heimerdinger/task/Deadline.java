package heimerdinger.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import heimerdinger.DateTimeParser;
import heimerdinger.HeimerdingerException;

public class Deadline extends Task {
    protected String icon = "D";
    protected DateTimeParser deadline;

    public Deadline(String description, String deadline) throws HeimerdingerException {
        super(description);
        this.deadline = DateTimeParser.parse(deadline);
    }

    public String getIcon() {
        return icon;
    }

    public String toString() {
        return "[" + this.getIcon() + "][" + this.getStatusIcon() + "] " + this.description + " (by: " + this.deadline.toString() + ")";
    }

    public String encode() {
        return this.deadline.encode();
    }

    public DateTimeParser getDeadline() {
        return this.deadline;
    }
}
