package heimerdinger.command;

import heimerdinger.DateTimeParser;
import heimerdinger.HeimerdingerException;
import heimerdinger.Storage;
import heimerdinger.Ui;
import heimerdinger.task.Deadline;
import heimerdinger.task.Event;
import heimerdinger.task.Task;
import heimerdinger.task.TaskList;
import heimerdinger.task.ToDo;

public class ScheduleCommand extends Command {
    private final String dateTime;

    public ScheduleCommand(String dateTime) {
        this.dateTime = dateTime;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeimerdingerException {
        assert this.dateTime != null : "Date cannot be empty!";
        DateTimeParser dateTime = DateTimeParser.parse(this.dateTime);

        TaskList displayList = new TaskList();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task instanceof ToDo) {
                continue;
            }

            if (task instanceof Deadline) {
                DateTimeParser deadline = ((Deadline) task).getDeadline();
                if (dateTime.isBefore(deadline)) {
                    displayList.add(task);
                }
            }

            if (task instanceof Event) {
                DateTimeParser fromDate = ((Event) task).getFromDate();
                DateTimeParser toDate = ((Event) task).getToDate();
                boolean dateInRange = fromDate.isBefore(dateTime) && dateTime.isBefore(toDate);
                if (dateInRange) {
                    displayList.add(task);
                }
            }
        }
        ui.showScheduledTasks(displayList, dateTime.toString());
    }
}
