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
            String split[] = input.split(" ", 2);
            String firstWord = split[0];
            String secondWord = (split.length > 1) ? split[1] : "";

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                if (index > 0) {
                    System.out.println("One step closer to greater understanding! Here's your list:");
                }
                showList(index, array);
            } else if (firstWord.equals("mark") && isNumeric(secondWord)){
                System.out.println("Indeed, a wise choice! Here's your list:");
                int listNumber = Integer.parseInt(secondWord);
                array[listNumber - 1].markAsDone();
                showList(index, array);
            } else if (firstWord.equals("unmark") && isNumeric(secondWord)) {
                System.out.println("One step forward, two steps back... Anyways here's your list:");
                int listNumber = Integer.parseInt(secondWord);
                array[listNumber - 1].markAsNotDone();
                showList(index, array);
            } else if (firstWord.equals("deadline")) {
                String secondSplit[] = secondWord.split("/by ", 2);
                String description = secondSplit[0];
                String deadline = (secondSplit.length > 1) ? secondSplit[1] : "";
                array[index] = new Deadline(description, deadline);
                System.out.println("Noted: " + array[index] + "\n");
                index++;
            } else if (firstWord.equals("event")) {
                String secondSplit[] = secondWord.split("/from ", 2);
                String description = secondSplit[0];
                String time = (secondSplit.length > 1) ? secondSplit[1] : "";

                String thirdSplit[] = time.split("/to ", 2);
                String fromDate = thirdSplit[0];
                String toDate = (thirdSplit.length > 1) ? thirdSplit[1] : "";
                array[index] = new Event(description, fromDate, toDate);
                System.out.println("Noted: " + array[index] + "\n");
                index++;
            } else {
                System.out.println("Noted: " + input + "\n");
                array[index] = new ToDo(input);
                index++;
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
            System.out.println("\n");
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
