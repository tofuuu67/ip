package heimerdinger.command;

import heimerdinger.Storage;
import heimerdinger.task.TaskList;
import heimerdinger.Ui;

public class ListCommand extends Command {
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}
