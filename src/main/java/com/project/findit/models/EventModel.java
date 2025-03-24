package com.project.findit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="TB_EVENTO")
public class EventModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID idEvento;

    @Column
    private String nome_do_evento;

    @Column
    private String descricao;

    @Column
    private String data_hora;

    @Column
    private String url_imagem;

    @Column
    private Double preco;

    @Column
    private Double duracao;

    @Column
    private Integer indicativo_idade;

    @Column
    private String telefone;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "local_id")
    private LocationModel local;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoryModel categoria;

    @ManyToOne
    @JoinColumn(name = "organizador_id")
    private OrganizerModel organizador;

    @ManyToMany
    @JoinTable(
            name = "tb_user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserModel> users = new HashSet<>();
}
