/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;


import br.com.eddydata.entidade.issqn.IssqnMovimentoParcela;
import br.com.eddydata.repositorio.issqn.IssqnMovimentoParcelaRepositorio;
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
 * @author David
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IssqnMovimentoParcelaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnMovimentoParcelaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnMovimentoParcelaRepositorio(em);
    }

    /**
     * metodo de validacao do contribuinte
     *
     * @param issqn
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarMovimento(IssqnMovimentoParcela movimento) throws BusinessViolation, Exception {
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
    public IssqnMovimentoParcela salvarMovimento(IssqnMovimentoParcela i) throws BusinessViolation, Exception {
        try {
            verificarMovimento(i);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.salvarIssqnMovimentoParcela(i);
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
    public void removerIssqnMovimentoParcela(int idMovimentoParcela) throws BusinessViolation, Exception {
        IssqnMovimentoParcela m = repositorio.obterIssqnMovimentoParcelaPorId(idMovimentoParcela);
        if (m == null) {
            throw new Exception("Contriuinte não encontrado para exclusão");
        }

        try {
            repositorio.removerIssqnMovimentoParcela(idMovimentoParcela);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public List<IssqnMovimentoParcela> buscarMovimentoPorIdMovimento(int id){
        return repositorio.buscarMovimentoParcelaPorIdMovimento(id);
    }
    
    public IssqnMovimentoParcela buscarMovimentoPorNossoNumero(int id){
        return repositorio.buscarMovimentoParcelaPorNossoNumero(id);
    }


}
