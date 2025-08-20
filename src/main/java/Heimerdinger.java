import java.util.Scanner;

public class Heimerdinger {
    public static void main(String[] args) {
        boolean end = false;

        System.out.println("Hello! I'm Heimerdinger\nWhat can I do for you?\n");
        Scanner scanner = new Scanner(System.in);
        while (!end) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }
            System.out.println(input + "\n");
        }
        System.out.println("For Piltover and science! Goodbye!");
        scanner.close();
    }
}
