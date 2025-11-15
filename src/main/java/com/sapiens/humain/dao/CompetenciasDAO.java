package com.sapiens.humain.dao;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.model.Competencias;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CompetenciasDAO {

    // GET (Listar competencias)
    public List<Competencias> listarCompetencias() {
        final String sql = "SELECT * FROM competencias";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.execute();
            List<Competencias> competencias = new ArrayList<>();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    competencias.add(new Competencias(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)
                    ));
                }
            }

            return competencias;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar competencias: " + e.getMessage());
        }
    }

    // GET (Buscar por ID)
    public Optional<Competencias> buscarCompetencia(Long id) {
        final String sql = "SELECT * FROM competencias WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Competencias competencia =  new Competencias(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)
                    );
                    return Optional.of(competencia);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar competencia: " + e.getMessage());
        }
    }

    // POST (Cadastrar competência)
    public Competencias adicionarCompetencia(Competencias competencias) {
        final String sql = "INSERT INTO competencias (nome, categoria) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, competencias.getNome());
            ps.setString(2, competencias.getCategoria());
            ps.executeUpdate();

            Competencias novaCompetencia = new Competencias();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    novaCompetencia.setId(rs.getLong(1));
                    novaCompetencia.setNome(rs.getString(2));
                    novaCompetencia.setCategoria(rs.getString(3));
                }
            }

            return novaCompetencia;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar competencia: " + e.getMessage());
        }
    }

    // PUT (Atualizar competencia)
    public Optional<Competencias> atualizarCompetencia(Long id, Competencias dados) {
        final String sql = "UPDATE competencias SET nome = ?, categoria = ?, descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dados.getNome());
            ps.setString(2, dados.getCategoria());
            ps.setString(3, dados.getDescricao());
            ps.setLong(4, id);
            int linhas =  ps.executeUpdate();
            if (linhas == 0) return Optional.empty();
            return buscarCompetencia(id);

        } catch (SQLException e) {
            throw new  RuntimeException("Erro ao atualizar competencia: " + e.getMessage());
        }
    }

    // DELETE (Excluir competência)
    public Boolean excluirCompetencia(Long id) {
        final String sql = "DELETE FROM competencias WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir competencia: " + e.getMessage());
        }

    }
}
