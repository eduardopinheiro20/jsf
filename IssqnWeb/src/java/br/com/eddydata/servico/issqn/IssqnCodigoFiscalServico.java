/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnCodigoFiscal;
import br.com.eddydata.repositorio.issqn.IssqnCodigoFiscalRepositorio;
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
public class IssqnCodigoFiscalServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnCodigoFiscalRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnCodigoFiscalRepositorio(em);
    }

    /**
     * metódo de validacao do codigo fiscal
     *
     * @param codigo
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarCodigoFiscal(IssqnCodigoFiscal codigo) throws BusinessViolation, Exception {
        if (codigo == null) {
            throw new Exception("Código Fiscal não foi passado como parâmetro");
        }
        if (codigo.getCodigoFiscal() == null || codigo.getCodigoFiscal().trim().equals("")) {
            throw new BusinessViolation("Código fiscal não informado!");
        }
    }

    /**
     * método para incluir ou salvar codigo fiscal
     *
     * @param f
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnCodigoFiscal salvarCodigoFiscal(IssqnCodigoFiscal f) throws BusinessViolation, Exception {
        try {
            verificarCodigoFiscal(f);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarCodigoFiscal(f);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover codigo fiscal
     *
     * @param codigoFiscalId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerCodigoFiscal(int codigoFiscalId) throws BusinessViolation, Exception {
        IssqnCodigoFiscal t = repositorio.obterCodigoFiscalPorId(codigoFiscalId);
        if (t == null) {
            throw new Exception("Código Fiscal não encontrado para exclusão");
        }

        try {
            repositorio.removerCodigoFiscal(codigoFiscalId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo fiscal pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnCodigoFiscal obterCodigoFiscalPorId(int id) throws BusinessViolation, Exception {
        IssqnCodigoFiscal t;

        try {
            t = repositorio.obterCodigoFiscalPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Código Fiscal não encontrado!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar os codigos fiscais
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnCodigoFiscal> obterCodigosFiscais(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterCodigosFiscais(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
