/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnUnidadeCalculo;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author d.morais
 */
public class IssqnUnidadeCalculoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnUnidadeCalculoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnUnidadeCalculo salvarUnidadeCalculo(IssqnUnidadeCalculo h) {
        if (h.getId() == null) {
            return adicionarEntidade(IssqnUnidadeCalculo.class, h);
        } else {
            return setEntidade(IssqnUnidadeCalculo.class, h);
        }
    }

    public synchronized void removerUnidadeCalculo(int historicoId) {
        IssqnUnidadeCalculo h = getEntidade(IssqnUnidadeCalculo.class, historicoId);
        removerEntidade(h);
    }

    public IssqnUnidadeCalculo obterUnidadeCalculoPorId(int id) {
        return getEntidade(IssqnUnidadeCalculo.class, id);
    }

    public List<IssqnUnidadeCalculo> obterUnidadeCalculoPorContribuinte(Integer idContribuinte) {
        return getListaPura(IssqnUnidadeCalculo.class,
                "select u from IssqnUnidadeCalculo u"
                + "\njoin u.iss i"
                + "\nwhere i.id = ?1"
                + "\norder by u.id", idContribuinte);

    }

}
