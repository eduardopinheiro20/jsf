/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnTermoIntimacao;
import br.com.eddydata.repositorio.issqn.TermoIntimacaoRepositorio;
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
 * @author Thiago Martos
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TermoIntimacaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private TermoIntimacaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new TermoIntimacaoRepositorio(em);
    }

    /**
     * método para incluir ou salvar registro
     *
     * @param ad
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnTermoIntimacao salvar(IssqnTermoIntimacao ad) throws BusinessViolation, Exception {
        try {
            return repositorio.salvar(ad);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover registro
     *
     * @param id
     * @throws BusinessViolation
     * @throws Exception
     */
    public void remover(int id) throws BusinessViolation, Exception {
        IssqnTermoIntimacao t = repositorio.obterPorId(id);
        if (t == null) {
            throw new Exception("Registro não encontrado para exclusão");
        }

        try {
            repositorio.remover(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar registro pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnTermoIntimacao obterPorId(int id) throws BusinessViolation, Exception {
        IssqnTermoIntimacao t;

        try {
            t = repositorio.obterPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Registro não encontrado!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar a listagem de registros
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnTermoIntimacao> listar(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.listar(filtro, limite);
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
    public void imprimirTermoIntimacao(Integer alvaraId, String orgaoId) throws Exception {
        if (alvaraId == null) {
            throw new Exception("Alvará não passado como parâmetro para impressão do alvará");
        }
        if (orgaoId == null) {
            throw new Exception("Orgão não passado como parâmetro para impressão do alvará");
        }

        repositorio.imprimirTermoIntimacao(alvaraId, orgaoId);
    }
    

    public void imprimirTermoIntimacao(Date dtInicial,Date dtFinal,String orgaoId) throws Exception {        
        if (dtInicial == null) {
            throw new Exception("Data Final não informado como parâmetro para impressão do alvará");
        }
        
        if (dtFinal == null) {
            throw new Exception("Data Inícial não informado como parâmetro para impressão do alvará");
        }
        
        if (orgaoId == null) {
            throw new Exception("Orgão não passado como parâmetro para impressão do alvará");
        }

        repositorio.imprimirTermoIntimacao(dtInicial, dtFinal, orgaoId);
    }

}
