package org.springframework.samples.volleymate.aspecto;

import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.model.BaseEntity;

import java.util.List;

import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "aspectos")
@Getter
@Setter
public class Aspecto extends BaseEntity {

    @Column(name = "imagen")
    private String imagen;

	@Column(name = "precio")
    private Integer precio;

    @ManyToMany(mappedBy = "aspectos")
    private List<Jugador> jugadores;

    
}
