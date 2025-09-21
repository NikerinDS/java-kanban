package tasktracker.data;

import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {
    private final List<Integer> subtasksId;

    public EpicTask(int id, String name, String description, List<Integer> subtasksId, TaskStatus taskStatus) {
        super(id, name, description, taskStatus);
        this.subtasksId = new ArrayList<>(subtasksId);
    }

    public EpicTask(EpicTask task) {
        this(task.getId(), task.getName(), task.getDescription(), task.subtasksId, task.getStatus());
    }

    public EpicTask(String name, String description) {
        this(0, name, description, new ArrayList<>(), TaskStatus.NEW);
    }

    public List<Integer> getSubtasksId() {
        return new ArrayList<>(subtasksId);
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasksId=" + subtasksId +
                '}';
    }
}
