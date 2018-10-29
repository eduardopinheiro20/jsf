/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnHistRegTribContribuinte;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author d.morais
 */
public class IssqnHistRegTribContribuinteRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnHistRegTribContribuinteRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnHistRegTribContribuinte salvarHistorico(IssqnHistRegTribContribuinte h) {
        if (h.getId() == null) {
            return adicionarEntidade(IssqnHistRegTribContribuinte.class, h);
        } else {
            return setEntidade(IssqnHistRegTribContribuinte.class, h);
        }
    }

    public synchronized void removerHistorico(int historicoId) {
        IssqnHistRegTribContribuinte h = getEntidade(IssqnHistRegTribContribuinte.class, historicoId);
        removerEntidade(h);
    }

    public IssqnHistRegTribContribuinte obterHistoricoPorId(int id) {
        return getEntidade(IssqnHistRegTribContribuinte.class, id);
    }

    public List<IssqnHistRegTribContribuinte> obterHistoricoPorContribuinte(Integer idContribuinte) {
        return getListaPura(IssqnHistRegTribContribuinte.class,
                "select h from IssqnHistRegTribContribuinte h"
                + "\njoin h.iss i"
                + "\nwhere i.id = ?1"
                + "\norder by h.id", idContribuinte);

    }

}
