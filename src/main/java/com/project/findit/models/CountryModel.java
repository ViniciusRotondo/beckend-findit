package com.project.findit.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "TB_PAIS")
public class CountryModel {
    @Id
    @Column
    private String sigla;

    @Column
    private String nome;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "paises", fetch = FetchType.LAZY)
    private Set<LocationModel> location = new HashSet<>();

}
