package io.github.guisso.jakartaee8.taskmanager.task;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Local
public interface TaskServiceLocal {

    public void save(Task task);

    public void moveToTrash(Task task);

    public List<Task> findAll();

    public List<Task> loadTrash();

    public Task loadTaskByIdWithPersons(Long id);

}
