/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnTaxa;
import br.com.eddydata.repositorio.issqn.IssqnTaxaRepositorio;
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
 * @author Eddydata
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IssqnTaxaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnTaxaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnTaxaRepositorio(em);
    }

    private void verificarTaxa(IssqnTaxa taxa) throws BusinessViolation, Exception {
        if (taxa == null) {
            throw new Exception("Taxa não foi passada como parâmetro");
        }

        if (taxa.getNome() == null || taxa.getNome().trim().equals("")) {
            throw new BusinessViolation("Nome da taxa não informado!");
        }
        if (taxa.getTpValor() == null || taxa.getTpValor().trim().equals("")) {
            throw new BusinessViolation("Tipo do Valor da taxa não informado!");
        }
    }

    /**
     * método para incluir ou salvar taxas
     *
     * @param t
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnTaxa salvarTaxa(IssqnTaxa t) throws BusinessViolation, Exception {
        if (t == null) {
            throw new Exception("Informe a taxa a ser salvo");
        }

        try {
            verificarTaxa(t);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarTaxa(t);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover taxas e taxas de atividades
     *
     * @param taxaId
     * @throws Exception
     */
    public void removerTaxa(int taxaId) throws Exception {
        IssqnTaxa t = repositorio.obterTaxaPorId(taxaId);
        if (t == null) {
            throw new Exception("Taxa não encontrada para exclusão");
        }

        try {
            repositorio.removerTaxa(taxaId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar taxa pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnTaxa obterTaxaPorId(int id) throws BusinessViolation, Exception {
        IssqnTaxa t;

        try {
            t = repositorio.obterTaxaPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Taxa não encontrada!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar as taxas
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnTaxa> obterTaxas(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterTaxas(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para retornar os itens da taxa
     *
     * @param taxaId
     * @return
     * @throws Exception
     */
    public List<IssqnTaxa> obterItens(Integer taxaId) throws Exception {
        try {
            return repositorio.obterItens(taxaId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public void imprimirTaxas(String orgaoId, String ordem) throws Exception {
        repositorio.imprimirTaxas(orgaoId, ordem);
    }
}
