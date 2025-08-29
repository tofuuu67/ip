package heimerdinger.command;

import java.util.ArrayList;

import heimerdinger.HeimerdingerException;
import heimerdinger.Storage;
import heimerdinger.Ui;
import heimerdinger.task.Task;
import heimerdinger.task.TaskList;
import heimerdinger.task.ToDo;

public class FindCommand extends Command {
    private final String matchingWord;

    public FindCommand(String matchingWord) {
        this.matchingWord = matchingWord;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws HeimerdingerException {
        if (matchingWord.isEmpty()) {
            throw new HeimerdingerException("I can't read your mind great scientist. Let me know what you are trying to find!");
        } else {
            String[] split = matchingWord.split(" ");
            if (split.length > 1) {
                throw new HeimerdingerException("You should only enter 1 keyword for me to look for!");
            }
        }
        TaskList displayList = new TaskList();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String description = task.getDescription();
            String[] split = description.split(" ");
            for (int j = 0; j < split.length; j++) {
                if (split[j].trim().equals(matchingWord)) {
                    displayList.add(task);
                    break;
                }
            }
        }
        ui.showFoundTasks(displayList, matchingWord);
    }
}
