package com.project.findit.models;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class PeopleModel {
    private String nome;
    private String email;
    private String senha;
}
