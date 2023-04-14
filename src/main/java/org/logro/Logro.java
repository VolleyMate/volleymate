package org.logro;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "logros")
@Getter
@Setter
public class Logro extends BaseEntity {

  @OneToOne
  private Jugador holder;

  private Valoracion valoracion;

}