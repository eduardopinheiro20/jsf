/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnAutoInfracao;
import br.com.eddydata.repositorio.issqn.IssqnAutoInfracaoRepositorio;
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
 * @author Thiago Martos
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IssqnAutoInfracaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnAutoInfracaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnAutoInfracaoRepositorio(em);
    }

    /**
     * método para incluir ou salvar alvará de diversões
     *
     * @param ad
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnAutoInfracao salvarAutoInfracao(IssqnAutoInfracao ad) throws BusinessViolation, Exception {
        try {
            return repositorio.salvarAutoInfracao(ad);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover alvará de diversões
     *
     * @param alvaraId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerAutoInfracao(int alvaraId) throws BusinessViolation, Exception {
        IssqnAutoInfracao t = repositorio.obterAutoInfracaoPorId(alvaraId);
        if (t == null) {
            throw new Exception("Alvará de diversões não encontrado para exclusão");
        }

        try {
            repositorio.removerAutoInfracao(alvaraId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar alvará de diversões pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnAutoInfracao obterAutoInfracaoPorId(int id) throws BusinessViolation, Exception {
        IssqnAutoInfracao t;

        try {
            t = repositorio.obterAutoInfracaoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Alvará de diversões não encontrado!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar os alvarás de diversões
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnAutoInfracao> obterAutoInfracoes(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterAutoInfracoes(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para imprimir o alvará de diversão
     *
     * @param alvaraId
     * @param orgaoId
     * @throws Exception
     */
    public void imprimirAutoInfracao(Integer alvaraId, String orgaoId) throws Exception {
        if (alvaraId == null) {
            throw new Exception("Alvará não passado como parâmetro para impressão do alvará");
        }
        if (orgaoId == null) {
            throw new Exception("Orgão não passado como parâmetro para impressão do alvará");
        }

        repositorio.imprimirAutoInfracao(alvaraId, orgaoId);
    }

}
