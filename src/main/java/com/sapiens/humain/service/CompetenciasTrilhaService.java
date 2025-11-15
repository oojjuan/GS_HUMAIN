package com.sapiens.humain.service;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.dao.CompetenciasTrilhaDAO;
import com.sapiens.humain.model.CompetenciasTrilha;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CompetenciasTrilhaService {
    private final CompetenciasTrilhaDAO dao;
    public CompetenciasTrilhaService(CompetenciasTrilhaDAO dao) {
        this.dao = dao;
    }

    public List<CompetenciasTrilha> listarCompetenciasTrilhas(Long idTrilha) {
        validarIdTrilha(idTrilha);
        return dao.listarCompetenciasTrilha(idTrilha);
    }

    public CompetenciasTrilha adicionarCompetenciaNaTrilha(Long idTrilha, Long idCompetencia) {
        validarIdTrilha(idTrilha);
        validarIdCompetencia(idCompetencia);
        return dao.adicionarCompetenciaNaTrilha(idTrilha, idCompetencia);
    }

    public Optional<CompetenciasTrilha> buscarCompetenciaPorTrilha(Long idCompetencia) {
        validarIdCompetencia(idCompetencia);
        return dao.buscarCompetenciasPorTrilha(idCompetencia);
    }

    public Boolean excluirCompetenciaPorTrilha(Long idTrilha, Long idCompetencia) {
        validarIdTrilha(idTrilha);
        validarIdCompetencia(idCompetencia);
        return dao.excluirCompetenciaTrilha(idTrilha, idCompetencia);
    }

    /// Validações
    public void validarIdTrilha(Long id) {
        if (id > 0) {
            final String sql = "SELECT id_trilha FROM Competencia_Trilha WHERE id_trilha = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Trilha com id " + id + " não foi encontrado");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar a trilha pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }

    public void validarIdCompetencia(Long id) {
        if (id > 0) {
            final String sql = "SELECT id_competencia FROM Competencia_Trilha WHERE id_competencia = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Competência com o id " + id + " não foi cadastrado na trilha");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao competência na trilha pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }
}
