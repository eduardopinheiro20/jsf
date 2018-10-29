/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnEscritorio;
import br.com.eddydata.repositorio.issqn.IssqnEscritorioRepositorio;
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
public class IssqnEscritorioServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnEscritorioRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnEscritorioRepositorio(em);
    }

    /**
     * metódo de validacao do escritorio
     *
     * @param escritorio
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarEscritorio(IssqnEscritorio escritorio) throws BusinessViolation, Exception {
        if (escritorio == null) {
            throw new Exception("Escritório não foi passado como parâmetro");
        }
        if (escritorio.getNomeFantasia() == null || escritorio.getNomeFantasia().trim().equals("")) {
            throw new BusinessViolation("Nome do escritório não informado!");
        }
        if (escritorio.getResponsavel() == null || escritorio.getResponsavel().trim().equals("")) {
            throw new BusinessViolation("Responsável do escritório não informado!");
        }
    }

    /**
     * método para incluir ou salvar escritorio
     *
     * @param es
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnEscritorio salvarEscritorio(IssqnEscritorio es) throws BusinessViolation, Exception {
        try {
            verificarEscritorio(es);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarEscritorio(es);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover escritorio
     *
     * @param escritorioId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerEscritorio(int escritorioId) throws BusinessViolation, Exception {
        IssqnEscritorio t = repositorio.obterEscritorioPorId(escritorioId);
        if (t == null) {
            throw new Exception("Escritorio não encontrado para exclusão");
        }

        try {
            repositorio.removerEscritorio(escritorioId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo escritorio pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnEscritorio obterEscritorioPorId(int id) throws BusinessViolation, Exception {
        IssqnEscritorio t;

        try {
            t = repositorio.obterEscritorioPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Escritorio não encontrado!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar os escritorios
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnEscritorio> obterEscritorios(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterEscritorios(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
