package com.project.lms.entities;

import javax.persistence.*;

@Entity
@Table(name = "UnitBook")
public class UnitBook {
	
	@Id
	@Column(name="unitBookId")
	private String id;
	
	@Column(name="assigned")
	private boolean assigned;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
		
}
