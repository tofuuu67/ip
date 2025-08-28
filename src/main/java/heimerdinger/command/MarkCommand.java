package heimerdinger.command;

import heimerdinger.*;
import heimerdinger.task.Task;
import heimerdinger.task.TaskList;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(String arg) throws HeimerdingerException {
        if (isNumeric(arg)) {
            this.index = Integer.parseInt(arg) - 1;
        } else {
            throw new HeimerdingerException("My precise machinations require a number!");
        }
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeimerdingerException{
        if (index < 0 || index >= tasks.size()) {
            throw new HeimerdingerException("My calculations indicate that your number is not accessible!");
        }
        Task marked = tasks.get(index);
        marked.markAsDone();
        ui.showMarked(marked, tasks);
        storage.save(tasks.getAll());
    }
}
