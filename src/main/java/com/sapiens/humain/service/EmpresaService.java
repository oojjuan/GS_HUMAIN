package com.sapiens.humain.service;

import com.sapiens.humain.config.ConnectionFactory;
import com.sapiens.humain.dao.EmpresaDAO;
import com.sapiens.humain.model.Empresa;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    private final EmpresaDAO dao;
    public EmpresaService(EmpresaDAO dao) {
        this.dao = dao;
    }

    public List<Empresa> listarEmpresas() {
        return dao.buscarEmpresas();
    }

    public Optional<Empresa> buscarEmpresa(Long id) {
        validarId(id);
        return dao.buscarEmpresa(id);
    }

    public Empresa cadastrarEmpresa(Empresa empresa) {
        validarEmpresa(empresa);
        return dao.cadastrarEmpresa(empresa);
    }

    public Optional<Empresa> atualizarEmpresa(Long id, Empresa dados) {
        validarId(id);
        validarEmpresa(dados);
        return dao.atualizarEmpresa(id, dados);
    }

    public Boolean excluirEmpresa(Long id) {
        validarId(id);
        return dao.excluirEmpresa(id);
    }

    /// Validações
    public void validarId(Long id) {
        if (id > 0) {
            final String sql = "SELECT id FROM empresa WHERE id = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    throw new RuntimeException("Empresa com ID" + id + " não foi encontrada");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar a empresa pelo id: " + e.getMessage());
            }


        } else {
            throw new RuntimeException("ID invalido");
        }
    }

    public void validarEmpresa(Empresa empresa) {
        if (empresa.getNome() == null || empresa.getNome().isBlank()) {
            throw new RuntimeException("Nome invalido");
        }

        if (empresa.getNome().length() < 3 || empresa.getNome().length() > 100) {
            throw new RuntimeException("Tamanho do 'nome' inválido");
        }
    }
}

