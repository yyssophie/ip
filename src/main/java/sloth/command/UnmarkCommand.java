package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;
import sloth.task.Task;

/**
 * Command to unmark a task (mark as incomplete).
 */
public class UnmarkCommand extends Command {
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
        assert index >= 1 && index <= tasks.size() : "Mark index out of range: " + index;
        Task t = tasks.get(index);
        Task task = tasks.unmark(index);
        storage.save(tasks.asList());
        assert !task.isDone() : "Marked task should be undone";
        return ui.showMarked(task, false);
    }
}
