package org.springframework.samples.volleymate.centro;

import org.springframework.samples.volleymate.model.BaseEntity;
import org.springframework.samples.volleymate.partido.Partido;
import org.springframework.samples.volleymate.jugador.Jugador;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.List;

@Entity
@Table(name = "centros")
@Getter
@Setter
public class Centro extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creador", referencedColumnName = "username")
	private Jugador creador;

    @Column(name = "nombre")
    private String nombre;

	@Column(name = "direccion")
    private String direccion;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "maps")
    private String maps;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "centro", cascade = CascadeType.ALL)
    private List<Partido> partidos;

}