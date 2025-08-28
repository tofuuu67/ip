package heimerdinger;

import heimerdinger.task.Deadline;
import heimerdinger.task.Event;
import heimerdinger.task.Task;
import heimerdinger.task.ToDo;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class Storage {
    private final Path path;

    public Storage(String filePath) {
        this.path = Paths.get(filePath);
    }

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

    private Task read(String line) {
        String[] split = line.split(" \\| ");
        String taskType = split[0];
        boolean isDone = split[1].equals("1");
        Task task;
        if (taskType.equals("T")) {
            task = new ToDo(split[2]);
        } else if (taskType.equals("D")) {
            task =  new Deadline(split[2], LocalDate.parse(split[3]));
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

    public String write(Task task) {
        String type = task.getIcon();
        String done = task.getStatusIcon().equals("X") ? "1" : "0";
        if (type.equals("T")) {
            ToDo todo = (ToDo) task;
            return type + " | " + done + " | " + todo.getDescription();
        } else if (type.equals("D")) {
            Deadline deadline = (Deadline) task;
            return type + " | " + done + " | " + deadline.getDescription() + " | " + deadline.getDeadline();
        } else {
            Event event = (Event) task;
            return type + " | " + done + " | " + event.getDescription() + " | " + event.getFromDate() + " | " + event.getToDate();
        }
    }
}
