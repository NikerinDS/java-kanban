import java.util.ArrayList;

public class EpicTask extends Task {
    private ArrayList<Integer> subtasksId;

    public EpicTask(int id, String name, String description, ArrayList<Integer> subtasksId, TaskStatus taskStatus) {
        super(id, name, description, taskStatus);
        this.subtasksId = new ArrayList<>(subtasksId);
    }

    public EpicTask(String name, String description) {
        super(name, description);
        this.subtasksId = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksId() {
        return new ArrayList<>(subtasksId);
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtasks.size=" + subtasksId.size() +
                '}';
    }
}
