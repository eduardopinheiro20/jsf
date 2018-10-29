/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnConselhoRegional;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David'
 */
public class IssqnConselhoRegionalRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnConselhoRegionalRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnConselhoRegional salvarConselhoRegional(IssqnConselhoRegional c) {
        if (c.getId() == null) {
            return adicionarEntidade(IssqnConselhoRegional.class, c);
        } else {
            return setEntidade(IssqnConselhoRegional.class, c);
        }
    }

    public synchronized void removerConselhoRegional(int conselhoRegionalId) {
        IssqnConselhoRegional t = getEntidade(IssqnConselhoRegional.class, conselhoRegionalId);
        removerEntidade(t);
    }

    public IssqnConselhoRegional obterConselhoRegionalPorId(int id) {
        return getEntidade(IssqnConselhoRegional.class, id);
    }

    public List<IssqnConselhoRegional> obterConselhosRegionais(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where c.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', c.descricao)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnConselhoRegional.class,
                "select c from IssqnConselhoRegional c "
                + where
                + "\norder by c.id", limite
        );

    }

}
