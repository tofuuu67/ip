package heimerdinger.command;

import heimerdinger.*;
import heimerdinger.task.Event;
import heimerdinger.task.TaskList;

public class AddEventCommand extends Command {
    private final String description;
    private final String fromDate;
    private final String toDate;

    public AddEventCommand(String args) throws HeimerdingerException {
        String[] split = args.split(" /from ", 2);
        if (split[0].isEmpty() || split.length < 2) {
            throw new HeimerdingerException("The event needs a description before using '/from'!");
        }
        String[] secondSplit = split[1].split(" /to ", 2);
        if (secondSplit[0].isEmpty() || secondSplit.length < 2) {
            throw new HeimerdingerException("The event needs a from date before using '/by'!");
        } else if (secondSplit[1].isEmpty()) {
            throw new HeimerdingerException("The event needs a to date after using '/to'");
        }
        this.description = split[0].trim();
        this.fromDate = secondSplit[0].trim();
        this.toDate = secondSplit[1].trim();
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeimerdingerException {
        Event event = new Event(description, fromDate, toDate);
        tasks.add(event);
        ui.showAdded(event);
        storage.save(tasks.getAll());
    }
}
