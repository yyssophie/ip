package sloth;

import sloth.command.Command;
import sloth.exception.SlothException;

/**
 * Main class for the Sloth task management application.
 * Coordinates the interaction between UI, Storage, and TaskList components
 * to provide a command-line task management system.
 */
public class Sloth {
    private final Storage storage;
    private final TaskList tasks;
    private final UI ui;

    /**
     * Constructs a new Sloth application instance.
     * Initializes the UI, storage system, and loads existing tasks from storage.
     */
    public Sloth() {
        ui = new UI();
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    /**
     * Runs the main application loop.
     * Displays welcome message, processes user commands until exit command is received,
     * and handles exceptions gracefully.
     */
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
                ui.showError("sorry :( That index is out of my knowledge.");
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Entry point for the Sloth application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Sloth().run();
    }
}