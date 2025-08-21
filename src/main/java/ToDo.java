public class ToDo extends Task {
    String icon = "T";

    public ToDo(String description) {
        super(description);
    }

    public String getIcon() {
        return icon;
    }
}
