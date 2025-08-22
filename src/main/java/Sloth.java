import java.util.*;
public class Sloth {
    public static void main(String[] args) {
        String logo =
                "   🌿─────      \n" +
                        "  ( - . - )    \n" +
                        "  /(   づ )    \n" +
                        "  ╯ ╯ ╯ ╯     \n";
        String line = "_".repeat(50);
        /* Greetings */
        System.out.println(line);
        System.out.println("Hello, I am ... Sloth\n" + logo + "\n" + "How can I help you today?");
        System.out.println(line);
        /* Echo */
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.toLowerCase().equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you soon~\nI'm gonna...go back to...sleep  zZZ");
                System.out.println(line);
                break;
            } else {
                System.out.println(line);
                System.out.println(input);
                System.out.println(line);
            }
        }
    }
}
