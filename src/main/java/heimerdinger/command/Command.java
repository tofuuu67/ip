package heimerdinger.command;

import heimerdinger.HeimerdingerException;
import heimerdinger.Storage;
import heimerdinger.task.TaskList;
import heimerdinger.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws HeimerdingerException;

    public boolean isExit() {
        return false;
    }

    public static boolean isNumeric(String maybeNumber) {
        if (maybeNumber == null) {
            return false;
        }
        try {
            Double.parseDouble(maybeNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
