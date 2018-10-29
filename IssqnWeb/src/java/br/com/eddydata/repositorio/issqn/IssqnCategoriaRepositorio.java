/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnCategoria;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class IssqnCategoriaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnCategoriaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnCategoria salvarCategoria(IssqnCategoria c) {
        if (c.getId() == null) {
            return adicionarEntidade(IssqnCategoria.class, c);
        } else {
            return setEntidade(IssqnCategoria.class, c);
        }
    }

    public synchronized void removerCategoria(int categoriaId) {
        IssqnCategoria t = getEntidade(IssqnCategoria.class, categoriaId);
        removerEntidade(t);
    }

    public IssqnCategoria obterCategoriaPorId(int id) {
        return getEntidade(IssqnCategoria.class, id);
    }

    public List<IssqnCategoria> obterCategorias(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where c.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', c.nome)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnCategoria.class,
                "select c from IssqnCategoria c "
                + where
                + "\norder by c.id", limite
        );

    }

}
