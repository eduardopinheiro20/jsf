/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.UsuarioSistema;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioSistemaRepositorio extends Repositorio {

    private static final long serialVioersionUID = 1L;

    public UsuarioSistemaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public UsuarioSistema getUsuarioSistemaPorId(Integer id) {
        return getEntidade(UsuarioSistema.class, id);
    }

    public UsuarioSistema setUsuarioSistema(UsuarioSistema p) {
        return setEntidade(UsuarioSistema.class, p);
    }

    public UsuarioSistema adicionarUsuarioSistema(UsuarioSistema p) {
        adicionarEntidade(UsuarioSistema.class, p);
        return p;
    }

    public void removerUsuarioSistema(UsuarioSistema p) {
        removerEntidade(p);
    }

    public List<UsuarioSistema> getUsuarioPerfis() {
        return getListaPura(UsuarioSistema.class, "select p from UsuarioSistema p");
    }

    public List<UsuarioSistema> getUsuarioSistemaPorNome(String nomeUsuario, String sistemaId, boolean somenteAtivos) {
        return getListaPura(UsuarioSistema.class, "select us "
                + "\nfrom UsuarioSistema us "
                + "\njoin us.usuario u"
                + "\njoin us.usuarioPerfil up"
                + "\nwhere u.nome like ?1 and up.sistema.idSistema = ?2"
                + (somenteAtivos ? " and u.ativo = 1" : "")
                + "\norder by u.nome", nomeUsuario, sistemaId);
    }

    public List<UsuarioSistema> getUsuarioSistemaPorUsuario(Integer usuarioId) {
        return getListaPura(UsuarioSistema.class, "select up from UsuarioSistema up"
                + "\njoin up.usuario u"
                + "\njoin up.usuarioPerfil p"
                + "\nwhere u.id = ?1", usuarioId);
    }

    public UsuarioSistema getUsuarioSistemaPorUsuario(Integer usuarioId, String sistema, boolean somenteAtivos) {
        return getEntidadePura(UsuarioSistema.class, "select up from UsuarioSistema up"
                + "\njoin up.usuario u"
                + "\njoin up.usuarioPerfil p"
                + "\nwhere u.id = ?1 and p.sistema.idSistema = ?2"
                + (somenteAtivos ? " and u.ativo = true " : ""), usuarioId, sistema);
    }

}
