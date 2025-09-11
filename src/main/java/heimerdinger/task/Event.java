package heimerdinger.task;

import heimerdinger.DateTimeParser;
import heimerdinger.HeimerdingerException;

public class Event extends Task {
    protected String icon = "E";
    protected DateTimeParser fromDate;
    protected DateTimeParser toDate;

    public Event(String description, String fromDate, String toDate) throws HeimerdingerException {
        super(description);
        this.fromDate = DateTimeParser.parse(fromDate);
        this.toDate = DateTimeParser.parse(toDate);
        if (this.toDate.isBefore(this.fromDate)) {
            throw new HeimerdingerException("Your dates are not in chronological order!");
        }
    }

    public String getIcon() {
        return icon;
    }

    public String toString() {
        return "[" + this.getIcon() + "][" + this.getStatusIcon() + "] " + this.description +
                " (from: " + this.fromDate.toString() + " to: " + this.toDate.toString() + ")";
    }

    public String fromDateEncode() {
        return this.fromDate.encode();
    }

    public String toDateEncode() {
        return this.toDate.encode();
    }

    public String getDescription() {
        return this.description;
    }
}
