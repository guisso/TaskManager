package io.github.guisso.jakartaee8.taskmanager.task;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@FacesConverter(value = "taskConverter", managed = true)
@ApplicationScoped
public class TaskConverter
        implements Converter<Task> {

    @Inject
    private TaskServiceLocal taskService;

    @Override
    public Task getAsObject(
            FacesContext context,
            UIComponent component,
            String id) {
        if (id == null) {
            return null;
        }
        return taskService
                .loadTaskByIdWithPersons(Long.valueOf(id));
    }

    @Override
    public String getAsString(
            FacesContext context,
            UIComponent component,
            Task task) {
        if (task == null) {
            return null;
        }
        return task.getId().toString();
    }

}
