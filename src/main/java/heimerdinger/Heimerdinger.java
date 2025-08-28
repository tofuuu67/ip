package heimerdinger;

import heimerdinger.command.Command;
import heimerdinger.task.TaskList;

public class Heimerdinger {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Heimerdinger(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (HeimerdingerException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String command = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(command);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (HeimerdingerException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Heimerdinger("./data/tasks.txt").run();
    }
}
