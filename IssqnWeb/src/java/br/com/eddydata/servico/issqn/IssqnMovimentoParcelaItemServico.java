/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnMovimentoParcelaItem;
import br.com.eddydata.repositorio.issqn.IssqnMovimentoParcelaItemRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
import br.com.eddydata.suporte.Util;
import java.util.Date;
import java.util.List;
import javassist.bytecode.Opcode;
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
public class IssqnMovimentoParcelaItemServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnMovimentoParcelaItemRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnMovimentoParcelaItemRepositorio(em);
    }

    /**
     * metodo de validacao do contribuinte
     *
     * @param issqn
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarMovimento(IssqnMovimentoParcelaItem movimento) throws BusinessViolation, Exception {
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
    public IssqnMovimentoParcelaItem salvarMovimento(IssqnMovimentoParcelaItem i) throws BusinessViolation, Exception {
        try {
            verificarMovimento(i);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.salvarIssqnMovimentoParcelaItem(i);
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
        IssqnMovimentoParcelaItem m = repositorio.obterIssqnMovimentoParcelaItemPorId(issqnId);
        if (m == null) {
            throw new Exception("Contriuinte não encontrado para exclusão");
        }

        try {
            repositorio.removerIssqnMovimentoParcelaItem(issqnId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public IssqnMovimentoParcelaItem buscarPagamentoPorLote(Integer idLote) throws BusinessViolation {
        if (idLote == null || idLote <= 0) {
            throw new BusinessViolation("Informe o lote para busca");
        }
        return repositorio.obterIssqnMovimentoParcelaItemPorId(idLote);
    }
    
    public IssqnMovimentoParcelaItem buscarPagamentoPorId(Integer idMovimentoItem) throws BusinessViolation {
        if (idMovimentoItem == null || idMovimentoItem <= 0) {
            throw new BusinessViolation("Informe o movimento para busca");
        }
        return repositorio.obterIssqnMovimentoParcelaItemPorId(idMovimentoItem);
    }
    
    public List<IssqnMovimentoParcelaItem> buscarPagamentoPorDataPagamento(Date dataInicial, Date DataFinal) throws BusinessViolation {
        if (dataInicial == null || DataFinal == null || Util.isDate(dataInicial) || Util.isDate(DataFinal)) {
            throw new BusinessViolation("Informe a data de pagamento para busca");
        }
        return repositorio.obterIssqnMovimentoParcelaPorDataPagamento(dataInicial, DataFinal);
    }
    
    public List<IssqnMovimentoParcelaItem> buscarPagamentoPorDataBaixa(Date dataInicial, Date dataFinal) throws BusinessViolation {
        if (dataInicial == null || Util.isDate(dataInicial) || dataFinal == null || Util.isDate(dataFinal)) {
            throw new BusinessViolation("Informe a data de baixa para busca");
        }
        return repositorio.obterIssqnMovimentoParcelaPorDataBaixa(dataInicial, dataFinal);
    }

    /**
     * relatório de pagamentos geral
     *
     * @param orgaoId
     * @param filtro
     * @throws Exception
     */
    public void imprimirRelacaoPagamentos(String orgaoId, String filtro, String pesquisa) throws Exception {
        repositorio.imprimirRelacaoPagamentos(orgaoId, filtro, pesquisa);
    }

}
