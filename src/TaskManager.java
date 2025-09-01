import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> simpleTasks;

    public TaskManager() {
        this.simpleTasks = new HashMap<>();
    }

    public void createSimpleTask(Task task) {
        simpleTasks.put(task.getId(), task);
    }

    public void updateSimpleTask(Task task) {
        simpleTasks.put(task.getId(), task);
    }

    public ArrayList<Task> getListOfSimpleTasks() {
        return new ArrayList<>(simpleTasks.values());
    }

    public void deleteAllSimpleTasks() {
        simpleTasks.clear();
    }

    public Task getSimpleTaskById(int id) {
        return simpleTasks.get(id);
    }

    public void deleteSimpleTaskById(int id) {
        simpleTasks.remove(id);
    }
}
