package org.springframework.samples.volleymate.partido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.model.BaseEntity;
import org.springframework.samples.volleymate.solicitud.Solicitud;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "partidos")
@Getter
@Setter
public class Partido extends BaseEntity {

    @NotBlank
    private String nombre;

    @Enumerated(EnumType.STRING)
	@Column(name = "sexo")
    private Sexo sexo;

    @NotBlank
    private String descripcion;

    @Column(name = "tipo")
    @NotEmpty
    private Tipo tipo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creador", referencedColumnName = "username")
	private Jugador creador;

    @NotBlank
    @Column(name = "num_jugadores")
    private Integer numJugadoresNecesarios;

    @NotBlank
    private String lugar;
    
    @NotEmpty
	private LocalDateTime fecha;

    @DecimalMin(value="0.0",inclusive = false)
    @Digits(integer=3, fraction=2)        
	private BigDecimal precioPersona;

    @Column(name = "fecha_creacion", updatable = false, nullable = false)
    private LocalDateTime fechaCreacion=LocalDateTime.now();

    @OneToMany(mappedBy = "partido")
    private Set<Solicitud> solicitudes;

    @ManyToMany(mappedBy = "partidos")
    private List<Jugador> jugadores;

    public String getFechaParseada(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd'/'MM'/'yyyy");
		return formato.format(fecha);
    }
    
    public String getFechaCreacionParseada() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm dd'/'MM'/'yyyy");
		return formato.format(fechaCreacion);
	}

}