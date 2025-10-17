package tasktracker.manager;

import tasktracker.data.EpicTask;
import tasktracker.data.Subtask;
import tasktracker.data.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private Node first = null;
    private Node last = null;
    private final Map<Integer, Node> idToNode = new HashMap<>();

    @Override
    public void add(Task task) {
        if (idToNode.containsKey(task.getId())) {
            removeNode(idToNode.get(task.getId()));
        }
        Node newNode = linkLast(task);
        idToNode.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        if (idToNode.containsKey(id)) {
            removeNode(idToNode.get(id));
            idToNode.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyCopy = new ArrayList<>();
        Node node = first;
        while (node != null) {
            historyCopy.add(copy(node.task));
            node = node.next;
        }
        return historyCopy;
    }

    private Task copy(Task task) {
        return switch (task) {
            case EpicTask epicTask -> new EpicTask(epicTask);
            case Subtask subtask -> new Subtask(subtask);
            default -> new Task(task);
        };
    }

    private Node linkLast(Task task) {
        Node node = new Node(copy(task), null, last);
        if (last == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        return node;
    }

    private void removeNode(Node node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }

    private static class Node {
        Task task;
        Node next;
        Node prev;

        public Node(Task task, Node next, Node prev) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }
}
