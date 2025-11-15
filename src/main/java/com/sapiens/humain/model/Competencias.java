package com.sapiens.humain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competencias {
    private Long id;
    private String nome;
    private String categoria;
    private String descricao;
}
