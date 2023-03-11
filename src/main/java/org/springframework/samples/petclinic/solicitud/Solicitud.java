package org.springframework.samples.petclinic.solicitud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
  @NotNull
  private Jugador jugador;

  @Column(name = "partido")
  @NotNull
  private Partido partido;

}
