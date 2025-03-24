package com.project.findit.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "TB_ORGANIZADOR")
public class OrganizerModel extends PeopleModel implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID idOrganizador;

    @Column
    private String cpf;

    @Column
    private String cnpj;

    @Column
    private String nome_empresa;

    @Column
    private String endereco_empresa;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "organizador", fetch = FetchType.LAZY)
    private Set<EventModel> events = new HashSet<>();

}
