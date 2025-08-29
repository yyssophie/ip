package sloth;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> list) {
        this.tasks = new ArrayList<>(list);
    }

    public int size() {
        return tasks.size();
    }
    public ArrayList<Task> asList() {

        return this.tasks;
    }
    public Task add(Task t){
        tasks.add(t);
        return t;
    }
    public Task get(int i){
        return tasks.get(i - 1);
    }
    public Task delete(int i){
        return tasks.remove(i - 1);
    }
    public Task mark(int i){
        Task t = get(i);
        if (t.getStatus().equals(" "))
            t.toggleStatus();
        return t;
    }
    public Task unmark(int i){
        Task t = get(i);
        if (t.getStatus().equals("X"))
            t.toggleStatus();
        return t;
    }
}
