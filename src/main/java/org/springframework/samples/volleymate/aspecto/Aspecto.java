package org.springframework.samples.volleymate.aspecto;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.samples.volleymate.jugador.Jugador;
import org.springframework.samples.volleymate.model.BaseEntity;

import java.util.List;

import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Jugador> jugadores;


    
}
