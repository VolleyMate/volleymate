package org.springframework.samples.volleymate.partido;

import org.springframework.samples.volleymate.model.BaseEntity;
import javax.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Table(name = "centros")
@Getter
@Setter
public class Centro extends BaseEntity {

    @Column(name = "nombre")
    private String nombre;

	@Column(name = "direccion")
    private String direccion;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "maps")
    private String maps;

    @OneToMany(mappedBy = "centro")
    private List<Partido> partidos;

}