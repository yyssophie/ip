package sloth;

import java.nio.file.*;
import java.util.*;
import java.io.IOException;

public class Storage {
    private static final Path dataDir = Paths.get("data");
    private static final Path dataFile = dataDir.resolve("sloth.txt");

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
                    tasks.add(TaskParser.parse_storage(line));
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
