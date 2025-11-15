package com.sapiens.humain.dao;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.model.Matricula;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;

@Repository
public class MatriculaDAO {

    // POST (Matricular user em uma trilha)
    public Matricula matricularTrilha(Long idTrilha, Long idUser) {
        final String sql = "INSERT INTO matricula (id_usuario, id_trilha) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUser);
            ps.setLong(2, idTrilha);
            ps.executeUpdate();

            Matricula novaMatricula = new Matricula();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    novaMatricula.setId(rs.getLong(1));
                    novaMatricula.setId_usuario(rs.getLong(2));
                    novaMatricula.setId_trilha(rs.getLong(3));
                    novaMatricula.setData_inscricao(Date.valueOf(LocalDate.now()));
                }
            }

            return  novaMatricula;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao realizar matricula: " + e.getMessage());
        }
    }

    // DELETE (Completar uma trilha)
    public Boolean completarTrilha(Long idTrilha, Long idUser) {
        final String sql = "DELETE FROM matricula WHERE id_usuario = ? AND id_trilha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUser);
            ps.setLong(2, idTrilha);

            final String sql2 = "UPDATE usuario SET pontos = pontos + 100 WHERE id = ?";

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setLong(1, idUser);
            ps2.executeUpdate();

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao completar trilha: " + e.getMessage());
        }
    }
}
