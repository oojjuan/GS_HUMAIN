package com.sapiens.humain.dao;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UsuarioDAO {

    // POST (Cadastrar usuário)
    public Usuario cadastrarUser(Usuario usuario) {
        final String sql = "INSERT INTO usuario (nome, email, area_atuacao) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getArea_atuacao());
            ps.executeUpdate();

            Usuario novoUser = new Usuario();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    novoUser.setId(rs.getLong(1));
                    novoUser.setNome(rs.getString(2));
                    novoUser.setEmail(rs.getString(3));
                    novoUser.setArea_atuacao(rs.getString(4));
                    novoUser.setPontos(0);
                }
            }

            return novoUser;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar user: " + e.getMessage());
        }
    }

    // GET (Buscar user pelo ID)
    public Optional<Usuario> buscarUser(Long idUser) {
        final String sql = "SELECT * FROM usuario WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUser);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5)
                    );
                    return Optional.of(usuario);
                }
            }

            return Optional.empty();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar user: " + e.getMessage());
        }
    }

    // PUT (Atualizar infos do user)
    public Optional<Usuario> atualizarUser(Long idUser, Usuario usuarioAtualizado) {
        final String sql = "UPDATE usuario SET nome = ?, email = ?, area_atuacao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuarioAtualizado.getNome());
            ps.setString(2, usuarioAtualizado.getEmail());
            ps.setString(3, usuarioAtualizado.getArea_atuacao());
            ps.setLong(4, idUser);

            int linhas = ps.executeUpdate();
            if (linhas == 0) return Optional.empty();
            return buscarUser(idUser);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar user: " + e.getMessage());
        }
    }

    // DELETE (Excluir conta)
    public Boolean excluirUser(Long idUser) {
        final String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idUser);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir user: " + e.getMessage());
        }
    }
}
