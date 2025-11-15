package com.sapiens.humain.dao;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.model.Trilha;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrilhaDAO {

    // GET (Listar trilhas)
    public List<Trilha> listarTrilhas() {
        final String sql = "SELECT * FROM Trilha";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.execute();
            List<Trilha> trilhas = new ArrayList<>();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    trilhas.add(new Trilha(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getString(6)
                    ));
                }
            }

            return trilhas;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar as trilhas: " + e.getMessage());
        }
    }

    // POST (criar trilha nova)
    public Trilha adicionarTrilha(Trilha trilha) {
        final String sql = "INSERT INTO Trilha (nome, descricao, nivel, carga_horaria, foco_trilha) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trilha.getNome());
            ps.setString(2, trilha.getDescricao());
            ps.setString(3, trilha.getNivel());
            ps.setInt(4, trilha.getCarga_horaria());
            ps.setString(5, trilha.getFoco_trilha());
            ps.executeUpdate();

            Trilha novaTrilha = new Trilha();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    novaTrilha.setId(rs.getLong(1));
                    novaTrilha.setNome(rs.getString(2));
                    novaTrilha.setDescricao(rs.getString(3));
                    novaTrilha.setNivel(rs.getString(4));
                    novaTrilha.setCarga_horaria(rs.getInt(5));
                    novaTrilha.setFoco_trilha(rs.getString(6));
                }
            }

            return novaTrilha;

        } catch (SQLException e) {
            throw new  RuntimeException("Erro ao adicionar trilha: " + e.getMessage());
        }
    }

    // GET (Buscar trilha pelo id)
    public Optional<Trilha> buscarTrilha(Long id) {
        final String sql = "SELECT * FROM Trilha WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Trilha trilha = new Trilha(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getString(6)
                    );
                    return  Optional.of(trilha);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha: " + e.getMessage());
        }
    }

    // PUT (Atualizar trilha)
    public Optional<Trilha> atualizarTrilha(Long id, Trilha dados) {
        final String sql = "UPDATE trilha SET nome = ?, descricao = ?, nivel = ?, carga_horaria = ?, foco_trilha = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dados.getNome());
            ps.setString(2, dados.getDescricao());
            ps.setString(3, dados.getNivel());
            ps.setInt(4, dados.getCarga_horaria());
            ps.setString(5, dados.getFoco_trilha());
            ps.setLong(6, id);
            int linhas = ps.executeUpdate();
            if (linhas == 0) return Optional.empty();
            return buscarTrilha(id);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar trilha: " + e.getMessage());
        }
    }

    // DELETE (Excluir trilha)
    public Boolean excluirTrilha(Long id) {
        final String sql = "DELETE FROM Trilha WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar trilha: " + e.getMessage());
        }
    }
}
