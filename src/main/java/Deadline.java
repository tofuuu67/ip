public class Deadline extends Task {
    protected String icon = "D";
    protected String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public String getIcon() {
        return icon;
    }

    public String toString() {
        return this.description + "(by: " + this.deadline + ")";
    }
}
