package io.github.guisso.jakartaee8.taskmanager.person;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Stateless
public class PersonService 
        implements PersonServiceBeanLocal {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Override
    public List<Person> findAll() {
        return entityManager
                .createNamedQuery(
                        "person.findAll",
                        Person.class)
                .getResultList();
    }

    @Override
    public Person findPersonById(Long personId) {
        return entityManager
                .createNamedQuery(
                        "person.findPersonById",
                        Person.class)
                .setParameter("id", personId)
                .getSingleResult();
    }

}
