package io.github.guisso.jakartaee8.taskmanager.person;

import io.github.guisso.jakartaee8.taskmanager.entity.JpaEntity;
import io.github.guisso.jakartaee8.taskmanager.task.Task;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "person.loadAll",
            query = "select p from Person p "
            + "order by p.name"),
    @NamedQuery(
            name = "person.findAll",
            query = "select distinct p from Person p "
            //            + "left join fetch p.tasks " // TODO Excluir
            + "where p.trash = false "
            + "order by p.name"),
    @NamedQuery(
            name = "person.loadTrash",
            query = "select p from Person p "
            + "where p.trash = true "
            + "order by p.name"
    ),
    @NamedQuery(
            name = "person.findPersonById",
            query = "select p from Person p "
            //            + "left join fetch p.tasks "
            + "where p.trash = false and p.id = :id "
            + "order by p.name"
    )
})
public class Person extends JpaEntity
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 50)
    @NotEmpty // Jakarta EE Validation
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    @NotEmpty // Jakarta EE Validation
    @Email // Jakarta EE Validation
    private String email;

    @Column(nullable = false)
    private Integer cellphone;

    @Column(columnDefinition = "DATE")
    @Past // Jakarta EE Validation
    private LocalDate birthday;

    @Transient
    private byte age;

    @Column(nullable = true)
    @ManyToMany(mappedBy = "persons",
            cascade = {CascadeType.ALL})
    // CascadeType.PERSIST, CascadeType.MERGE, ...
    private List<Task> tasks;

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCellphone() {
        return cellphone;
    }

    public void setCellphone(Integer cellphone) {
        this.cellphone = cellphone;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday != null) {
            age = (byte) Period
                    .between(LocalDate.now(), birthday)
                    .getYears();
        } else {
            age = 0;
        }

        this.birthday = birthday;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="hashCode/equals/toString">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public String toString() {
        return "io.github.guisso.jakartaee8.taskmanager.person.Person[ id=" + getId()
                + ", uuid=" + getUuid()
                + ", name=" + name
//                + ", tasks[persons != null]=" + tasks
//                        .stream()
//                        .filter(t -> t.getPersons() != null)
//                        .map(t -> t.getId().toString())
//                        .collect(Collectors
//                                .joining(", ", "[ ", " ]"))
//                + ", tasks[persons == null]=" + tasks
//                        .stream()
//                        .filter(t -> t.getPersons() == null)
//                        .map(t -> t.getId().toString())
//                        .collect(Collectors
//                                .joining(", ", "[ ", " ]"))
                + " ]";
    }
    //</editor-fold>

}
