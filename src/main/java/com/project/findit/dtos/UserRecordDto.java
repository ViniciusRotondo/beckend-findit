package com.project.findit.dtos;

import java.util.Date;

public record UserRecordDto(Date data_nascimento, String email, String nome, String senha, String telefone) {
}
