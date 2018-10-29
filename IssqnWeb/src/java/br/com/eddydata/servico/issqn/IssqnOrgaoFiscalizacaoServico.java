/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnOrgaoFiscalizacao;
import br.com.eddydata.repositorio.issqn.IssqnOrgaoFiscalizacaoRepositorio;
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
public class IssqnOrgaoFiscalizacaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnOrgaoFiscalizacaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnOrgaoFiscalizacaoRepositorio(em);
    }

    /**
     * metódo de validacao do questionario
     *
     * @param q
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarOrgaoFiscalizacao(IssqnOrgaoFiscalizacao orgao) throws BusinessViolation, Exception {
        if (orgao == null) {
            throw new Exception("Orgão de Fiscalização não foi passado como parâmetro");
        }

        if (orgao.getNome().toUpperCase() == null || orgao.getNome().toUpperCase().isEmpty()) {
            throw new BusinessViolation("Nome do Orgão de Fiscalização não informado!");
        }
    }

    /**
     * método para incluir ou salvar orgao
     *
     * @param orgao
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnOrgaoFiscalizacao salvarOrgaoFiscalizacao (IssqnOrgaoFiscalizacao orgao) throws BusinessViolation, Exception {
        try {
            verificarOrgaoFiscalizacao(orgao);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarOrgao(orgao);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover orgao
     *
     * @param orgaoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerOrgaoFiscalizacao (int orgaoId) throws BusinessViolation, Exception {
        IssqnOrgaoFiscalizacao o = repositorio.obterOrgaoFiscalizacaoPorId(orgaoId);
        if (o == null) {
            throw new Exception("Orgão de Fiscalização não encontrado para exclusão");
        }

        try {
            repositorio.removerOrgaoFiscalizacao(orgaoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo questionario pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnOrgaoFiscalizacao obterOrgaoFiscalizacaoPorId(int id) throws BusinessViolation, Exception {
        IssqnOrgaoFiscalizacao o;

        try {
            o = repositorio.obterOrgaoFiscalizacaoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (o == null) {
            throw new BusinessViolation("Orgão de Fiscalização não encontrado!");
        } else {
            return o;
        }
    }

    /**
     * método para retornar os questionario
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnOrgaoFiscalizacao> obterFiscalizacaos(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterOrgaoFiscalizacao(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
}
