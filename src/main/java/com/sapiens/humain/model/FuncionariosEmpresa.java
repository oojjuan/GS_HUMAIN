package com.sapiens.humain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionariosEmpresa {
    private Long id_empresa;
    private Long id_usuario;
    private String cargo;
}
