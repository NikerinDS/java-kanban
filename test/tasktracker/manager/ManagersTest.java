package tasktracker.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void getDefaultTaskManagerShouldReturnInMemoryTaskManager() {
        TaskManager taskManager = Managers.getDefaultTaskManager();
        assertInstanceOf(InMemoryTaskManager.class, taskManager);
    }

    @Test
    void getDefaultHistoryManagerShouldReturnInMemoryHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistoryManager();
        assertInstanceOf(InMemoryHistoryManager.class, historyManager);
    }
}