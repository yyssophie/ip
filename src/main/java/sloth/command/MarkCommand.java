package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;
import sloth.task.Task;

/**
 * Command to mark a task as completed.
 */
public class MarkCommand extends Command {
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
        assert index >= 1 && index <= tasks.size() : "Mark index out of range: " + index;
        Task task = tasks.mark(index);
        storage.save(tasks.asList());
        assert task.isDone() : "Marked task should be done";
        return ui.showMarked(task, true);
    }
}