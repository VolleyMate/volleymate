package org.springframework.samples.volleymate.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
	
	@Id
	String username;
	
	String password;
	
	boolean enabled;

	String correo;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Authorities> authorities;
}
