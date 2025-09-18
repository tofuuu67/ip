package heimerdinger.command;

import heimerdinger.HeimerdingerException;
import heimerdinger.Storage;
import heimerdinger.Ui;
import heimerdinger.task.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddToDoCommandTest {
    @Test
    public void addToDoCommand_noDescription_exceptionThrown() {
        try {
            Command c = new AddToDoCommand("");
            Ui ui = new Ui();
            Storage storage = new Storage("");
            TaskList tasks = new TaskList();
            c.execute(tasks, ui, storage);
        } catch (HeimerdingerException e) {
            assertEquals("I can't read your mind great scientist. ToDo must have an accompanying text!",
                    e.getMessage());
        }
    }
}
