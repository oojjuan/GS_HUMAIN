package com.sapiens.humain.dao;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.model.FuncionariosEmpresa;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FuncionariosEmpresaDAO {

    // GET (Exibir funcionários da empresa)
    public List<FuncionariosEmpresa> listarFuncionariosEmpresa(Long idEmpresa) {
        final String sql = "SELECT * FROM Funcionarios_Empresa WHERE id_empresa = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEmpresa);
            List<FuncionariosEmpresa> funcionarios = new ArrayList<>();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    funcionarios.add(new FuncionariosEmpresa(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getString(3)
                    ));
                }
            }

            return funcionarios;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionarios da empresa: " + e.getMessage());
        }
    }

    // POST (Adicionar funcionário na empresa)
    public FuncionariosEmpresa cadastrarFuncionariosEmpresa(FuncionariosEmpresa funcionario) {
        final String sql = "INSERT INTO Funcionarios_Empresa VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, funcionario.getId_empresa());
            ps.setLong(2, funcionario.getId_usuario());
            ps.setString(3, funcionario.getCargo());

            FuncionariosEmpresa novoFuncionario = new FuncionariosEmpresa();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    novoFuncionario.setId_empresa(rs.getLong(1));
                    novoFuncionario.setId_usuario(rs.getLong(2));
                    novoFuncionario.setCargo(rs.getString(3));
                }
            }

            return novoFuncionario;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar novo funcionário na empresa: " + e.getMessage());
        }
    }

    // GET (Buscar funcionário pelo seu id)
    public Optional<FuncionariosEmpresa> buscarFuncionario(Long idEmpresa, Long idFuncionario) {
        final String sql = "SELECT * FROM Funcionarios_Empresa WHERE id_empresa = ? AND id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEmpresa);
            ps.setLong(2, idFuncionario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    FuncionariosEmpresa novoFuncionario = new FuncionariosEmpresa(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getString(3)
                    );
                    return Optional.of(novoFuncionario);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pelo funcionário: " +  e.getMessage());
        }
    }

    // PUT (Atualizar cargo do funcionário)
    public Optional<FuncionariosEmpresa> atualizarCargoFuncionario(FuncionariosEmpresa funcionarioAtualizado) {
        final String sql = "UPDATE Funcionarios_Empresa SET cargo = ? WHERE id_empresa = ? AND id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, funcionarioAtualizado.getCargo());
            ps.setLong(2, funcionarioAtualizado.getId_empresa());
            ps.setLong(3, funcionarioAtualizado.getId_usuario());
            int linhas = ps.executeUpdate();
            if (linhas == 0) return Optional.empty();
            return buscarFuncionario(funcionarioAtualizado.getId_empresa() , funcionarioAtualizado.getId_usuario()  );

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cargo do funcionário: " + e.getMessage());
        }
    }

    // DELETE (Excluir funcionário da empresa)
    public Boolean excluirFuncionario(Long idEmpresa, Long idFuncionario) {
        final String sql = "DELETE FROM Funcionarios_Empresa WHERE id_empresa = ? AND id_usuario = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idEmpresa);
            ps.setLong(2, idFuncionario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário: " + e.getMessage());
        }
    }

}
