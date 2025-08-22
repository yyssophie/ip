import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sloth {
    public static void main(String[] args) throws InterruptedException {
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
                    System.out.println("\t" + i+ ". " + t);
                }
                System.out.println(line);
            } else {
                String[] split = input.split(" ");
                if (!(split[0].equals("mark") || split[0].equals("unmark"))) {  // add something.
                    System.out.println(line);
                    Task task_to_add = new Task("");
                    if (split[0].toLowerCase().equals("todo")) {  /* ToDo */
                        Pattern todo_pat = Pattern.compile("^todo\\s(.+)$");
                        Matcher match =  todo_pat.matcher(input);
                        match.matches();
                        String content = match.group(1);
                        lst.add(task_to_add = new ToDo(content));

                    } else if (split[0].toLowerCase().equals("deadline")) {  /* Deadline */
                        Pattern deadline_pat = Pattern.compile("^deadline\\s+(.+)\\s/by\\s(.+)$");
                        Matcher match = deadline_pat.matcher(input);
                        match.matches();
                        String content = match.group(1), endDate = match.group(2);
                        lst.add(task_to_add = new Deadline(content, endDate));
                    } else {  /* Event */
                        Pattern event_pat = Pattern.compile("^event\\s+(.+)\\s/from\\s(.+)\\s/to\\s(.+)$");
                        Matcher match = event_pat.matcher(input);
                        match.matches();
                        String content = match.group(1), startDate = match.group(2), endDate = match.group(3);
                        lst.add(task_to_add = new Event(content, startDate, endDate));
                    }
                    System.out.println("okayy ... I've added " + task_to_add);
                    System.out.println("Let... me.... calculate...");
                    Thread.sleep(2000);
                    System.out.println("Now you have " + lst.size() + " tasks \uD83E\uDDA5");
                    System.out.println(line);
                } else {  // mark or unmark
                    int idx = Integer.parseInt(split[1]);
                    Task stuff = lst.get(idx-1);
                    System.out.println(line);
                    if (split[0].equals("mark")) {
                        if (stuff.getStatus().equals(" "))  stuff.toggleStatus();
                        System.out.println("Well... done! I've marked the following as done:");
                    } else {
                        if (stuff.getStatus().equals("X")) stuff.toggleStatus();
                        System.out.println("okay... I've marked the following as undone:");
                    }
                    System.out.println(stuff);
                    System.out.println(line);
                }
            }
        }
    }
}
