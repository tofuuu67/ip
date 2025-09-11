package heimerdinger.command;

import heimerdinger.*;
import heimerdinger.task.Deadline;
import heimerdinger.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddDeadlineCommand extends Command {
    private final String description;
    private final String deadline;

    public AddDeadlineCommand(String args) throws HeimerdingerException {
        String[] split = args.split(" /by ", 2);
        if (split[0].isEmpty()) {
            throw new HeimerdingerException("The deadline requires a description!");
        } else if (split.length < 2) {
            throw new HeimerdingerException("The deadline requires a due date!");
        }
        this.description = split[0].trim();
        this.deadline = split[1].trim();
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeimerdingerException {
        try {
            Deadline task = new Deadline(description, deadline);
            tasks.add(task);
            ui.showAdded(task);
            storage.save(tasks.getAll());
        } catch (DateTimeParseException e) {
            throw new HeimerdingerException("Would you like some assistance in learning how to write dates? " +
                    "(It's in the 'yyyy-mm-dd'/'yyyy-mm-dd hh:mm' format!)");
        }
    }
}
