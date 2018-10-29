/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnMovimento;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Tales
 */
public class IssqnMovimentoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;
    private boolean parcelado = false;

    private boolean parc_iss = false;

    public IssqnMovimentoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnMovimento salvarIssqnMovimento(IssqnMovimento i) {
        if (i.getId() == null) {
            return adicionarEntidade(IssqnMovimento.class, i);
        } else {
            return setEntidade(IssqnMovimento.class, i);
        }
    }

    public synchronized void removerIssqnMovimento(int issqnId) {
        IssqnMovimento t = getEntidade(IssqnMovimento.class, issqnId);
        removerEntidade(t);
    }

    public IssqnMovimento obterIssqnMovimentoPorId(int id) {
        return getEntidade(IssqnMovimento.class, id);
    }
    
    public List<IssqnMovimento> buscarMovimentoPorIss(int idIss, int exercicio){
        Query q = createQuery("select i from IssqnMovimento i "
                + " where i.iss.id = ?1 and i.idExercicio = ?2 "
                + " and i.canceladoMovimento = 0 "
                + " order by i.id");
        
        q.setParameter(1, idIss);
        q.setParameter(2, exercicio);
        
        return (List<IssqnMovimento>) q.getResultList();        
    }
}
