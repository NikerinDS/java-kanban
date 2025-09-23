package tasktracker.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void shouldBeEqualsIfIdIsSame() {
        Task firstTask = new Task(1,"1","1",TaskStatus.NEW);
        Task secondTask = new Task(1,"1","1",TaskStatus.NEW);
        assertEquals(firstTask, secondTask);
    }

    @Test
    void shouldNotBeEqualsIfIdIsDifferent() {
        Task firstTask = new Task(1,"1","1",TaskStatus.NEW);
        Task secondTask = new Task(2,"1","1",TaskStatus.NEW);
        assertNotEquals(firstTask, secondTask);
    }
}