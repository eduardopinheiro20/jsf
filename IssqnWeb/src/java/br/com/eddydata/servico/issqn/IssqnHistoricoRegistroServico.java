/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.entidade.issqn.IssqnHistoricoRegistro;
import br.com.eddydata.repositorio.issqn.IssqnHistoricoRegistroRepositorio;
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
public class IssqnHistoricoRegistroServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnHistoricoRegistroRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnHistoricoRegistroRepositorio(em);
    }

    /**
     * método para incluir ou salvar historico de registro
     *
     * @param i
     * @return
     * @throws Exception
     */
    public IssqnHistoricoRegistro salvarHistoricoRegistro(Issqn i) throws Exception {
        if (i == null) {
            throw new Exception("Informe o historico a ser salvo");
        }

        try {
            return repositorio.salvarHistoricoRegistro(i);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para incluir ou salvar historico de registro
     *
     * @param i
     * @return
     * @throws Exception
     */
    public boolean alteracaoRegistro(IssqnHistoricoRegistro i) throws Exception {
        if (i == null) {
            throw new Exception("Informe o historico a ser salvo");
        }

        try {
            return repositorio.alteracaoRegistro(i);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover historico de registro
     *
     * @param historicoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerHistorico(int historicoId) throws BusinessViolation, Exception {
        IssqnHistoricoRegistro h = repositorio.obterHistoricoRegistroPorId(historicoId);
        if (h == null) {
            throw new Exception("Histórico não encontrado para exclusão");
        }

        try {
            repositorio.removerHistoricoRegistro(historicoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * metodo que retorna os historicos
     *
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnHistoricoRegistro> obterHistoricos(Integer limite) throws Exception {
        try {
            limite = (limite == null ? 100 : limite);
            return repositorio.obterHistoricos(limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * metodo que imprime todos os historico da pessoa passada por parametro
     *
     * @param orgaoId
     * @param nomePessoa
     * @param idPessoa
     * @param emailUsuario
     * @throws Exception
     */
    public void imprimirHistoricoRegistro(String orgaoId, String nomePessoa, int idPessoa, String emailUsuario) throws Exception {
        repositorio.imprimirHistoricoRegistro(orgaoId, nomePessoa, idPessoa, emailUsuario);
    }

}
