public class Event extends Task {
    protected String icon = "E";
    protected String fromDate;
    protected String toDate;

    public Event(String description, String fromDate, String toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getIcon() {
        return icon;
    }

    public String toString() {
        return this.description + "(from: " + this.fromDate + " to: " + this.toDate + ")";
    }
}
