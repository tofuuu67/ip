import java.util.Scanner;

public class Heimerdinger {
    public static void main(String[] args) {
        boolean end = false;

        System.out.println("Hello! I'm Heimerdinger\nWhat can I do for you?\n");
        Scanner scanner = new Scanner(System.in);
        Task[] array = new Task[100];
        String input;
        int index = 0;
        while (!end) {
            input = scanner.nextLine();
            String[] split = input.split(" ", 2);
            String firstWord = split[0];
            String secondWord = (split.length > 1) ? split[1] : "";

            try {
                if (firstWord.isEmpty()) {
                    throw new HeimerdingerException("Geniuses often go silent... but do give me a prompt to work with!");
                }
                if (input.equals("bye")) {
                    break;
                } else if (input.equals("list")) {
                    if (index > 0) {
                        System.out.println("One step closer to greater understanding! Here's your list:");
                        showList(index, array);
                    } else {
                        throw new HeimerdingerException("According to my records, the list is currently empty!");
                    }
                } else if (firstWord.equals("mark")) {
                    if (isNumeric(secondWord)) {
                        System.out.println("Indeed, a wise choice! Here's your list:");
                        int listNumber = Integer.parseInt(secondWord);
                        array[listNumber - 1].markAsDone();
                        showList(index, array);
                    } else {
                        throw new HeimerdingerException("Recheck the runes! Index must be a number!");
                    }
                } else if (firstWord.equals("unmark")) {
                    if (isNumeric(secondWord)) {
                        System.out.println("One step forward, two steps back... Anyways here's your list:");
                        int listNumber = Integer.parseInt(secondWord);
                        array[listNumber - 1].markAsNotDone();
                        showList(index, array);
                    } else {
                        throw new HeimerdingerException("Recheck the runes! Index must be a number!");
                    }
                } else if (firstWord.equals("todo")) {
                    if (!secondWord.isEmpty()) {
                        System.out.println("Noted: " + split[1] + "\n");
                        array[index] = new ToDo(split[1]);
                        index++;
                    } else {
                        throw new HeimerdingerException("I can't read your mind great scientist. ToDo must have an accompanying text!");
                    }
                } else if (firstWord.equals("deadline")) {
                    if (!secondWord.isEmpty()) {
                        String[] secondSplit = secondWord.split("/by ", 2);
                        String description = secondSplit[0];
                        String deadline = (secondSplit.length > 1) ? secondSplit[1] : "";
                        if (!description.isEmpty() && !deadline.isEmpty()) {
                            array[index] = new Deadline(description, deadline);
                            System.out.println("Noted: " + array[index] + "\n");
                            index++;
                        } else {
                            throw new HeimerdingerException("Need more information for my book of deadlines!");
                        }
                    } else {
                        throw new HeimerdingerException("I can't read your mind great scientist. Deadline must have an accompanying text!");
                    }
                } else if (firstWord.equals("event")) {
                    String[] secondSplit = secondWord.split("/from ", 2);
                    String description = secondSplit[0];
                    String time = (secondSplit.length > 1) ? secondSplit[1] : "";

                    String[] thirdSplit = time.split("/to ", 2);
                    String fromDate = thirdSplit[0];
                    String toDate = (thirdSplit.length > 1) ? thirdSplit[1] : "";
                    if (!description.isEmpty() && !fromDate.isEmpty() && !toDate.isEmpty()) {
                        array[index] = new Event(description, fromDate, toDate);
                        System.out.println("Noted: " + array[index] + "\n");
                        index++;
                    } else {
                        throw new HeimerdingerException("Need more information for my book of events!");
                    }
                } else {
                    throw new HeimerdingerException("I don't seem to understand this archaic language.");
                }
            } catch (HeimerdingerException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
        System.out.println("For Piltover and science! Goodbye!");
        scanner.close();
    }

    public static void showList(int listLength, Task[] array) {
        if (listLength == 0) {
            System.out.println("Hmm its an empty list!");
        } else {
            for (int i = 0; i < listLength; i++) {
                System.out.println(i + 1 + ".[" + array[i].getIcon() + "][" + array[i].getStatusIcon() + "] " + array[i].toString());
            }
            System.out.println();
        }
    }

    public static boolean isNumeric(String maybeNumber) {
        if (maybeNumber == null) {
            return false;
        }
        try {
            Double.parseDouble(maybeNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
