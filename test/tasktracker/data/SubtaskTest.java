package tasktracker.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    void shouldBeEqualsIfIdIsSame() {
        Subtask firstTask = new Subtask(1,"1","1",3, TaskStatus.NEW);
        Subtask secondTask = new Subtask(1,"1","1",3,TaskStatus.NEW);
        assertEquals(firstTask, secondTask);
    }

    @Test
    void shouldNotBeEqualsIfIdIsDifferent() {
        Subtask firstTask = new Subtask(1,"1","1",3, TaskStatus.NEW);
        Subtask secondTask = new Subtask(2,"1","1",3,TaskStatus.NEW);
        assertNotEquals(firstTask, secondTask);
    }
}