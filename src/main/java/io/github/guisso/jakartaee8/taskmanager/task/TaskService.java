package io.github.guisso.jakartaee8.taskmanager.task;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Stateless
public class TaskService
        implements TaskServiceLocal {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Task task) {
        if (entityManager.contains(task)) {
            // Update attached -- Ever used??
            System.out.println("TaskServiceBean::save[U].task => " + task);
            entityManager.persist(task);
        } else if (task.getId() != null) {
            // Detached entity
            System.out.println("TaskServiceBean::save[U'].task => " + task);

//            task.getPersons().forEach(p -> {
//                entityManager.merge(p);
//            });

            entityManager.merge(task);
        } else {
            // Create new
            System.out.println("TaskServiceBean::save[S].task => " + task);
            
            // entityManager.persist(task);
            // Forces the merge to all related entities
            // (CascadeType.ALL) and avoids an exception.
            // However performance degrades. Review required.
            entityManager.merge(task);
        }
    }

    @Override
    public void moveToTrash(Task task) {
        task.setTrash(true);
        entityManager.merge(task);
    }

    @Override
    public List<Task> findAll() {
        return entityManager
                .createNamedQuery(
                        "task.findAll",
                        Task.class)
                .getResultList();
    }

    @Override
    public List<Task> loadTrash() {
        return entityManager
                .createNamedQuery(
                        "task.loadTrash",
                        Task.class)
                .getResultList();
    }

    @Override
    public Task loadTaskByIdWithPersons(Long id) {
//        System.out.println("TaskService::loadTaskByIdWithPersons().id => " + id);
        return entityManager
                .createNamedQuery(
                        "task.loadTaskByIdWithPersons",
                        Task.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
