public class Sloth {
    private final Storage storage;
    private final TaskList tasks;
    private final UI ui;

    public Sloth() {
        ui = new UI();
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = TaskParser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (SlothException e) {
                ui.showError(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                ui.showError("Index out of range");
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new  Sloth().run();
    }
}
