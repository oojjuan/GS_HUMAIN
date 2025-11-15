package com.sapiens.humain.service;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.dao.MatriculaDAO;
import com.sapiens.humain.model.Matricula;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class MatriculaService {
    private final MatriculaDAO dao;
    public MatriculaService(MatriculaDAO dao) {
        this.dao = dao;
    }

    public Matricula matricularTrilha(Long idTrilha, Long idUser) {
        validarIdTrilha(idTrilha);
        validarIdUser(idUser);
        return dao.matricularTrilha(idTrilha, idUser);
    }

    public Boolean completarTrilha(Long idTrilha, Long idUser) {
        validarIdUser(idUser);
        validarIdTrilha(idTrilha);
        return dao.completarTrilha(idTrilha, idUser);
    }

    /// Validações
    public void validarIdTrilha(Long id) {
        if (id > 0) {
            final String sql = "SELECT id_trilha FROM matricula WHERE id_trilha = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Trilha com id " + id + " não foi cadastrado pelo usuario");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar a trilha pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }

    public void validarIdUser(Long id) {
        if (id > 0) {
            final String sql = "SELECT id_usuario FROM matricula WHERE id_usuario = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Usuario com id " + id + " não se cadastrou na trilha");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar o user pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }
}
