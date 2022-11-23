package io.github.guisso.jakartaee8.taskmanager.person;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Named(value = "personBean")
//@RequestScoped
//@SessionScoped
@ViewScoped
public class PersonBean
        implements Serializable {

    @Inject
    private PersonServiceLocal personService;

    private Person selectedPerson;

    // Retains all the persons from database
    private List<Person> persons;

    public PersonBean() {
        
    }
    
    @PostConstruct
    public void init() {
        if (persons == null) {
            persons = findAll();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    //</editor-fold>

    public List<Person> findAll() {
        return personService.findAll();
    }

    public Person findPersonById(Long id) {
        return personService.findPersonById(id);
    }

}
