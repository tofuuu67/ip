package heimerdinger;

import heimerdinger.task.Task;
import heimerdinger.task.TaskList;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Heimerdinger.Heimerdinger. What can I do for you?\n");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("______________________________________________");
    }

    public void showError(String message) {
        System.out.println("Uhoh! " + message);
    }

    public void showLoadingError() {
        System.out.println("Error loading saved files. Starting with new list");
    }

    public void showAdded(Task t) {
        System.out.println("Noted. Added task:");
        System.out.println("    " + t);
    }

    public void showDeleted(Task t, int size) {
        System.out.println("Noted. Deleted task:");
        System.out.println("    " + t);
        System.out.println(size + " item(s) in your inventory now.");
    }

    public void showMarked(Task t, TaskList tasks) {
        System.out.println("Indeed, a wise choice! Here's your list:");
        showTasks(tasks);
    }

    public void showUnmarked(Task t, TaskList tasks) {
        System.out.println("One step forward, two steps back. Here's your list:");
        showTasks(tasks);
    }

    public void showGoodbye() {
        System.out.println("For Piltover and science! Goodbye!");
    }

    public void showList(TaskList tasks) {
        System.out.println("Roger donger! Here's your list:");
        showTasks(tasks);
    }

    public void showTasks(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println("Nothing in your list for now!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("    " + (i + 1) + "." + tasks.get(i));
            }
        }
    }
}
