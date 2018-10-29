/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnHistoricoTaxa;
import br.com.eddydata.repositorio.issqn.IssqnHistoricoTaxaRepositorio;
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
 * @author Joylson T
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IssqnHistoricoTaxaServico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnHistoricoTaxaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnHistoricoTaxaRepositorio(em);
    }

    /**
     * método para incluir ou salvar HistoricoTaxas
     *
     * @param h
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnHistoricoTaxa salvarHistoricoTaxa(IssqnHistoricoTaxa h) throws BusinessViolation, Exception {
        if (h == null) {
            throw new Exception("Informe a Historico Taxa a ser salvo");
        }

        try {
            return repositorio.salvarHistoricoTaxa(h);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover HistoricoTaxas
     *
     * @param HistoricoTaxaId
     * @throws Exception
     */
    public void removerHistoricoTaxa(int HistoricoTaxaId) throws Exception {
        IssqnHistoricoTaxa h = repositorio.obterHistoricoTaxaPorId(HistoricoTaxaId);
        if (h == null) {
            throw new Exception("Historico Taxa não encontrada para exclusão");
        }

        try {
            repositorio.removerHistoricoTaxa(HistoricoTaxaId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar HistoricoTaxa pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnHistoricoTaxa obterHistoricoTaxaPorId(int id) throws BusinessViolation, Exception {
        IssqnHistoricoTaxa h;

        try {
            h = repositorio.obterHistoricoTaxaPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (h == null) {
            throw new BusinessViolation("Historico Taxa não encontrada!");
        } else {
            return h;
        }
    }

    /**
     * método para retornar as HistoricoTaxas
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnHistoricoTaxa> obterHistoricoTaxas(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 0 : limite);

        try {
            return repositorio.obterHistoricoTaxas(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para retornar a ultima taxa adidcionada
     *
     * @param taxa
     * @return
     */
    public IssqnHistoricoTaxa obterUltimoAdd(String taxa) {
        return this.repositorio.obterUltimoAdd(taxa);
    }
}
