package com.example.server.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

/** 
 * EntityBase, base entity for all entities. 
 */
@MappedSuperclass
public abstract class EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	private Integer version = 0;
	
	/**
     * Auto-increment version # whenever persisted
     */
    @PrePersist
    void onPersist()
    {
        this.version++;
    }    
    
    public Long getId() {
		return id;
	}

	public Integer getVersion() {
        return version;
    }
 
}
