import java.util.*;

public class Sloth {
    public static void main(String[] args) throws InterruptedException, SlothException {
        String logo =   "   🌿─────\n" +
                        "  ( - . - )\n" +
                        "  /(   づ )\n" +
                        "  ╯ ╯ ╯ ╯\n";
        String line = "_".repeat(50);
        /* Greetings */
        System.out.println(line);
        System.out.println("Hello, I am ... Sloth\n" + logo + "\n" + "How can I help you today?\uD83E\uDDA5");
        System.out.println(line);
        /* List */
        ArrayList<Task> lst = Storage.load();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) { // goodbye
                System.out.println(line);
                System.out.println("Bye. Hope to see you soon~\nI'm gonna...go back to...sleep  zZZ \uD83E\uDDA5");
                System.out.println(line);
                Storage.save(lst);
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
                if (!(split[0].equals("mark") || split[0].equals("unmark") || split[0].equals("delete"))) {  // add something.
                    System.out.println(line);
                    /* 3 cases: todo, deadline and event. handled in separate function */
                    Task task_to_add = null;
                    try {
                        task_to_add = TaskParser.parse_input(input);
                        lst.add(task_to_add);
                    } catch (SlothException e) {
                        System.out.println(e.getMessage());
                        System.out.println(line);
                        continue;
                    }
                    try {
                        System.out.println("okayy ... I've added " + task_to_add);
                        System.out.println("Let... me.... think...");
                        Thread.sleep(2000);
                        System.out.println("Now you have " + lst.size() + " tasks \uD83E\uDDA5");
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(line);
                } else {  // mark or unmark or delete
                    int idx = Integer.parseInt(split[1]);
                    Task stuff = null;
                    System.out.println(line);
                    try {
                        stuff = lst.get(idx - 1);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Oh no the little sloth cannot find " + idx + " in current list\uD83D\uDE2D");
                        System.out.println(line);
                        continue;
                    }
                    try {
                        if (split[0].equals("mark")) {
                            if (stuff.getStatus().equals(" ")) stuff.toggleStatus();
                            System.out.println("sniff sniff! I've marked the following as done:\n" + stuff);
                        } else if (split[0].equals("unmark")) {
                            if (stuff.getStatus().equals("X")) stuff.toggleStatus();
                            System.out.println("okay... I've marked the following as undone:\n" + stuff);
                        } else if (split[0].equals("delete")) {
                            Task task_to_delete = lst.get(idx - 1);
                            lst.remove(idx - 1);

                            System.out.println("Got it ..... I remove this task\n" + stuff);
                            System.out.println("Now you have " + lst.size() + " tasks \uD83E\uDDA5");
                        } else {
                            throw new UnknownCommandException(input);
                        }
                    } catch (UnknownCommandException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println(line);
                }
            }
        }
    }
}
