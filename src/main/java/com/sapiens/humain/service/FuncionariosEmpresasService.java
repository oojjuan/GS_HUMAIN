package com.sapiens.humain.service;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.dao.FuncionariosEmpresaDAO;
import com.sapiens.humain.model.FuncionariosEmpresa;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionariosEmpresasService {
    private final FuncionariosEmpresaDAO dao;
    public FuncionariosEmpresasService(FuncionariosEmpresaDAO dao) {
        this.dao = dao;
    }

    public List<FuncionariosEmpresa> listarFuncionariosEmpresa(Long idEmpresa) {
        validarIdEmpresa(idEmpresa);
        return  dao.listarFuncionariosEmpresa(idEmpresa);
    }

    public FuncionariosEmpresa cadastrarFuncionariosEmpresa(FuncionariosEmpresa funcionario) {
        validarIdEmpresa(funcionario.getId_empresa());
        validarIdFuncionario(funcionario.getId_usuario());
        validarCargo(funcionario.getCargo());
        return dao.cadastrarFuncionariosEmpresa(funcionario);
    }

    public Optional<FuncionariosEmpresa> buscarFuncionario(Long idEmpresa, Long idFuncionario) {
        validarIdEmpresa(idEmpresa);
        validarIdFuncionario(idFuncionario);
        return dao.buscarFuncionario(idEmpresa, idFuncionario);
    }

    public Optional<FuncionariosEmpresa> atualizarCargoFuncionario(FuncionariosEmpresa funcionarioAtualizado) {
        validarIdEmpresa(funcionarioAtualizado.getId_empresa());
        validarIdFuncionario(funcionarioAtualizado.getId_usuario());
        validarCargo(funcionarioAtualizado.getCargo());
        return dao.atualizarCargoFuncionario(funcionarioAtualizado);
    }

    public Boolean excluirFuncionario(Long idEmpresa, Long  idFuncionario) {
        validarIdEmpresa(idEmpresa);
        validarIdFuncionario(idFuncionario);
        return dao.excluirFuncionario(idEmpresa, idFuncionario);
    }


    /// Validações
    public void validarIdEmpresa(Long id) {
        if (id > 0) {
            final String sql = "SELECT id_empresa FROM Funcionarios_Empresa WHERE id_empresa = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Funcionários da empresa com ID" + id + " não forma encontrados");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar os funcionários da empresa pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }

    public void validarIdFuncionario(Long id) {
        if (id > 0) {
            final String sql = "SELECT id_usuario FROM Funcionarios_Empresa WHERE id_usuario = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Funcionario com o " + id + " não foi cadastrado na empresa");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar o funcionario pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }

    public void validarCargo(String cargo) {
        if (cargo == null) {
            throw new RuntimeException("Cargo é obrigatório");
        }

        if (cargo.trim().length() < 3 || cargo.trim().length() > 50) {
            throw new RuntimeException("Tamanho do 'cargo' inválido");
        }
    }
}
