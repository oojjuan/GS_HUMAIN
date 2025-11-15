package com.sapiens.humain.service;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.dao.TrilhaDAO;
import com.sapiens.humain.model.Trilha;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class TrilhaService {
    private final TrilhaDAO dao;
    public TrilhaService(TrilhaDAO dao) {
        this.dao = dao;
    }

    public List<Trilha> listarTrilhas() {
        return dao.listarTrilhas();
    }

    public Optional<Trilha> buscarTrilha(Long id) {
        validarId(id);
        return dao.buscarTrilha(id);
    }

    public Trilha adicionarTrilha(Trilha trilha) {
        validarTriha(trilha);
        return dao.adicionarTrilha(trilha);
    }

    public Optional<Trilha> atualizarTrilha(Long id, Trilha trilha) {
        validarId(id);
        validarTriha(trilha);
        return dao.atualizarTrilha(id, trilha);
    }

    public Boolean deletarTrilha(Long id) {
        validarId(id);
        return dao.excluirTrilha(id);
    }

    /// Validações
    public void validarId(Long id) {
        if (id > 0) {
            final String sql = "SELECT id FROM trilha WHERE id = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Trilha com ID" + id + " não foi encontrada");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar a trilha pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }

    public void validarTriha(Trilha trilha) {
        /// Nome
        if (trilha.getNome() == null || trilha.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (trilha.getNome().trim().length() < 5 || trilha.getNome().trim().length() > 100) {
            throw new RuntimeException("Tamanho do 'nome' inválido");
        }
        /// Nivel
        if (trilha.getNivel() == null || trilha.getNivel().isBlank()) {
            throw new RuntimeException("Nivel é obrigatório");
        }

        if (trilha.getNivel().trim().length() < 3 || trilha.getNivel().trim().length() > 20) {
            throw new RuntimeException("Tamanho do 'nível' inválido");
        }
        /// Carga horária
        if (trilha.getCarga_horaria() < 3 || trilha.getCarga_horaria() > 200) {
            throw new RuntimeException("Carga horária inválida");
        }
        /// Foco da trilha
        if (trilha.getFoco_trilha() == null || trilha.getFoco_trilha().isBlank()) {
            throw new RuntimeException("Foco da trilha é obrigatório");
        }

        if (trilha.getFoco_trilha().length() < 3 || trilha.getFoco_trilha().length() > 50) {
            throw new RuntimeException("Tamanho do 'foco da trilha' inválido");
        }

    }
}
