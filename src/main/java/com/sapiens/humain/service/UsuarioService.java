package com.sapiens.humain.service;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.dao.UsuarioDAO;
import com.sapiens.humain.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioDAO dao;
    public UsuarioService(UsuarioDAO dao) {
        this.dao = dao;
    }

    public Usuario cadastrarUser(Usuario usuario) {
        validarUser(usuario);
        return dao.cadastrarUser(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id){
        validarId(id);
        return dao.buscarUser(id);
    }

    public Optional<Usuario> atualizarUser(Long id, Usuario usuario){
        validarId(id);
        validarUser(usuario);
        return  dao.atualizarUser(id, usuario);
    }

    public Boolean deletarUser(Long id){
        validarId(id);
        return dao.excluirUser(id);
    }



    /// Validações
    public void validarId(Long id) {
        if (id > 0) {
            final String sql = "SELECT id FROM usuario WHERE id = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Usuario com id " + id + " não foi encontrado");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao usuario pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }

    public void validarUser(Usuario usuario) {
        // Nome
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (usuario.getNome().length() < 3 ||  usuario.getNome().length() > 100) {
            throw new RuntimeException("Tamanho do 'nome' inválido");
        }

        // Email
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RuntimeException("Email é obrigatório");
        }

        if (usuario.getEmail().length() < 10 ||  usuario.getEmail().length() > 150) {
            throw new RuntimeException("Tamanho do 'email' inválido");
        }

        if (!usuario.getEmail().contains("@")) {
            throw new RuntimeException("Email inválido");
        }

        final String sql = "SELECT email FROM usuario WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    throw new RuntimeException("Email já existe");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao procurar email do user: " + e.getMessage());
        }

        // Área de atuação
        if (usuario.getArea_atuacao() == null || usuario.getArea_atuacao().isBlank()) {
            throw new RuntimeException("Área de atuação é obrigatória");
        }

        if (usuario.getArea_atuacao().length() < 3 ||  usuario.getArea_atuacao().length() > 100) {
            throw new RuntimeException("Tamanho da 'área de atuação' inválido");
        }

        // Pontos
        if (usuario.getPontos() != 0) {
            throw new RuntimeException("Usuários novos devem ser cadastrados com 0 pontos");
        }

    }

}
