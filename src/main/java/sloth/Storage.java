package sloth;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

/**
 * Handles persistent storage of tasks to and from the file system.
 * Uses a simple text file format to store task data in the data/sloth.txt file.
 */
public class Storage {
    private static final Path dataDir = Paths.get("data");
    private static final Path dataFile = dataDir.resolve("sloth.txt");

    /**
     * Loads tasks from the storage file.
     * Creates the data directory and file if they don't exist.
     * Skips corrupted lines and continues loading valid tasks.
     *
     * @return ArrayList of loaded tasks, empty if no file exists or on first run
     */
    public static ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            if (Files.notExists(dataDir)) Files.createDirectories(dataDir);
            if (Files.notExists(dataFile)) {
                Files.createFile(dataFile);
                return tasks; // empty (first run)
            }
            List<String> lines = Files.readAllLines(dataFile);
            for (String line : lines) {
                if (line.isEmpty()) continue;
                try {
                    tasks.add(TaskParser.parseStorage(line));
                } catch (SlothException ex) {
                    // corrupted line; skip but keep going
                    System.err.println("Skipping corrupted line: " + line + "  Reason: " + ex.getMessage());
                }
            }
        } catch (IOException ioe) {
            System.err.println("Failed to load tasks: " + ioe.getMessage());
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to the storage file.
     * Creates the data directory if it doesn't exist.
     * Overwrites the existing file with the current task list.
     *
     * @param tasks the list of tasks to save
     */
    public static void save(ArrayList<Task> tasks) {
        ArrayList<String> lines = new ArrayList<>();
        for (Task t : tasks) lines.add(t.to_storage_string());
        try {
            if (Files.notExists(dataDir)) Files.createDirectories(dataDir);
            Files.write(dataFile, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException ioe) {
            System.err.println("Failed to save tasks: " + ioe.getMessage());
        }
    }
}
