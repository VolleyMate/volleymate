package org.springframework.samples.volleymate.jugador;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.volleymate.aspecto.Aspecto;
import org.springframework.samples.volleymate.model.Person;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.user.User;
import org.springframework.samples.volleymate.valoracion.Valoracion;
import org.springframework.samples.volleymate.logro.Logro;

import javax.persistence.ForeignKey;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
public class Jugador extends Person{
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ciudad")
	private Ciudad ciudad;

    @Column(name = "telephone")
	private String telephone;

	@Enumerated(EnumType.STRING)
	@Column(name = "sexo")
    private Sexo sexo;

	@Column(name = "image")
	protected String image="/resources/images/perfilPorDefecto.png";

	@NotNull
	@Column(name = "volleys")
	private Integer volleys=150;

	@Column(name = "premium")
	private Boolean premium;

	@Column(name = "fecha_inicio_premium")
	private LocalDateTime fechaInicioPremium;

	@Column(name = "fecha_fin_premium")
	private LocalDateTime fechaFinPremium;
 
    @OneToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;


	@OneToMany(mappedBy = "ratedPlayer")
	@OnDelete(action = OnDeleteAction.CASCADE)
    private List<Valoracion> valoracionesRecibidas;

    @OneToMany(mappedBy = "ratingPlayer")
	@OnDelete(action = OnDeleteAction.CASCADE)
    private List<Valoracion> valoracionesDadas;

    @ManyToMany
	@JoinTable(name="logros_jugador",
				joinColumns = @JoinColumn(name="id_jugador"),
				inverseJoinColumns = @JoinColumn(name="id_logro"),
				foreignKey = @ForeignKey(name = "fk_logros_jugador_id_jugador", 
                             foreignKeyDefinition = "FOREIGN KEY (id_jugador) REFERENCES jugadores(id) ON DELETE CASCADE"))
    private List<Logro> logros;

	

	/*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jugador_partidos", 
            joinColumns = @JoinColumn(name = "jugador_id"),
            inverseJoinColumns = @JoinColumn(name = "partido_id"),
            foreignKey = @ForeignKey(name = "fk_jugador_partidos_jugador_id", 
                        foreignKeyDefinition = "FOREIGN KEY (jugador_id) REFERENCES jugadores(id) ON DELETE NO ACTION"),
            inverseForeignKey = @ForeignKey(name = "fk_jugador_partidos_partido_id",
                        foreignKeyDefinition = "FOREIGN KEY (partido_id) REFERENCES partidos(id) ON DELETE NO ACTION"))
    private Set<Partido> partidos;*/
	@ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "jugador_partidos",
            joinColumns = @JoinColumn(name = "jugador_id"),
            inverseJoinColumns = @JoinColumn(name = "partido_id"))
    private Set<Partido> partidos;


    
	@ManyToMany
	@JoinTable(
		name = "aspectos_jugador", 
		joinColumns = @JoinColumn(name = "jugador_id"), 
		inverseJoinColumns = @JoinColumn(name = "aspecto_id"),
		foreignKey = @ForeignKey(name = "fk_aspectos_jugador_jugador_id", 
                             foreignKeyDefinition = "FOREIGN KEY (jugador_id) REFERENCES jugadores(id) ON DELETE CASCADE"))
	private List<Aspecto> aspectos;

	public Double getValoracionMedia (){
		if(valoracionesRecibidas.isEmpty()){
			return 0.0;
		}else {
			return valoracionesRecibidas.stream().mapToDouble(Valoracion::getPuntuacion).sum()/valoracionesRecibidas.size();
		}
	}
}
