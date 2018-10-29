/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2015, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.NotasVersao;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

public class NotasVersaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public NotasVersaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public NotasVersao getNota(int id) {
        return getEntidade(NotasVersao.class, id);
    }

    public NotasVersao setNota(NotasVersao c) {
        return setEntidade(NotasVersao.class, c);
    }

    public NotasVersao adicionarNota(NotasVersao c) {
        adicionarEntidade(NotasVersao.class, c);
        return c;
    }

    public void removerNota(NotasVersao c) {
        removerEntidade(c);
    }

    public NotasVersao getNotaPorId(int id) {
        return getEntityManager().find(NotasVersao.class, id);
    }

    public List<NotasVersao> getNotas() {
        return getListaPura(NotasVersao.class, "select o from NotasVersao o order by o.data desc");
    }

    public List<NotasVersao> getNotaPorSistema(String nome) {
        return getListaPura(NotasVersao.class, "select o from NotasVersao o where o.sistema = ?1 order by o.data desc, o.id", nome);
    }

    public List<NotasVersao> getNotaPorDescricao(String descricao) {
        return getListaPura(NotasVersao.class, "select o from NotasVersao o where UPPER(o.descricao) like ?1 order by o.data desc, o.id", "%" + descricao.trim().toUpperCase() + "%");
    }

    public String getUltimaVersao(String sistema) {
        return getEntidadePura(String.class, "select max(v.versao) from NotasVersao v where v.sistema = ?1", sistema.toUpperCase());
    }

}
