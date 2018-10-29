/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnAtividade;
import br.com.eddydata.entidade.issqn.IssqnCnae;
import br.com.eddydata.repositorio.issqn.IssqnAtividadeRepositorio;
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
public class IssqnAtividadeServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnAtividadeRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnAtividadeRepositorio(em);
    }

    /**
     * método para incluir ou salvar atividade
     *
     * @param a
     * @return
     * @throws Exception
     */
    public IssqnCnae salvarAtividade(IssqnCnae a) throws Exception {
        if (a == null) {
            throw new Exception("Informe a atividade a ser salva");
        }

        try {
            return repositorio.salvarAtividade(a);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover atividade
     *
     * @param atividadeId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerAtividade(int atividadeId) throws BusinessViolation, Exception {
        IssqnCnae t = repositorio.obterAtividadePorId(atividadeId);
        if (t == null) {
            throw new Exception("Atividade não encontrada para exclusão");
        }

        try {
            repositorio.removerAtividade(atividadeId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo atividade pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnCnae obterAtividadePorId(int id) throws BusinessViolation, Exception {
        IssqnCnae t;

        try {
            t = repositorio.obterAtividadePorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Atividade não encontrada!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar as atividades
     *
     * @param filtro
     * @param tpAtividade
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnCnae> obterAtividades(String filtro, Integer tpAtividade, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 10000 : limite);

        try {
            return repositorio.obterAtividades(filtro, tpAtividade, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * método para retornar as atividades
     *
     * @param nome
     * @return
     * @throws Exception
     */
    public List<IssqnCnae> obterCnaePorNome(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 10000 : limite);

        try {
            return repositorio.obterCnaePorNome(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
