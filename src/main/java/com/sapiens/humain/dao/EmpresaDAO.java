package com.sapiens.humain.dao;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.model.Empresa;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmpresaDAO {

    // GET (Listar empresas)
    public List<Empresa> buscarEmpresas() {
        final String sql = "SELECT * FROM empresas";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.execute();
            List<Empresa> empresas = new ArrayList<>();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    empresas.add(new Empresa(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3)
                    ));
                }
            }

            return empresas;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empresas: " + e.getMessage());
        }
    }

    // POST (Cadastrar empresa)
    public Empresa cadastrarEmpresa(Empresa empresa) {
        final String sql = "INSERT INTO empresa (nome, descricao) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empresa.getNome());
            ps.setString(2, empresa.getDescricao());
            ps.execute();

            Empresa novaEmpresa = new Empresa();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    novaEmpresa.setId(rs.getLong(1));
                    novaEmpresa.setNome(rs.getString(2));
                    novaEmpresa.setDescricao(rs.getString(3));
                }
            }

            return novaEmpresa;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar empresa: " + e.getMessage());
        }
    }

    // GET (Buscar empresa pelo ID)
    public Optional<Empresa> buscarEmpresa(Long id) {
        final String sql = "SELECT * FROM empresas WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Empresa novaEmpresa = new Empresa(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3)
                    );
                    return Optional.of(novaEmpresa);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empresa: " + e.getMessage());
        }
    }

    // PUT (Atualizar dados da empresa)
    public Optional<Empresa> atualizarEmpresa(Long id, Empresa dados) {
        final String sql = "UPDATE empresa SET nome = ?, descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dados.getNome());
            ps.setString(2, dados.getDescricao());
            ps.setLong(3, id);
            int linhas = ps.executeUpdate();
            if (linhas == 0) return Optional.empty();
            return buscarEmpresa(id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar empresa: " + e.getMessage());
        }
    }

    // DELETE (Excluir empresa)
    public Boolean excluirEmpresa(Long id) {
        final String sql = "DELETE FROM empresa WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir empresa: " + e.getMessage());
        }
    }

}
