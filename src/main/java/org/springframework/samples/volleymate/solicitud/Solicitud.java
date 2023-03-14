package org.springframework.samples.volleymate.solicitud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.model.BaseEntity;
import org.springframework.samples.volleymate.partido.Partido;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "solicitudes", uniqueConstraints = {@UniqueConstraint(columnNames = {"jugador_id", "partido_id"})})
@Getter
@Setter
public class Solicitud extends BaseEntity {
  
  @NotNull
  @ManyToOne
  @JoinColumn(name = "jugador_id")
  private Jugador jugador;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "partido_id")
  private Partido partido;
}
