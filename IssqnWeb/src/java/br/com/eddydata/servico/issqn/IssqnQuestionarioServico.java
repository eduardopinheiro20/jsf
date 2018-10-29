/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnQuestionario;
import br.com.eddydata.entidade.issqn.IssqnRamoAtuacao;
import br.com.eddydata.repositorio.issqn.IssqnQuestionarioRepositorio;
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
public class IssqnQuestionarioServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnQuestionarioRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnQuestionarioRepositorio(em);
    }

    /**
     * metódo de validacao do questionario
     *
     * @param q
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarQuestionario(IssqnQuestionario q) throws BusinessViolation, Exception {
        if (q == null) {
            throw new Exception("Questionário não foi passado como parâmetro");
        }

        if (q.getDescricao() == null || q.getDescricao().isEmpty()) {
            throw new BusinessViolation("Pergunta do questionário de atuação não informado!");
        }
    }

    /**
     * método para incluir ou salvar questionario
     *
     * @param q
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnQuestionario salvarQuestionario(IssqnQuestionario q) throws BusinessViolation, Exception {
        try {
            verificarQuestionario(q);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarQuestionario(q);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover questionario
     *
     * @param questionarioId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerQuestionario(int questionarioId) throws BusinessViolation, Exception {
        IssqnQuestionario q = repositorio.obterQuestionarioPorId(questionarioId);
        if (q == null) {
            throw new Exception("Questionário de atuação não encontrado para exclusão");
        }

        try {
            repositorio.removerQuestionario(questionarioId);
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
    public IssqnQuestionario obterQuestionarioPorId(int id) throws BusinessViolation, Exception {
        IssqnQuestionario q;

        try {
            q = repositorio.obterQuestionarioPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (q == null) {
            throw new BusinessViolation("Questionário de atuação não encontrado!");
        } else {
            return q;
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
    public List<IssqnQuestionario> obterQuestionario(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterQuestionario(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public void imprimirQuestionario(String orgaoId) throws Exception {
        repositorio.imprimirQuestionario(orgaoId);
    }
}
