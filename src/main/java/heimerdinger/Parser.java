package heimerdinger;

import heimerdinger.command.*;

public class Parser {
    public static Command parse(String command) throws HeimerdingerException {
        String[] split = command.split(" ", 2);
        String commandWord = split[0];
        String args = (split.length > 1 ) ? split[1] : "";

        switch (commandWord) {
        case "list":
            return new ListCommand();
        case "bye":
            return new ExitCommand();
        case "mark":
            return new MarkCommand(args);
        case "unmark":
            return new UnmarkCommand(args);
        case "delete":
            return new DeleteCommand(args);
        case "todo":
            return new AddToDoCommand(args);
        case "deadline":
            return new AddDeadlineCommand(args);
        case "event":
            return new AddEventCommand(args);
        default:
            throw new HeimerdingerException("I don't seem to understand this archaic language.");
        }
    }
}
