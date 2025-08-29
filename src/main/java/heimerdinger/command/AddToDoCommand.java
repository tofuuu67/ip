package heimerdinger.command;

import heimerdinger.*;
import heimerdinger.task.Task;
import heimerdinger.task.TaskList;
import heimerdinger.task.ToDo;

public class AddToDoCommand extends Command {
    private final String description;

    public AddToDoCommand(String description) {
        this.description = description;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeimerdingerException {
        if (description.isEmpty()) {
            throw new HeimerdingerException("I can't read your mind great scientist. ToDo must have an accompanying text!");
        }
        Task t = new ToDo(description);
        tasks.add(t);
        ui.showAdded(t);
        storage.save(tasks.getAll());
    }
}
