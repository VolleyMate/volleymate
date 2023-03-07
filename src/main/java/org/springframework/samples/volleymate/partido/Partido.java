package org.springframework.samples.volleymate.partido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.samples.volleymate.jugador.Jugador;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "partidos")
@Getter
@Setter
public class Partido{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToMany(mappedBy = "partidos")
    private List<Jugador> jugadores;

}