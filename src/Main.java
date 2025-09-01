public class Main {
    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        System.out.println(tm.getListOfSimpleTasks());

        Task task1 = new Task("1", "помыть полы");
        Task task2 = new Task("2", "");
        Task task3 = new Task("3", "купить продукты");
        Task task4 = new Task("4", "поспать");
        tm.createSimpleTask(task1);
        tm.createSimpleTask(task2);
        tm.createSimpleTask(task3);
        tm.createSimpleTask(task4);
        System.out.println(tm.getListOfSimpleTasks());

        task2 = new Task(task2.getId(), task2.getName(), "выкинуть мусор", TaskStatus.IN_PROGRESS);
        task4 = new Task(task4.getId(), task4.getName(), task4.getDescription(), TaskStatus.DONE);

        tm.updateSimpleTask(task2);
        tm.updateSimpleTask(task4);
        System.out.println(tm.getListOfSimpleTasks());

        System.out.println();
        int id = task3.getId();
        System.out.println(tm.getSimpleTaskById(id));

        tm.deleteSimpleTaskById(id);
        System.out.println(tm.getListOfSimpleTasks());

        tm.deleteAllSimpleTasks();
        System.out.println(tm.getListOfSimpleTasks());
    }
}
