package com.project.findit.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "TB_ESTADO")
public class StateModel {

    @Id
    @Column
    private String sigla;

    @Column
    private String nome;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "estados", fetch = FetchType.LAZY)
    private Set<LocationModel> location = new HashSet<>();

}
