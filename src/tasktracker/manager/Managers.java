package tasktracker.manager;

public class Managers {
    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }
}
