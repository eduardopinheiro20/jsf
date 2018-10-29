/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.UsuarioSistema;
import br.com.eddydata.entidade.admin.Acesso;
import br.com.eddydata.repositorio.admin.UsuarioSistemaRepositorio;
import br.com.eddydata.servico.Servico;
import java.util.ArrayList;
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
public class UsuarioSistemaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private UsuarioSistemaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new UsuarioSistemaRepositorio(em);
    }

    public UsuarioSistemaServico() {

    }

    public UsuarioSistema getUsuarioSistemaPorId(Integer id) {
        return repositorio.getUsuarioSistemaPorId(id);
    }

    public UsuarioSistema setUsuarioSistema(UsuarioSistema p) {
        return repositorio.setUsuarioSistema(p);
    }

    public void removerUsuarioSistema(UsuarioSistema p) {
        repositorio.removerUsuarioSistema(p);
    }

    public UsuarioSistema adicionarUsuarioSistema(UsuarioSistema p) {
        return repositorio.adicionarUsuarioSistema(p);
    }

    public List<UsuarioSistema> getUsuarioPerfis() {
        return repositorio.getUsuarioPerfis();
    }

    public List<UsuarioSistema> getUsuarioSistemaPorNome(String nomeUsuario, String sistemaId, boolean somenteAtivos) {
        return repositorio.getUsuarioSistemaPorNome(nomeUsuario, sistemaId, somenteAtivos);
    }

    public List<UsuarioSistema> getUsuarioSistemaPorUsuario(Integer usuarioId) {
        return repositorio.getUsuarioSistemaPorUsuario(usuarioId);
    }

    public UsuarioSistema getUsuarioSistemaPorUsuario(Integer usuarioId, String sistema, boolean somenteAtivos) {
        return repositorio.getUsuarioSistemaPorUsuario(usuarioId, sistema, somenteAtivos);
    }

    public List<Acesso> getPermiteAcesso(Integer usuarioId, String sistemaId) {

        try {
            List<Acesso> resources = new ArrayList<>();

            UsuarioSistema usuario = getUsuarioSistemaPorUsuario(usuarioId, sistemaId, false);

            if (usuario == null) {
                return null;
            }

            resources = usuario.getUsuarioPerfil().getAcessos();
//            for (Acesso access : usuario.getPerfil().getAcessos()) {
//                if (access.getAtivo()) {
//                    resources.add(access.getPagina().getResource());
//                }
//            }
            return resources;
        } catch (Exception e) {
//            Log.error(e, "Erro ao preencher recursos permitidos para o usuario " + userId);
            return null;
        }
    }
}
