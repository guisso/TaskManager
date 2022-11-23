package io.github.guisso.jakartaee8.taskmanager.task;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Named(value = "taskBean")
//@RequestScoped
//@SessionScoped
@ViewScoped
public class TaskBean implements Serializable {

    @Inject
    private TaskServiceLocal taskService;

    private Task selectedTask;

    // Auxilar variable to retrieve the selected task 
    // by f:viewParam
    private Long selectedTaskId;

    // W/o this, the datatable column sort will not work
    private List<Task> allTasks;

    // W/o this, the datatable column sort will not work
    private List<Task> trashTasks;

    // Reserved for datatable filtered tasks
    private List<Task> filteredTasks;

    private LocalDate minDate;
    private LocalDate maxDate;

    public TaskBean() {

    }

    @PostConstruct
    public void init() {
        // First execution
        if (selectedTask == null) {
            selectedTask = new Task();
        }
        minDate = maxDate = LocalDate.now();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Task getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }

    public Long getSelectedTaskId() {
        return selectedTaskId;
    }

    public void setSelectedTaskId(Long selectedTaskId) {
        // Edit?
        if (selectedTaskId != null) {
            selectedTask = taskService.loadTaskByIdWithPersons(selectedTaskId);
        }

        // New task
        if (selectedTaskId == null || selectedTask == null) {
            selectedTask = new Task();
        }

        this.selectedTaskId = selectedTaskId;
    }

    public LocalDate getMinDate() {
        return minDate;
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public List<Task> getAllTasks() {
        if (allTasks == null) {
            reloadTasks();
        }
        return allTasks;
    }

    public List<Task> getTrashTasks() {
        if (trashTasks == null) {
            reloadTrashTasks();
        }
        return trashTasks;
    }

    public List<Task> getFilteredTasks() {
        return filteredTasks;
    }

    public void setFilteredTasks(List<Task> filteredTasks) {
        this.filteredTasks = filteredTasks;
    }
    //</editor-fold>

    public String save(Task task) {
        taskService.save(task);
        reloadTasks();
        reset();
        return null;
    }

    public String saveCurrent() {
        save(selectedTask);
        return "index?faces-redirect=true";
    }

    public String moveToTrash(Task task) {
        taskService.moveToTrash(task);
        reloadTasks();
        return null;
    }

    public String moveCurrentToTrash() {
        moveToTrash(selectedTask);
        return "index?faces-redirect=true";
    }

    public List<Task> loadAll() {
        return null;
    }

    public List<Task> findAll() {
        return taskService.findAll();
    }

    public List<Task> loadTrash() {
        return taskService.loadTrash();
    }

    public List<Task> findTasksNotConcluded() {
        return null;
    }

    public List<Task> findTasksConcluded() {
        return null;
    }

    public List<Task> findExpiredTasks() {
        return null;
    }

    public Task loadTaskWithPersons(Task task) {
        if (task != null) {
            Task fullTask = taskService.loadTaskByIdWithPersons(task.getId());
            selectedTask = fullTask;
            return selectedTask;
        } else {
            return null;
        }

    }

    public void onPersonSelected() {
        System.out.println("TaskBean::onPersonSelected/Task = > " + selectedTask);
        System.out.println("TaskBean::onPersonSelected/Persons = > " + selectedTask.getPersons());
    }

    public void reset() {
        selectedTask = new Task();
    }

    private void reloadTasks() {
        allTasks = findAll();
    }

    private void reloadTrashTasks() {
        allTasks = loadTrash();
    }

}
