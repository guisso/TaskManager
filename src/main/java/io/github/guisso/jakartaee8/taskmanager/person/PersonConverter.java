package io.github.guisso.jakartaee8.taskmanager.person;

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
@FacesConverter(value = "personConverter", managed = true)
@ApplicationScoped
public class PersonConverter
    implements Converter<Person>{
    
    @Inject
    private PersonServiceBeanLocal personService;

    @Override
    public Person getAsObject(
            FacesContext context, 
            UIComponent component, 
            String id) {
        if (id == null) {
            return null;
        }
        return personService
                .findPersonById(Long.parseLong(id));
    }

    @Override
    public String getAsString(
            FacesContext context, 
            UIComponent component, 
            Person person) {
        if (person == null) {
            return null;
        }
        return person.getId().toString();
    }
    
}
