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
        /* List */
        ArrayList<String> lst = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.toLowerCase().equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you soon~\nI'm gonna...go back to...sleep  zZZ");
                System.out.println(line);
                break;
            } else if (input.toLowerCase().equals("list")){
                System.out.println(line);
                for (int i = 1; i <= lst.size(); i++) {
                    String s = lst.get(i-1);
                    System.out.println(i+ ". " + s);
                }
                System.out.println(line);
            } else {
                lst.add(input);
                System.out.println(line);
                System.out.println("added: " + input);
                System.out.println(line);
            }
        }
    }
}
