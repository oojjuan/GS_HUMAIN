package com.sapiens.humain.dao;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.model.CompetenciasTrilha;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CompetenciasTrilhaDAO {

    // GET (Listar competências da trilha)
    public List<CompetenciasTrilha> listarCompetenciasTrilha(Long idTrilha) {
        final String sql = "SELECT * FROM Competencias_Trilha WHERE id_trilha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idTrilha);

            List<CompetenciasTrilha> competenciasTrilhas = new ArrayList<>();

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    competenciasTrilhas.add(new CompetenciasTrilha(
                            rs.getLong(1),
                            rs.getLong(2)
                    ));
                }
            }

            return competenciasTrilhas;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar as competências da trilha: " + e.getMessage());
        }
    }

    // POST (Adicionar competência na trilha)
    public CompetenciasTrilha adicionarCompetenciaNaTrilha(Long idTrilha, Long idCompetencia) {
        final String sql = "INSERT INTO Competencias_Trilha VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idTrilha);
            ps.setLong(2, idCompetencia);
            ps.executeUpdate();

            CompetenciasTrilha novaCompetenciaNaTrilha = new CompetenciasTrilha();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    novaCompetenciaNaTrilha.setId_trilha(rs.getLong(1));
                    novaCompetenciaNaTrilha.setId_competencia(rs.getLong(2));
                }
            }

            return novaCompetenciaNaTrilha;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar competência na trilha: " + e.getMessage());
        }
    }

    // GET (Buscar trilhas com as competências especificadas)
    public Optional<CompetenciasTrilha> buscarCompetenciasPorTrilha(Long idCompetencia) {
        final String sql = "SELECT ct.id_trilha, t.nome, ct.id_competencia, c.nome" +
                "FROM Competencias_Trilha ct " +
                "INNER JOIN Trilha t ON ct.id_trilha = t.id " +
                "INNER JOIN Competencia c ON ct.id_competencia = c.id" +
                "WHERE c.id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idCompetencia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CompetenciasTrilha novaCompetenciaNaTrilha = new CompetenciasTrilha(
                            rs.getLong("id_trilha"),
                            rs.getLong("id_competencia")
                    );
                    return Optional.of(novaCompetenciaNaTrilha);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar as trilhas com as competências especificadas: " + e.getMessage());
        }
    }

    // DELETE (Deleta competência da trilha)
    public Boolean excluirCompetenciaTrilha(Long idTrilha, Long idCompetencia) {
        final String sql = "DELETE FROM Competencias_Trilha WHERE id_trilha = ? and id_competencia = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idTrilha);
            ps.setLong(2, idCompetencia);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir competência da trilha: "  + e.getMessage());
        }
    }
}
