package io.github.guisso.jakartaee8.taskmanager.person;

import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 */
@Singleton
@Startup
public class PersonPopulateService {

    @Inject
    private PersonServiceLocal personService;

    @PostConstruct
    public void populateDatabase() {
        Person[] persons = new Person[5];

        persons[0] = new Person("Ana Zaira", "ana@mail.com", 911111111, LocalDate.of(2001, 1, 1));
        persons[1] = new Person("Beatriz Yana", "beatriz@mail.com", 922222222, LocalDate.of(2002, 2, 2));
        persons[2] = new Person("Cecília Xerxes", "cecilia@mail.com", 933333333, LocalDate.of(2003, 3, 3));
        persons[3] = new Person("Débora Wendel", "debora@mail.com", 944444444, LocalDate.of(2004, 4, 4));
        persons[4] = new Person("Eugênia Vasconcelos", "eugenia@mail.com", 955555555, LocalDate.of(2005, 5, 5));

        for (Person person : persons) {
            System.out.println("> " + person);
            personService.save(person);
        }
    }
}
