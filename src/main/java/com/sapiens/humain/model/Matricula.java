package com.sapiens.humain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Matricula {
    private Long id;
    private Long id_usuario;
    private Long id_trilha;
    private Date data_inscricao;
}
