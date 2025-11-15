package com.sapiens.humain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trilha {
    private Long id;
    private String nome;
    private String descricao;
    private String nivel;
    private int carga_horaria;
    private String foco_trilha;
}
