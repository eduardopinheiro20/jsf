/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnHistorico;
import br.com.eddydata.repositorio.issqn.IssqnHistoricoRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
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
public class IssqnHistoricoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnHistoricoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnHistoricoRepositorio(em);
    }

    /**
     * método para incluir ou salvar historico
     *
     * @param h
     * @return
     * @throws Exception
     */
    public IssqnHistorico salvarHistorico(IssqnHistorico h) throws Exception {
        if (h == null) {
            throw new Exception("Informe o historico a ser salvo");
        }

        try {
            return repositorio.salvarHistorico(h);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover historico
     *
     * @param historicoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerHistorico(int historicoId) throws BusinessViolation, Exception {
        IssqnHistorico h = repositorio.obterHistoricoPorId(historicoId);
        if (h == null) {
            throw new Exception("Histórico não encontrado para exclusão");
        }

        try {
            repositorio.removerHistorico(historicoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar historico pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnHistorico obterHistoricoPorId(int id) throws BusinessViolation, Exception {
        IssqnHistorico h;

        try {
            h = repositorio.obterHistoricoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (h == null) {
            throw new BusinessViolation("Historico não encontrado!");
        } else {
            return h;
        }
    }

    /**
     * metodo que retorna os historicos por contribuinte
     *
     * @param idContribuinte
     * @return
     * @throws Exception
     */
    public List<IssqnHistorico> obterHistoricoPorContribuinte(Integer idContribuinte) throws Exception {
        try {
            return repositorio.obterHistoricoPorContribuinte(idContribuinte);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * metodo que retorna os historicos por contribuinte
     *
     * @param idContribuinte
     * @return
     * @throws Exception
     */
    public void imprimirHistoricoContribuinte(String orgaoId, Issqn issqn, String emailUsuario) throws Exception {
        try {
            repositorio.imprimirHistoricoContribuinte(orgaoId, issqn, emailUsuario);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
