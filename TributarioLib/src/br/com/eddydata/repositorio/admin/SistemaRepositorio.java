/**
 * @Copyright (C) 2015
 * @author Eddydata Informática ltda.
 * @version 1.0
 *
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.Sistema;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

public class SistemaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public SistemaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public Sistema obterSistema(String id) {
        return getEntidade(Sistema.class, id);
    }

    public Sistema salvarSistema(Sistema model) {
        return setEntidade(Sistema.class, model);
    }

    public void removerSistema(Sistema model) {
        removerEntidade(model);
    }

    public List<Sistema> obterTodosSistemas() {
        return getListaPura(Sistema.class, "select a from Sistema a order by a.nome");
    }

    public List<Sistema> obterSistemaPorNome(String nome) {
        return getListaPura(Sistema.class, "select a from Sistema a "
                + "\njoin a.orgao o"
                + "where a.nome like ?1", nome + "%");
    }

    public Sistema obterSistemaPorNomeExato(String nome) {
        return getEntidadePura(Sistema.class, "select a from Sistema a where a.nome = ?1", nome);
    }

}
