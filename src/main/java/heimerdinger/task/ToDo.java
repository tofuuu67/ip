package heimerdinger.task;

public class ToDo extends Task {
    String icon = "T";

    public ToDo(String description) {
        super(description);
    }

    public String getIcon() {
        return icon;
    }

    public String toString() {
        return "[" + this.getIcon() + "][" + this.getStatusIcon() + "] " + this.getDescription();
    }
}
