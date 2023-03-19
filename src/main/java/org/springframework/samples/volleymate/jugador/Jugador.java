package org.springframework.samples.volleymate.jugador;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.EnumType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;


import org.springframework.samples.volleymate.model.Person;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.valoracion.Valoracion;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
public class Jugador extends Person{
	@Column(name = "ciudad")
	@NotEmpty
	private String ciudad;

    @Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 9)
	private String telephone;

	@Enumerated(EnumType.STRING)
	@Column(name = "sexo")
    private Sexo sexo;

	@Column(name = "image")
	protected String image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;


    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "jugador_partidos", joinColumns = @JoinColumn(name = "jugador_id"),
			inverseJoinColumns = @JoinColumn(name = "partido_id"))
	private Set<Partido> partidos;

	@OneToMany(mappedBy = "ratedPlayer", cascade = CascadeType.ALL)
    private List<Valoracion> valoracionesRecibidas;

    @OneToMany(mappedBy = "ratingPlayer", cascade = CascadeType.ALL)
    private List<Valoracion> valoracionesDadas;
    
}
