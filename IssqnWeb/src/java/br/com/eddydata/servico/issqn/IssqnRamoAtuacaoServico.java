/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnRamoAtuacao;
import br.com.eddydata.repositorio.issqn.IssqnRamoAtuacaoRepositorio;
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
public class IssqnRamoAtuacaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnRamoAtuacaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnRamoAtuacaoRepositorio(em);
    }

    /**
     * metódo de validacao do ramo de atuacao
     *
     * @param ramo
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarRamoAtuacao(IssqnRamoAtuacao ramo) throws BusinessViolation, Exception {
        if (ramo == null) {
            throw new Exception("Ramo de atuação não foi passado como parâmetro");
        }

        if (ramo.getNome() == null || ramo.getNome().trim().equals("")) {
            throw new BusinessViolation("Nome do ramo de atuação não informado!");
        }
    }

    /**
     * método para incluir ou salvar ramo de atuacao
     *
     * @param a
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnRamoAtuacao salvarRamoAtuacao(IssqnRamoAtuacao a) throws BusinessViolation, Exception {
        try {
            verificarRamoAtuacao(a);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarRamoAtuacao(a);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover ramo de atuação
     *
     * @param ramoAtuacaoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerRamoAtuacao(int ramoAtuacaoId) throws BusinessViolation, Exception {
        IssqnRamoAtuacao t = repositorio.obterRamoAtuacaoPorId(ramoAtuacaoId);
        if (t == null) {
            throw new Exception("Ramo de atuação não encontrado para exclusão");
        }

        try {
            repositorio.removerRamoAtuacao(ramoAtuacaoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo ramo de atuação pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnRamoAtuacao obterRamoAtuacaoPorId(int id) throws BusinessViolation, Exception {
        IssqnRamoAtuacao t;

        try {
            t = repositorio.obterRamoAtuacaoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Ramo de atuação não encontrado!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar os ramos de atuacao
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnRamoAtuacao> obterRamosAtuacao(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterRamosAtuacao(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
