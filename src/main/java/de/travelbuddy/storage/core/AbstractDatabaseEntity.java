package de.travelbuddy.storage.core;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
public abstract class AbstractDatabaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    /*
    JPA Helper Methods
     */

    @PrePersist
    void onCreate()
    {
        this.setCreated( new Date() );
    }
    @PreUpdate
    void onUpdate()
    {
        this.setModified( new Date() );
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }


}