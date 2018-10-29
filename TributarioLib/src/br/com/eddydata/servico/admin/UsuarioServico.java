/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.Usuario;
import br.com.eddydata.entidade.admin.UsuarioSistema;
import br.com.eddydata.repositorio.admin.UsuarioRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private UsuarioRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new UsuarioRepositorio(em);
    }

    public UsuarioServico() {

    }

    private void verificarUsuario(Usuario usuario) throws BusinessViolation, Exception {
        if (usuario == null) {
            throw new Exception("Usuário não foi passado como parâmetro");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().equals("")) {
            throw new BusinessViolation("Senha não informada!");
        }
    }

    public Usuario getUsuario(int id) {
        return repositorio.getUsuario(id);
    }

    public Usuario setUsuario(Usuario u) throws BusinessViolation, Exception {
        try {
            verificarUsuario(u);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.setUsuario(u);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void removerUsuario(Usuario u) {
        repositorio.removerUsuario(u);
    }

    public Usuario getUsuarioPorId(int id) {
        return repositorio.getUsuarioPorId(id);
    }

    public void setSenha(int idUsuario, String senha) {
        repositorio.setSenha(senha, idUsuario);
    }

    public Usuario adicionarUsuario(Usuario u) throws BusinessViolation, Exception {
        try {
            verificarUsuario(u);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.adicionarUsuario(u);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Usuario registrarUsuario(Usuario u, Integer cidadeId) {
        return repositorio.registrarUsuario(u, cidadeId);
    }

    public Usuario getUsuarioPorLoginSenha(String login, String senha) {
        Usuario u = repositorio.getUsuarioPorLoginSenha(login, senha);

        return u;
    }
    
    public Usuario obterUsuarioPorCPFESenha(String cpf,String senha){
        Usuario u = repositorio.getUsuarioPorCPFESenha(cpf,senha);
        
        return u;
    }
    public Usuario getUsuarioPorEmail(String email) {
        try {
            return repositorio.getUsuarioPorEmail(email);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * busca usuarios
     *
     * @param sistema
     * @param somenteAtivos
     * @return
     */
    public List<Usuario> getUsuarioPorSistema(String sistema, boolean somenteAtivos) {
        return repositorio.getUsuarioPorSistema(sistema, somenteAtivos);
    }

    public List<UsuarioSistema> getUsuarioSistemaPorSistema(String sistema, boolean somenteAtivos) {
        return repositorio.getUsuarioSistemaPorSistema(sistema, somenteAtivos);
    }

    public List<Usuario> getUsuarios(boolean somenteAtivos) {
        return repositorio.getUsuarios(somenteAtivos);
    }

    public List<Usuario> getUsuarioPorNome(String nome, String orgao, String sistema, boolean somenteAtivos) {
        return repositorio.getUsuarioPorNome(nome.toUpperCase(), orgao, sistema, somenteAtivos);
    }

    public List<Usuario> getUsuarioPorNome(String nome, String orgao, boolean somenteAtivos) {
        return repositorio.getUsuarioPorNome(nome.toUpperCase(), orgao, somenteAtivos);
    }
}
