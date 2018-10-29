/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.UsuarioPerfil;
import br.com.eddydata.entidade.admin.Acesso;
import br.com.eddydata.entidade.referencia.Pagina;
import br.com.eddydata.repositorio.Repositorio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioPerfilRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public UsuarioPerfilRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public UsuarioPerfil getUsuarioPerfilPorId(int id) {
        return getEntidade(UsuarioPerfil.class, id);
    }

    public UsuarioPerfil setUsuarioPerfil(UsuarioPerfil p) {
        return setEntidade(UsuarioPerfil.class, p);
    }

    public UsuarioPerfil adicionarUsuarioPerfil(UsuarioPerfil p) {
        adicionarEntidade(UsuarioPerfil.class, p);
        return p;
    }

    public void removerUsuarioPerfil(UsuarioPerfil p) {
        removerEntidade(p);
    }

    public List<UsuarioPerfil> getUsuarioPerfis() {
        return getListaPura(UsuarioPerfil.class, "select p from UsuarioPerfil p");
    }

    public List<UsuarioPerfil> getUsuarioPorPerfil(String nomePerfil) {
        return getListaPura(UsuarioPerfil.class, "select ue from UsuarioPerfil ue where ue.nome like ?1 order by ue.nome", nomePerfil);
    }

    public List<UsuarioPerfil> getUsuarioPerfilPorUsuario(Integer usuarioId) {
        return getListaPura(UsuarioPerfil.class, "select up from UsuarioPerfil up"
                + "\njoin up.usuario u"
                + "\nwhere u.idUsuario = ?1", usuarioId);
    }

    public UsuarioPerfil getUsuarioPerfilPorUsuario(Integer usuarioId, String sistema) {
        return getEntidadePura(UsuarioPerfil.class, "select up from UsuarioPerfil up"
                + "\njoin up.usuario u"
                + "\nwhere u.idUsuario = ?1 and up.sistema.nome = ?2", usuarioId, sistema);
    }

    public List<UsuarioPerfil> getUsuarioPerfilPorOrgao(String orgaoId, String sistema) {
        return getListaPura(UsuarioPerfil.class, "select up from UsuarioPerfil up"
                + "\njoin up.orgao o"
                + "\nwhere o.idOrgao = ?1 and up.sistema.nome = ?2", orgaoId, sistema);
    }

    public List<UsuarioPerfil> getUsuarioPerfilByPerfil(String nomePerfil) {
        return getListaPura(UsuarioPerfil.class, "select ue from UsuarioPerfil ue where ue.nome like ?1 order by ue.nome", nomePerfil);
    }

    public List<UsuarioPerfil> getUsuarioPerfilBySistema(String sistemaId) {
        return getListaPura(UsuarioPerfil.class, "select ue from UsuarioPerfil ue "
                + "\njoin ue.sistema s"
                + "\nwhere s.idSistema = ?1 order by ue.nome", sistemaId);
    }

    public List<UsuarioPerfil> getUsuarioPerfilByNome(String nome, String sistemaId) {
        return getListaPura(UsuarioPerfil.class, "select ue from UsuarioPerfil ue "
                + "\njoin ue.sistema s"
                + "\nwhere ue.nome like ?1 and s.idSistema = ?2 order by ue.nome", nome, sistemaId);
    }

    public List<UsuarioPerfil> getUsuarioPerfilByNomeUsuario(String nome, String sistemaId) {
        return getListaPura(UsuarioPerfil.class, "select up from UsuarioPerfil up"
                + "\njoin up.usuario u"
                + "\njoin up.sistema s"
                + "\nwhere upper(u.nome) like ?1 and s.idSistema = ?2", nome.toUpperCase() + "%", sistemaId);
    }

    public UsuarioPerfil getUsuarioPerfilByUsuario(Integer usuarioId, String sistemaId) {
        return getEntidadePura(UsuarioPerfil.class, "select up from UsuarioPerfil up"
                + "\njoin up.usuario u"
                + "\njoin up.sistema s"
                + "\nwhere u.idUsuario = ?1 and a.idSistema = ?2", usuarioId, sistemaId);
    }

    public List<Acesso> getAcessos(Integer perfilId, String sistema) {
        try {
            UsuarioPerfil perfil = getUsuarioPerfilPorId(perfilId == null ? 0 : perfilId);
            if (perfil == null || perfil.getIdPerfil() == null || perfil.getAcessos().isEmpty()) {
                perfil = new UsuarioPerfil();
                ArrayList<Acesso> list = new ArrayList<>();
                Acesso access;
                for (Pagina obj : Pagina.values()) {
                    //pega apenas paginas do sistema 
                    if (obj.getSistema().equals(sistema)) {
                        access = new Acesso();
                        access.setPagina(obj);
                        access.setUsuarioPerfil(perfil);
                        list.add(access);
                    }
                }
                perfil.setAcessos(list);
                return new ArrayList<>(list);
            } else {
                Acesso access;
                List<Acesso> list;
                if (perfil.getAcessos().isEmpty()) {
                    list = new ArrayList<>();
                } else {
                    list = perfil.getAcessos();
                }
                if (perfilId != null && perfilId != 0) {
                    for (Pagina pag : Pagina.values()) {
                        if (pag.getSistema().equals(sistema)) {
                            List listAcesso = getListaPura(Acesso.class, "select a from Acesso a "
                                    + "where a.pagina = ?1 and a.usuarioPerfil.idPerfil = ?2 ", pag, perfil.getIdPerfil());
                            if (listAcesso.isEmpty()) {
                                access = new Acesso();
                                access.setAtivo(true);
                                access.setUsuarioPerfil(perfil);
                                access.setPagina(pag);
                                adicionarEntidade(Acesso.class, access);
                                list.add(access);
                            }
                        }
                    }
                }
                perfil.setAcessos(list);
                return perfil.getAcessos();
            }
        } catch (Exception e) {
            return null;
        }
    }

}
