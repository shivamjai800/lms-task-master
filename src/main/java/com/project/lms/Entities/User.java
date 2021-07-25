package com.project.lms.Entities;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.security.DeclareRoles;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity

@Table(name = "Userr")
public class User {
    @Id
    @NotBlank(message = "it should not be blank ")
    @Column(name="username")
    String username;
    
    @NotBlank(message = "it should not be blank ")
    @Size(min = 1, max = 25, message = "About Me must be between 1 and 25 characters")
    String name;
    
    @NotBlank(message = "it should not be blank ")
    String password;
    
    @NotBlank(message = "Please Choose the role")
    String roles;
    
    boolean active;
    
    @OneToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name="username")
    List<BookRecord> request;
    
 
    public User() {
        super();
    }
    
	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    
    
	public List<BookRecord> getRequest() {
		return request;
	}

	public void setRequest(List<BookRecord> request) {
		this.request = request;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", password=" + password + ", roles=" + roles
				+ ", active=" + active + ", request=" + request + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, name, password, request, roles, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return active == other.active && Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(request, other.request) && Objects.equals(roles, other.roles)
				&& Objects.equals(username, other.username);
	}
	
    
}

