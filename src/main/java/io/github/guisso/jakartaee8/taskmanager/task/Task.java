package io.github.guisso.jakartaee8.taskmanager.task;

import io.github.guisso.jakartaee8.taskmanager.entity.JpaEntity;
import io.github.guisso.jakartaee8.taskmanager.person.Person;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "task.loadAll",
            query = "select distinct t from Task t "
            + "order by t.id"
    ),
    @NamedQuery(
            name = "task.findAll",
            query = "select distinct t from Task t "
            + "left join fetch t.persons " // Bagdes, ...
            + "where t.trash = false "
            + "order by t.id"
    ),
    @NamedQuery(
            name = "task.loadTrash",
            query = "select distinct t from Task t "
            + "where t.trash = true "
            + "order by t.id"
    ),
    @NamedQuery(
            name = "task.findTasksNotConcluded",
            query = "select distinct t from Task t "
            + "where t.conclusion = null "
            + "order by t.id"
    ),
    @NamedQuery(
            name = "task.findTasksConcluded",
            query = "select distinct t from Task t "
            + "where t.conclusion != null "
            + "order by t.id"
    ),
    @NamedQuery(
            name = "task.findExpiredTasks",
            query = "select distinct t from Task t "
            + "where t.expiration < LocalDate.now() "
            + "order by t.id"
    ),
    @NamedQuery(
            name = "task.loadTaskByIdWithPersons",
            query = "select distinct t from Task t "
            + "left join fetch t.persons "
            + "where t.trash = false and t.id = :id "
            + "order by t.id"
    )
})
public class Task extends JpaEntity
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 150)
    @NotEmpty // Jakarta EE Validation
    private String description;

    @Min(value = 1) // Jakarta EE Validation
    @Max(value = 10) // Jakarta EE Validation
    private Byte priority;

    @Column(precision = 7, scale = 2)
    @DecimalMin(value = "0.00") // Jakarta EE Validation
    @DecimalMax(value = "99999.99") // Jakarta EE Validation
    private BigDecimal cost;

    @Column(columnDefinition = "DATE")
    @FutureOrPresent // Jakarta EE Validation
    private LocalDate expiration;

    @Column(columnDefinition = "DATE")
    @PastOrPresent // Jakarta EE Validation
    private LocalDate conclusion;

    @Column(nullable = true)
    @ManyToMany(
            cascade = {CascadeType.ALL})
    @JoinTable(
            name = "tasks_persons",
            joinColumns
            = @JoinColumn(name = "task_id"),
            inverseJoinColumns
            = @JoinColumn(name = "person_id")
    )
    private List<Person> persons;

    public Task() {
        super();
        cost = BigDecimal.ZERO;
        persons = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
//        System.out.println("Task::setDescription() => " + description);
        this.description = description;
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public LocalDate getConclusion() {
        return conclusion;
    }

    public void setConclusion(LocalDate conclusion) {
        this.conclusion = conclusion;
    }

    public List<Person> getPersons() {
//        System.out.println("Task::getPersons => " + persons);
        for (Person p : persons) {
            System.out.println("---> " + p);
        }
        return persons;
    }

    public void setPersons(List<Person> persons) {
        // TODO Ajustar bidirecionalidade
//        persons.stream().forEach(p -> p.setTasks(tasks));
        this.persons = persons;
    }
    //</editor-fold>

    public String allocatedPersons() {
        if (!persons.isEmpty()) {
            return persons
                    .stream()
                    .filter(p -> p != null) // TODO Necessary?
                    .map(p -> p.getName())
                    .collect(Collectors
                            .joining(", ", "[ ", " ]"));
        } else {
            return null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="hashCode/equals/toString">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.getId());
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
        final Task other = (Task) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public String toString() {
        return "Task[ id=" + getId()
                + ", description=" + description
                + ", persons=" + persons
                + " ]";
    }
    //</editor-fold>

}
