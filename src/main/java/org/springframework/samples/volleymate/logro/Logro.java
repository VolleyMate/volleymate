package org.springframework.samples.volleymate.logro;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "logros")
@Getter
@Setter
public class Logro extends BaseEntity {

    @Column(name = "nombre")
    @NotBlank
    private String nombre;

   @Column(name = "descripcion")
    @NotBlank
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "threshold")
    @NotNull
    private Double threshold;
	
    @Column(name= "metrica")
    @NotBlank
	  private String metrica;

    @ManyToMany(mappedBy="logros")
    private List<Jugador> jugadores;

}
