package sloth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class TaskParserTest {

    @Test
    @DisplayName("Test parseFlexibleDateTime with valid MMM dd yyyy, HH:mm format")
    public void testParseFlexibleDateTime_ValidMMMddFormat() throws SlothException {
        // Test with April format
        LocalDateTime result = TaskParser.parseFlexibleDateTime("Apr 18 2025, 18:00");
        LocalDateTime expected = LocalDateTime.of(2025, 4, 18, 18, 0);
        assertEquals(expected, result);

        // Test with December format
        result = TaskParser.parseFlexibleDateTime("Dec 25 2024, 23:59");
        expected = LocalDateTime.of(2024, 12, 25, 23, 59);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test parseFlexibleDateTime with valid MMM d yyyy, HH:mm format (single digit day)")
    public void testParseFlexibleDateTime_ValidMMMdFormat() throws SlothException {
        LocalDateTime result = TaskParser.parseFlexibleDateTime("Jan 5 2025, 09:30");
        LocalDateTime expected = LocalDateTime.of(2025, 1, 5, 9, 30);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test parseFlexibleDateTime with valid yyyy-MM-dd HH:mm format")
    public void testParseFlexibleDateTime_ValidISOFormat() throws SlothException {
        LocalDateTime result = TaskParser.parseFlexibleDateTime("2025-03-15 14:30");
        LocalDateTime expected = LocalDateTime.of(2025, 3, 15, 14, 30);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test parseFlexibleDateTime with valid d/M/yyyy HH:mm format")
    public void testParseFlexibleDateTime_ValidSlashFormat() throws SlothException {
        LocalDateTime result = TaskParser.parseFlexibleDateTime("2/12/2025 18:00");
        LocalDateTime expected = LocalDateTime.of(2025, 12, 2, 18, 0);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test parseFlexibleDateTime with valid ISO_LOCAL_DATE_TIME format")
    public void testParseFlexibleDateTime_ValidISOLocalDateTime() throws SlothException {
        LocalDateTime result = TaskParser.parseFlexibleDateTime("2025-10-15T18:00");
        LocalDateTime expected = LocalDateTime.of(2025, 10, 15, 18, 0);
        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Test parseFlexibleDateTime throws exception for invalid format")
    public void testParseFlexibleDateTime_InvalidFormat() {
        SlothException exception = assertThrows(SlothException.class, () -> {
            TaskParser.parseFlexibleDateTime("invalid date format");
        });

        assertTrue(exception.getMessage().contains("Little sloth don't understand this date format!"));
        assertTrue(exception.getMessage().contains("Try e.g. Apr 18 2025, 18:00"));
    }

    @Test
    @DisplayName("Test parseFlexibleDateTime throws exception for partially valid format")
    public void testParseFlexibleDateTime_PartiallyValidFormat() {
        SlothException exception = assertThrows(SlothException.class, () -> {
            TaskParser.parseFlexibleDateTime("Apr 18 2025");  // Missing time
        });

        assertTrue(exception.getMessage().contains("Little sloth don't understand this date format!"));
    }

    @Test
    @DisplayName("Test parseFlexibleDateTime throws exception for empty string")
    public void testParseFlexibleDateTime_EmptyString() {
        SlothException exception = assertThrows(SlothException.class, () -> {
            TaskParser.parseFlexibleDateTime("");
        });

        assertTrue(exception.getMessage().contains("Little sloth don't understand this date format!"));
    }

    @Test
    @DisplayName("Test parseFlexibleDateTime throws exception for null input")
    public void testParseFlexibleDateTime_NullInput() {
        assertThrows(NullPointerException.class, () -> {
            TaskParser.parseFlexibleDateTime(null);
        });
    }

}