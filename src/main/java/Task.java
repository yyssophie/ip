public class Task {
    private String content;
    private boolean done;

    public Task(String content) {
        this.content = content;
        this.done = false;
    }

    public String getContent() {
        return this.content;
    }

    public String getStatus() {
        return this.done? "X" : " ";
    }

    public void toggleStatus() {
        this.done = !this.done;
    }
}
