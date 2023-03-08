package org.springframework.samples.petclinic.solicitud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.jugador.Jugador;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.partido.Partido;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
public class Solicitud extends BaseEntity {
  
  @Column(name = "jugador")
  @NotEmpty
  private Jugador jugador;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name="solicitudes_partido", joinColumns = @JoinColumn(name = "solicitudId"), 
        inverseJoinColumns = @JoinColumn(name = "partidoId"))
  private Partido partido;

  @Column(name = "estado")
  @NotEmpty
  private EstadoSolicitud estado;

}
