/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.UsuarioPerfil;
import br.com.eddydata.entidade.admin.Acesso;
import br.com.eddydata.repositorio.admin.UsuarioPerfilRepositorio;
import br.com.eddydata.servico.Servico;
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
public class UsuarioPerfilServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    
    private UsuarioPerfilRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new UsuarioPerfilRepositorio(em);
    }

    public UsuarioPerfilServico() {

    }

    public UsuarioPerfil getUsuarioPerfilPorId(int id) {
        return repositorio.getUsuarioPerfilPorId(id);
    }

    public UsuarioPerfil setUsuarioPerfil(UsuarioPerfil p) {
        return repositorio.setUsuarioPerfil(p);
    }

    public void removerUsuarioPerfil(UsuarioPerfil p) {
        repositorio.removerUsuarioPerfil(p);
    }

    public UsuarioPerfil adicionarUsuarioPerfil(UsuarioPerfil p) {
        return repositorio.adicionarUsuarioPerfil(p);
    }

    public List<UsuarioPerfil> getUsuarioPerfis() {
        return repositorio.getUsuarioPerfis();
    }

    public List<UsuarioPerfil> getUsuarioPorPerfil(String nomePerfil) {
        return repositorio.getUsuarioPorPerfil(nomePerfil);
    }

    public List<UsuarioPerfil> getUsuarioPerfilPorUsuario(Integer usuarioId) {
        return repositorio.getUsuarioPerfilPorUsuario(usuarioId);
    }

    public UsuarioPerfil getUsuarioPerfilPorUsuario(Integer usuarioId, String sistema) {
        return repositorio.getUsuarioPerfilPorUsuario(usuarioId, sistema);
    }

    public List<UsuarioPerfil> getUsuarioByPerfil(String nomePerfil) {
        return repositorio.getUsuarioPerfilByPerfil(nomePerfil);
    }

    public List<UsuarioPerfil> getUsuarioPerfilByNomeUsuario(String nome, String sistemaId) {
        return repositorio.getUsuarioPerfilByNomeUsuario(nome, sistemaId);
    }

    public UsuarioPerfil getUsuarioPerfilByUsuario(Integer usuarioId, String sistemaId) {
        return repositorio.getUsuarioPerfilByUsuario(usuarioId, sistemaId);
    }

    public List<UsuarioPerfil> getUsuarioBySistema(String sistemaId) {
        return repositorio.getUsuarioPerfilBySistema(sistemaId);
    }
    
    public List<UsuarioPerfil> getUsuarioPerfilPorOrgao(String orgaoId, String sistema) {
        return repositorio.getUsuarioPerfilPorOrgao(orgaoId, sistema);
    }
    
    public List<UsuarioPerfil> getUsuarioPerfilByNome(String nome, String sistemaId) {
        return repositorio.getUsuarioPerfilByNome(nome, sistemaId);
    }
    
     public List<Acesso> getAcessos(Integer perfilId, String sistema) {
         return repositorio.getAcessos(perfilId, sistema);
     }
     
      public List<UsuarioPerfil> getUsuarioPerfilBySistema(String sistemaId) {
          return repositorio.getUsuarioPerfilBySistema(sistemaId);
      }

}
