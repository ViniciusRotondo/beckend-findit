package com.project.findit.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_LOCAL")
public class LocationModel implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID idLocal;

    @Column
    private String nome;

    @Column
    private String endereco;

    @Column
    private Integer capacidade_de_pessoas;

    @Column
    private String url_mapa;

    @Column
    private String telefone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "local", fetch = FetchType.LAZY)
    private Set<EventModel> events = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private CityModel cidades;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private StateModel estados;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private CountryModel paises;

}
