package sloth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.ArrayList;

import sloth.task.Task;
import sloth.task.Event;
import sloth.task.ToDo;
import sloth.task.Deadline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TaskListTest {

    private TaskList taskList;
    private ToDo todoTask;
    private Deadline deadlineTask;
    private Event eventTask;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        todoTask = new ToDo("Buy groceries");
        deadlineTask = new Deadline("Submit assignment", LocalDateTime.of(2025, 3, 15, 23, 59));
        eventTask = new Event("Team meeting",
                LocalDateTime.of(2025, 3, 10, 14, 0),
                LocalDateTime.of(2025, 3, 10, 16, 0));

        // Add tasks to the list for testing
        taskList.add(todoTask);
        taskList.add(deadlineTask);
        taskList.add(eventTask);
    }

    @Test
    @DisplayName("Test mark method marks an unmarked task")
    public void testMark_unmarkedTask() {
        // Ensure the task is initially unmarked
        assertEquals(" ", todoTask.getStatus());
        assertFalse(todoTask.isDone());

        // Mark the task
        Task markedTask = taskList.mark(1);  // 1-based indexing

        // Verify the task is now marked
        assertEquals("X", markedTask.getStatus());
        assertTrue(markedTask.isDone());
        assertEquals(todoTask, markedTask);  // Should return the same task object
    }

    @Test
    @DisplayName("Test mark method does not change already marked task")
    public void testMark_alreadyMarkedTask() {
        // First mark the task
        todoTask.toggleStatus();
        assertEquals("X", todoTask.getStatus());
        assertTrue(todoTask.isDone());

        // Try to mark it again
        Task result = taskList.mark(1);

        // Should remain marked (no change)
        assertEquals("X", result.getStatus());
        assertTrue(result.isDone());
        assertEquals(todoTask, result);
    }

    @Test
    @DisplayName("Test mark method with different task types")
    public void testMark_differentTaskTypes() {
        // Test marking a Deadline task
        assertEquals(" ", deadlineTask.getStatus());
        Task markedDeadline = taskList.mark(2);
        assertEquals("X", markedDeadline.getStatus());
        assertTrue(markedDeadline.isDone());

        // Test marking an Event task
        assertEquals(" ", eventTask.getStatus());
        Task markedEvent = taskList.mark(3);
        assertEquals("X", markedEvent.getStatus());
        assertTrue(markedEvent.isDone());
    }

    @Test
    @DisplayName("Test mark method throws exception for invalid index (too high)")
    public void testMark_invalidIndexTooHigh() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.mark(10);  // Only 3 tasks in the list
        });
    }

    @Test
    @DisplayName("Test mark method throws exception for invalid index (zero)")
    public void testMark_invalidIndexZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.mark(0);  // 1-based indexing, so 0 is invalid
        });
    }

    @Test
    @DisplayName("Test mark method throws exception for negative index")
    public void testMark_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            taskList.mark(-1);
        });
    }

    @Test
    @DisplayName("Test mark method on empty task list")
    public void testMark_emptyTaskList() {
        TaskList emptyList = new TaskList();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            emptyList.mark(1);
        });
    }

    @Test
    @DisplayName("Test mark method preserves task content and other properties")
    public void testMark_preservesTaskProperties() {
        // Test with Deadline task to ensure dates are preserved
        String originalContent = deadlineTask.getContent();
        String originalToString = deadlineTask.toString();

        Task markedTask = taskList.mark(2);

        // Content should remain the same
        assertEquals(originalContent, markedTask.getContent());

        // ToString should change (status changed from [ ] to [X])
        assertNotEquals(originalToString, markedTask.toString());
        assertTrue(markedTask.toString().contains("[X]"));
    }

    @Test
    @DisplayName("Test mark method with task list created from existing list")
    public void testMark_withPreloadedTaskList() {
        // Create a new task list from existing tasks
        ArrayList<Task> existingTasks = new ArrayList<>();
        ToDo preloadedTask = new ToDo("Preloaded task");
        existingTasks.add(preloadedTask);

        TaskList preloadedList = new TaskList(existingTasks);

        // Mark the preloaded task
        Task result = preloadedList.mark(1);

        assertEquals("X", result.getStatus());
        assertTrue(result.isDone());
        assertEquals(preloadedTask, result);
    }

}
