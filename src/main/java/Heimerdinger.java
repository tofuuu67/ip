import java.util.Scanner;

public class Heimerdinger {
    public static void main(String[] args) {
        boolean end = false;

        System.out.println("Hello! I'm Heimerdinger\nWhat can I do for you?\n");
        Scanner scanner = new Scanner(System.in);
        String[] array = new String[100];
        int index = 0;
        while (!end) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                if (index == 0) {
                    System.out.println("Hmm its an empty list!");
                } else {
                    for (int i = 0; i < index; i++) {
                        System.out.println(i + 1 + ". " + array[i]);
                    }
                    System.out.println("\n");
                }
            } else {
                System.out.println("Noted: " + input + "\n");
                array[index] = input;
                index++;
            }
        }
        System.out.println("For Piltover and science! Goodbye!");
        scanner.close();
    }
}
