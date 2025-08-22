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
        ArrayList<Task> lst = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.toLowerCase().equals("bye")) { // goodbye
                System.out.println(line);
                System.out.println("Bye. Hope to see you soon~\nI'm gonna...go back to...sleep  zZZ");
                System.out.println(line);
                break;
            } else if (input.toLowerCase().equals("list")){ // list out all elements
                System.out.println(line);
                for (int i = 1; i <= lst.size(); i++) {
                    Task t = lst.get(i - 1);
                    String s = t.getContent();
                    System.out.println("\t" + i+ ". " + "[" + t.getStatus() + "] "+ s);
                }
                System.out.println(line);
            } else {
                String[] split = input.split(" ");
                if (!(split[0].equals("mark") || split[0].equals("unmark"))) {  // add something.
                    lst.add(new  Task(input));
                    System.out.println(line);
                    System.out.println("added: " + input);
                    System.out.println(line);
                } else {  // mark or unmark
                    int idx = Integer.parseInt(split[1]);
                    Task event = lst.get(idx-1);
                    System.out.println(line);
                    if (split[0].equals("mark")) {
                        if (event.getStatus().equals(" "))  event.toggleStatus();
                        System.out.println("Well... done! I've marked the following as done:");
                    } else {
                        if (event.getStatus().equals("X")) event.toggleStatus();
                        System.out.println("okay... I've marked the following as undone:");
                    }
                    System.out.println("\t" + "[" + event.getStatus() + "]" + " " + event.getContent());
                    System.out.println(line);
                }

            }
        }
    }
}
