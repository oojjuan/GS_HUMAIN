package com.sapiens.humain.service;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.dao.CompetenciasDAO;
import com.sapiens.humain.model.Competencias;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CompetenciasService {
    private final CompetenciasDAO dao;
    public CompetenciasService(CompetenciasDAO dao) {
        this.dao = dao;
    }

    public List<Competencias> getCompetencias() {
        return dao.listarCompetencias();
    }

    public Optional<Competencias> buscarCompetenciaPeloId(Long id) {
        validarId(id);
        return dao.buscarCompetencia(id);
    }

    public Competencias adicionarCompetencia(Competencias competencia) {
        validarCompetencia(competencia);
        return dao.adicionarCompetencia(competencia);
    }

    public Optional<Competencias> atualizarCompetencia(Long id, Competencias dados) {
        validarId(id);
        validarCompetencia(dados);
        return  dao.atualizarCompetencia(id, dados);
    }

    public Boolean excluirCompetencia(Long id) {
        validarId(id);
        return dao.excluirCompetencia(id);
    }

    /// Validações
    public void validarId(Long id) {
        if (id > 0) {
            final String sql = "SELECT id FROM competencias WHERE id = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Competência com ID" + id + " não foi encontrada");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar o competencia pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }

    public void validarCompetencia(Competencias competencia) {
        // Nome
        if (competencia.getNome() == null || competencia.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (competencia.getNome().length() < 3 || competencia.getNome().length() > 50) {
            throw new RuntimeException("Tamanho do 'nome' inválido");
        }

        // Categoria
        if (competencia.getCategoria() == null || competencia.getCategoria().isBlank()) {
            throw new RuntimeException("Categoria é obrigatória");
        }

        if (competencia.getCategoria().length() < 3 || competencia.getCategoria().length() > 30) {
            throw new RuntimeException("Tamanho da 'categoria' inválido");
        }
    }
}
