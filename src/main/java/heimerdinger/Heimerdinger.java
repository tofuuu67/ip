package heimerdinger;

import heimerdinger.command.Command;
import heimerdinger.task.TaskList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Main entry point for the Heimerdinger chatbot.
 */
public class Heimerdinger {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a new instance of Heimerdinger object, initialises UI and loads tasks from the specified file path.
     *
     * @param filePath The file path where task data is stored.
     */
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

    /**
     * Runs the main loop of the Heimerdinger application.
     */
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

    /**
     * Entry point of the program.
     *
     * @param args Command line arguments which are not used.
     */
    public static void main(String[] args) {
        new Heimerdinger("./data/tasks.txt").run();
    }

    public String getResponse(String input) {
        // Capture everything printed to System.out during command execution
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8);

        try {
            System.setOut(capture);
            try {
                Command c = Parser.parse(input);
                c.execute(tasks, ui, storage);   // this prints via Ui -> System.out
                // Optional: if (c.isExit()) Platform.exit();
            } catch (HeimerdingerException e) {
                ui.showError(e.getMessage());    // also goes into the buffer
            }
        } finally {
            System.setOut(originalOut);
            capture.close();
        }

        String out = new String(buffer.toByteArray(), StandardCharsets.UTF_8).trim();
        return out.isEmpty() ? "…" : out;
    }
}
