package sloth;

/**
 * Abstract base class for all commands in the Sloth task management system.
 * Implements the Command pattern to encapsulate user actions.
 */
public abstract class Command {

    /**
     * Determines if this command should cause the application to exit.
     *
     * @return true if the command should exit the application, false otherwise
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying messages
     * @param storage the storage system for persisting tasks
     * @throws SlothException if an error occurs during command execution
     */
    public abstract String execute(TaskList tasks, UI ui, Storage storage) throws SlothException;
}

/**
 * Command to terminate the Sloth application.
 * Saves all tasks before exiting and displays a goodbye message.
 */
class ByeCommand extends Command {

    /**
     * Indicates that this command will exit the application.
     *
     * @return true, as this command exits the application
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * Executes the bye command by saving tasks and showing goodbye message.
     *
     * @param tasks the task list to save before exiting
     * @param ui the user interface for displaying the goodbye message
     * @param storage the storage system for saving tasks
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        storage.save(tasks.asList());
        return ui.showGoodbye();
    }
}

/**
 * Command to display all tasks in the current task list.
 */
class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks the task list to display
     * @param ui the user interface for showing the task list
     * @param storage the storage system (not used in this command)
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        return ui.showList(tasks.asList());
    }
}

/**
 * Command to add a new task to the task list.
 */
class AddTaskCommand extends Command {
    private final Task task;

    /**
     * Creates a new AddTaskCommand with the specified task.
     *
     * @param task the task to be added
     */
    public AddTaskCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add command by adding the task to the list, saving, and showing confirmation.
     *
     * @param tasks the task list to add the task to
     * @param ui the user interface for showing the confirmation message
     * @param storage the storage system for persisting the updated task list
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        tasks.add(task);
        storage.save(tasks.asList());
        return ui.showAdded(task, tasks.size());
    }
}

/**
 * Command to mark a task as completed.
 */
class MarkCommand extends Command {
    private final int index;

    /**
     * Creates a new MarkCommand for the task at the specified index.
     *
     * @param oneBasedIndex the 1-based index of the task to mark as completed
     */
    public MarkCommand(int oneBasedIndex) {
        this.index = oneBasedIndex;
    }

    /**
     * Executes the mark command by marking the task as done, saving, and showing confirmation.
     *
     * @param tasks the task list containing the task to mark
     * @param ui the user interface for showing the confirmation message
     * @param storage the storage system for persisting the updated task list
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        Task task = tasks.mark(index);
        storage.save(tasks.asList());
        return ui.showMarked(task, true);
    }
}

/**
 * Command to unmark a task (mark as incomplete).
 */
class UnmarkCommand extends Command {
    private final int index;

    /**
     * Creates a new UnmarkCommand for the task at the specified index.
     *
     * @param oneBasedIndex the 1-based index of the task to mark as incomplete
     */
    public UnmarkCommand(int oneBasedIndex) {
        this.index = oneBasedIndex;
    }

    /**
     * Executes the unmark command by marking the task as undone, saving, and showing confirmation.
     *
     * @param tasks the task list containing the task to unmark
     * @param ui the user interface for showing the confirmation message
     * @param storage the storage system for persisting the updated task list
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        Task task = tasks.unmark(index);
        storage.save(tasks.asList());
        return ui.showMarked(task, false);
    }
}

/**
 * Command to delete a task from the task list.
 */
class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a new DeleteCommand for the task at the specified index.
     *
     * @param oneBasedIndex the 1-based index of the task to delete
     */
    public DeleteCommand(int oneBasedIndex) {
        this.index = oneBasedIndex;
    }

    /**
     * Executes the delete command by removing the task, saving, and showing confirmation.
     *
     * @param tasks the task list to remove the task from
     * @param ui the user interface for showing the confirmation message
     * @param storage the storage system for persisting the updated task list
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        Task task = tasks.delete(index);
        storage.save(tasks.asList());
        return ui.showDeleted(task, tasks.size());
    }
}


/**
 * Command to find  a task
 * by searching for a keyword in the task description
 */
class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes find command by looping through all tasks in task list
     * to find tasks that match the key word
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying messages
     * @param storage the storage system for persisting tasks
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        java.util.ArrayList<Task> allTasks = tasks.asList();
        java.util.ArrayList<Task> matchingTasks = new java.util.ArrayList<>();

        for (Task task : allTasks) {
            if (task.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        return ui.showFoundTasks(matchingTasks);
    }
}