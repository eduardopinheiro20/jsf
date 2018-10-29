/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnMovimentoParcela;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Tales
 */
public class IssqnMovimentoParcelaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnMovimentoParcelaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnMovimentoParcela salvarIssqnMovimentoParcela(IssqnMovimentoParcela i) {
        if (i.getId() == null) {
            return adicionarEntidade(IssqnMovimentoParcela.class, i);
        } else {
            return setEntidade(IssqnMovimentoParcela.class, i);
        }
    }

    public synchronized void removerIssqnMovimentoParcela(int issqnId) {
        IssqnMovimentoParcela t = getEntidade(IssqnMovimentoParcela.class, issqnId);
        removerEntidade(t);
    }

    public IssqnMovimentoParcela obterIssqnMovimentoParcelaPorId(int id) {
        return getEntidade(IssqnMovimentoParcela.class, id);
    }
    
    public List<IssqnMovimentoParcela> buscarMovimentoParcelaPorIdMovimento(int id){
        Query q = createQuery("select m from IssqnMovimentoParcela m "
                + "where m.movimento.id = ?1 "
                + "order by m.idTipoCobranca, m.parcela ");
        q.setParameter(1, id);
        
        return q.getResultList();
    }
    
    public IssqnMovimentoParcela buscarMovimentoParcelaPorNossoNumero(int id){
        Query q = createQuery("select m from IssqnMovimentoParcela m "
                + "where m.nossoNumero = ?1 "
                + "order by m.idTipoCobranca, m.parcela ");
        q.setParameter(1, id);
        IssqnMovimentoParcela t = (IssqnMovimentoParcela)q.getResultList().get(0);
        return t;
    }
}
