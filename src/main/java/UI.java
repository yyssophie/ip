// Ui.java
import java.util.List;
import java.util.Scanner;

public class UI {
    private final String line = "_".repeat(50);
    private final Scanner sc = new Scanner(System.in);

    public void showWelcome() {
        String logo = "   🌿─────\n  ( - . - )\n  /(   づ )\n  ╯ ╯ ╯ ╯\n";
        println(line);
        println("Hello, I am ... Sloth\n" + logo + "\nHow can I help you today?🦥");
        println(line);
    }
    public String readCommand() { return sc.nextLine(); }
    public void showLine() { println(line); }
    public void showError(String msg) { println(msg); }
    public void showGoodbye() {
        println(line);
        println("Bye. Hope to see you soon~\nI'm gonna...go back to...sleep  zZZ 🦥");
        println(line);
    }
    public void showList(List<Task> tasks) {
        println(line);
        for (int i = 0; i < tasks.size(); i++) println("\t" + (i+1) + ". " + tasks.get(i));
        println(line);
    }
    public void showAdded(Task t, int size) {
        println("okayy ... I've added " + t);
        println("Now you have " + size + " tasks 🦥");
    }
    public void showMarked(Task t, boolean done) {
        println(done ? "sniff sniff! I've marked as done:\n" + t
                : "okay... I've marked as undone:\n" + t);
    }
    public void showDeleted(Task t, int size) {
        println("Got it ..... I removed this task\n" + t);
        println("Now you have " + size + " tasks 🦥");
    }
    private void println(String s){ System.out.println(s); }
}
