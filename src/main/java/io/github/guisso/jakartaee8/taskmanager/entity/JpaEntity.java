package io.github.guisso.jakartaee8.taskmanager.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private Boolean trash;

    public JpaEntity() {
        trash = false;
        uuid = UUID.randomUUID();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getTrash() {
        return trash;
    }

    public void setTrash(Boolean trash) {
        this.trash = trash;
    }
    //</editor-fold>

}
