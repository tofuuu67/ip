package heimerdinger;

import heimerdinger.task.Deadline;
import heimerdinger.task.Event;
import heimerdinger.task.Task;
import heimerdinger.task.ToDo;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Responsible for reading from and writing to a .txt file that contains a list of tasks
 */
public class Storage {
    private final Path path;

    /**
     * Creates a Storage object that contains information about the filePath to access, write, and read from
     *
     * @param filePath relative path of the file used to store information about tasks
     */
    public Storage(String filePath) {
        this.path = Paths.get(filePath);
    }

    /**
     * Load tasks from the storage file
     *
     * @return an ArrayList of tasks obtained from storage file
     * @throws HeimerdingerException if file cannot be read
     */
    public ArrayList<Task> load() throws HeimerdingerException {
        try {
            Path parent = path.toAbsolutePath().getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
                return new ArrayList<>();
            }
            ArrayList<Task> tasks = new ArrayList<>();
            for (String line: Files.readAllLines(path)) {
                if (!line.isEmpty()) {
                    tasks.add(read(line));
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new HeimerdingerException("There seems to be an error in your file: " + path);
        }
    }

    /**
     * Saves a list of tasks to storage file
     *
     * @param tasks list of tasks to be stored
     * @throws HeimerdingerException if file at filePath cannot be written to
     */
    public void save(List<Task> tasks) throws HeimerdingerException {
        try {
            Path parent = path.toAbsolutePath().getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            ArrayList<String> lines = new ArrayList<>();
            for (Task t : tasks) {
                lines.add(write(t));
            }
            Files.write(path, lines);
        } catch (IOException e) {
            throw new HeimerdingerException("There seems to be an error in your file: " + path);
        }
    }

    /**
     * Converts a line of text into something readable by the programme
     *
     * @param line raw string from the file
     * @return Task object that corresponds to the raw string
     */
    private Task read(String line) throws HeimerdingerException {
        String[] split = line.split(" \\| ");
        String taskType = split[0];
        boolean isDone = split[1].equals("1");
        Task task;
        if (taskType.equals("T")) {
            task = new ToDo(split[2]);
        } else if (taskType.equals("D")) {
            task =  new Deadline(split[2], split[3]);
        } else if (taskType.equals("E")) {
            task = new Event(split[2], split[3], split[4]);
        } else {
            throw new IllegalArgumentException("Error in source file");
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Converts a Task into a string representation
     *
     * @param task the task to convert into string format
     * @return string representation of the task
     */
    public String write(Task task) throws HeimerdingerException {
        String type = task.getIcon();
        String done = task.getStatusIcon().equals("X") ? "1" : "0";
        if (type.equals("T")) {
            ToDo todo = (ToDo) task;
            return type + " | " + done + " | " + todo.getDescription();
        } else if (type.equals("D")) {
            Deadline deadline = (Deadline) task;
            return type + " | " + done + " | " + deadline.getDescription() + " | " + deadline.encode();
        } else if (type.equals("E")) {
            Event event = (Event) task;
            return type + " | " + done + " | " + event.getDescription() + " | " + event.fromDateEncode() + " | " + event.toDateEncode();
        } else {
            throw new HeimerdingerException("There seems to be an error in writing your task to my database.");
        }
    }
}
