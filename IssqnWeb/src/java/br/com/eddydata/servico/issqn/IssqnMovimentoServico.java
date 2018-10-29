/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnMovimento;
import br.com.eddydata.repositorio.issqn.IssqnMovimentoRepositorio;
import br.com.eddydata.repositorio.issqn.IssqnRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tales
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IssqnMovimentoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnMovimentoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnMovimentoRepositorio(em);
    }

    /**
     * metodo de validacao do contribuinte
     *
     * @param issqn
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarMovimento(IssqnMovimento movimento) throws BusinessViolation, Exception {
        if (movimento == null) {
            throw new Exception("Contribuinte não foi passada como parâmetro");
        }
    }

    /**
     * método para incluir ou salvar contribuinte
     *
     * @param i
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnMovimento salvarMovimento(IssqnMovimento i) throws BusinessViolation, Exception {
        try {
            verificarMovimento(i);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.salvarIssqnMovimento(i);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover contribuinte
     *
     * @param issqnId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerIssqnMovimento(int issqnId) throws BusinessViolation, Exception {
        IssqnMovimento m = repositorio.obterIssqnMovimentoPorId(issqnId);
        if (m == null) {
            throw new Exception("Contriuinte não encontrado para exclusão");
        }

        try {
            repositorio.removerIssqnMovimento(issqnId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public IssqnMovimento buscarPagamentoPorLote(Integer idLote) throws BusinessViolation {
        if (idLote == null || idLote <= 0) {
            throw new BusinessViolation("Informe a inscricao do contribuinte para busca");
        }
        return repositorio.obterIssqnMovimentoPorId(idLote);
    }
    
    public List<IssqnMovimento> buscaMovimentoPorIss(int idIss, int exercicio){
        return repositorio.buscarMovimentoPorIss(idIss, exercicio);
    }

}
