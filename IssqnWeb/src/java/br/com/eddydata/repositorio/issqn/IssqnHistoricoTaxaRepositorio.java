/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnHistoricoTaxa;
import br.com.eddydata.repositorio.Repositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Joylson T
 */
public class IssqnHistoricoTaxaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnHistoricoTaxaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnHistoricoTaxa salvarHistoricoTaxa(IssqnHistoricoTaxa h) {
        if (h.getId() == null) {
            return adicionarEntidade(IssqnHistoricoTaxa.class, h);
        } else {
            return setEntidade(IssqnHistoricoTaxa.class, h);
        }
    }

    public synchronized void removerHistoricoTaxa(int taxaId) {
        IssqnHistoricoTaxa h = getEntidade(IssqnHistoricoTaxa.class, taxaId);
        removerEntidade(h);
    }

    public IssqnHistoricoTaxa obterHistoricoTaxaPorId(int id) {
        return getEntidade(IssqnHistoricoTaxa.class, id);
    }

    public List<IssqnHistoricoTaxa> obterHistoricoTaxas(String filtro, Integer limite) {
        String where = !filtro.equals("") ? "\nwhere " + filtro : "";

        return getListaPuraLimite(IssqnHistoricoTaxa.class,
                "select h from IssqnHistoricoTaxa h "
                + where
                + "\norder by h.id desc", limite
        );
    }

    public IssqnHistoricoTaxa obterUltimoAdd(String taxa) {
        String where = taxa == null  || taxa.equals("") ? "" : "\nwhere "
                + "h.id = (select max(h2.id) from IssqnHistoricoTaxa h2 where " + taxa + ")";
        
        return getEntidadePura(IssqnHistoricoTaxa.class,
                "select h from IssqnHistoricoTaxa h "
                + where
        );
    }

}
