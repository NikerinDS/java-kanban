package tasktracker.data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest {
    @Test
    void shouldBeEqualsIfIdIsSame() {
        EpicTask firstTask = new EpicTask(1,"1","1",new ArrayList<>(),TaskStatus.NEW);
        EpicTask secondTask = new EpicTask(1,"1","1",new ArrayList<>(),TaskStatus.NEW);
        assertEquals(firstTask, secondTask);
    }

    @Test
    void shouldNotBeEqualsIfIdIsDifferent() {
        EpicTask firstTask = new EpicTask(1,"1","1",new ArrayList<>(),TaskStatus.NEW);
        EpicTask secondTask = new EpicTask(2,"1","1",new ArrayList<>(),TaskStatus.NEW);
        assertNotEquals(firstTask, secondTask);
    }
}