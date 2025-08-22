import java.util.*;

public class Sloth {
    public static void main(String[] args) throws InterruptedException, SlothException {
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
            if (input.equalsIgnoreCase("bye")) { // goodbye
                System.out.println(line);
                System.out.println("Bye. Hope to see you soon~\nI'm gonna...go back to...sleep  zZZ");
                System.out.println(line);
                break;
            } else if (input.equalsIgnoreCase("list")){ // list out all elements
                System.out.println(line);
                for (int i = 1; i <= lst.size(); i++) {
                    Task t = lst.get(i - 1);
                    System.out.println("\t" + i+ ". " + t);
                }
                System.out.println(line);
            } else {
                String[] split = input.split(" ");
                if (!(split[0].equals("mark") || split[0].equals("unmark"))) {  // add something.
                    System.out.println(line);
                    /* 3 cases: todo, deadline and event. handled in separate function */
                    Task task_to_add = null;
                    try {
                        task_to_add = TaskParser.parse(input);
                    } catch (SlothException e) {
                        System.out.println(e.getMessage());
                        System.out.println(line);
                        continue;
                    }
                    try {
                        lst.add(task_to_add);
                        System.out.println("okayy ... I've added " + task_to_add);
                        System.out.println("Let... me.... think...");
                        Thread.sleep(2000);
                        System.out.println("Now you have " + lst.size() + " tasks \uD83E\uDDA5");
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(line);
                } else {  // mark or unmark
                    int idx = Integer.parseInt(split[1]);
                    Task stuff = null;
                    System.out.println(line);
                    try {
                        stuff = lst.get(idx - 1);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Sorry, " + idx + " is out of bounds of my list\uD83D\uDE2D");
                        System.out.println(line);
                        continue;
                    }
                    try {
                        if (split[0].equals("mark")) {
                            if (stuff.getStatus().equals(" ")) stuff.toggleStatus();
                            System.out.println("Well... done! I've marked the following as done:");
                        } else if (split[0].equals("unmark")) {
                            if (stuff.getStatus().equals("X")) stuff.toggleStatus();
                            System.out.println("okay... I've marked the following as undone:");
                        } else {
                            throw new UnknownCommandException(input);
                        }
                    } catch (UnknownCommandException e) {
                        System.out.println(e.getMessage());
                        System.out.println(line);
                        continue;
                    }
                    System.out.println(stuff);
                    System.out.println(line);
                }
            }
        }
    }
}
