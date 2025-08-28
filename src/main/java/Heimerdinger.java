import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Heimerdinger {
    public static void main(String[] args) {
        boolean end = false;

        System.out.println("Hello! I'm Heimerdinger\nWhat can I do for you?\n");
        Scanner scanner = new Scanner(System.in);

        Storage storage = new Storage("./data/heimerdinger.txt");
        ArrayList<Task> array;
        try {
            array = storage.load();
        } catch (IOException e) {
            System.out.println("Error loading saved files. Starting with new list");
            array = new ArrayList<>();
        }
        String input;
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
                    if (!array.isEmpty()) {
                        System.out.println("One step closer to greater understanding! Here's your list:");
                        showList(array);
                    } else {
                        throw new HeimerdingerException("According to my records, the list is currently empty!");
                    }
                } else if (firstWord.equals("mark")) {
                    if (isNumeric(secondWord)) {
                        int listNumber = Integer.parseInt(secondWord);
                        if (listNumber > 0 && listNumber <= array.size()) {
                            array.get(listNumber - 1).markAsDone();
                            System.out.println("Indeed, a wise choice! Here's your list:");
                            showList(array);
                            try {
                                storage.save(array);
                            } catch (IOException e) {
                                System.out.println("Hmmm... there seems to be a problem saving.\n");
                            }
                        } else {
                            throw new HeimerdingerException("My calculations indicate that your number is not accessible.");
                        }
                    } else {
                        throw new HeimerdingerException("Recheck the runes! Index must be a number!");
                    }
                } else if (firstWord.equals("unmark")) {
                    if (isNumeric(secondWord)) {
                        int listNumber = Integer.parseInt(secondWord);
                        if (listNumber > 0 && listNumber <= array.size()) {
                            array.get(listNumber - 1).markAsNotDone();
                            System.out.println("One step forward, two steps back... Anyways here's your list:");
                            showList(array);
                            try {
                                storage.save(array);
                            } catch (IOException e) {
                                System.out.println("Hmmm... there seems to be a problem saving.\n");
                            }
                        } else {
                            throw new HeimerdingerException("My calculations indicate that your number is not accessible.");
                        }
                    } else {
                        throw new HeimerdingerException("Recheck the runes! Index must be a number!");
                    }
                } else if (firstWord.equals("delete")) {
                    if (isNumeric(secondWord)) {
                        int listNumber = Integer.parseInt(secondWord);
                        if (listNumber > 0 && listNumber <= array.size()) {
                            System.out.println("Rogerdonger. Removing list item:");
                            System.out.println("    [" + array.get(listNumber - 1).getIcon() + "][" + array.get(listNumber - 1).getStatusIcon() + "] " + array.get(listNumber - 1).toString());
                            array.remove(listNumber - 1);
                            System.out.println(array.size() + " item(s) in your records now.\n");
                            try {
                                storage.save(array);
                            } catch (IOException e) {
                                System.out.println("Hmmm... there seems to be a problem saving.\n");
                            }
                        } else {
                            throw new HeimerdingerException("My calculations indicate that your number is not accessible.");
                        }
                    } else {
                        throw new HeimerdingerException("Recheck the runes! Index must be a number!");
                    }
                } else if (firstWord.equals("todo")) {
                    if (!secondWord.isEmpty()) {
                        System.out.println("Noted: " + split[1] + "\n");
                        array.add(new ToDo(split[1]));
                        try {
                            storage.save(array);
                        } catch (IOException e) {
                            System.out.println("Hmmm... there seems to be a problem saving.");
                        }
                    } else {
                        throw new HeimerdingerException("I can't read your mind great scientist. ToDo must have an accompanying text!");
                    }
                } else if (firstWord.equals("deadline")) {
                    if (!secondWord.isEmpty()) {
                        String[] secondSplit = secondWord.split(" /by ", 2);
                        String description = secondSplit[0];
                        String deadline = (secondSplit.length > 1) ? secondSplit[1] : "";
                        if (!description.isEmpty() && !deadline.isEmpty()) {
                            try {
                                LocalDate date = LocalDate.parse(deadline);
                                Deadline task = new Deadline(description, date);
                                array.add(task);
                                System.out.println("Noted: " + task + "\n");
                            } catch (java.time.format.DateTimeParseException e) {
                                System.out.println("Would you like some assistance in learning how to write dates? (It's in the 'yyyy-mm-dd' format!)\n");
                            }

                            try {
                                storage.save(array);
                            } catch (IOException e) {
                                System.out.println("Hmmm... there seems to be a problem saving.");
                            }
                        } else {
                            throw new HeimerdingerException("Need more information for my book of deadlines!");
                        }
                    } else {
                        throw new HeimerdingerException("I can't read your mind great scientist. Deadline must have an accompanying text!");
                    }
                } else if (firstWord.equals("event")) {
                    String[] secondSplit = secondWord.split(" /from ", 2);
                    String description = secondSplit[0];
                    String time = (secondSplit.length > 1) ? secondSplit[1] : "";

                    String[] thirdSplit = time.split(" /to ", 2);
                    String fromDate = thirdSplit[0];
                    String toDate = (thirdSplit.length > 1) ? thirdSplit[1] : "";
                    if (!description.isEmpty() && !fromDate.isEmpty() && !toDate.isEmpty()) {
                        Event task = new Event(description, fromDate, toDate);
                        array.add(task);
                        System.out.println("Noted: " + task + "\n");
                        try {
                            storage.save(array);
                        } catch (IOException e) {
                            System.out.println("Hmmm... there seems to be a problem saving.");
                        }
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

    public static void showList(ArrayList<Task> array) {
        if (array.isEmpty()) {
            System.out.println("Nothing in your list for now!\n");
        } else {
            for (int i = 0; i < array.size(); i++) {
                System.out.println("    " + (i + 1) + ".[" + array.get(i).getIcon() + "][" + array.get(i).getStatusIcon() + "] " + array.get(i).toString());
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
