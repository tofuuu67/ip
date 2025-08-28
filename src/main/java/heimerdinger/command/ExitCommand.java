package heimerdinger.command;

import heimerdinger.Storage;
import heimerdinger.task.TaskList;
import heimerdinger.Ui;

public class ExitCommand extends Command {
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    public boolean isExit() {
        return true;
    }
}
