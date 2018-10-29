/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnUnidadeCalculo;
import br.com.eddydata.repositorio.issqn.IssqnUnidadeCalculoRepositorio;
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
 * @author d.morais
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IssqnUnidadeCalculoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnUnidadeCalculoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnUnidadeCalculoRepositorio(em);
    }

    /**
     * método para incluir ou salvar unidade Calculo
     *
     * @param u
     * @return
     * @throws Exception
     */
    public IssqnUnidadeCalculo salvarUnidadeCalculo(IssqnUnidadeCalculo u) throws Exception {
        if (u == null) {
            throw new Exception("Informe a Unidade de Cálculo a ser salvo");
        }

        try {
            return repositorio.salvarUnidadeCalculo(u);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover unidade cálculo
     *
     * @param unidadeId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerUnidadeCalculo(int unidadeId) throws BusinessViolation, Exception {
        IssqnUnidadeCalculo u = repositorio.obterUnidadeCalculoPorId(unidadeId);
        if (u == null) {
            throw new Exception("Unidade de Cálculo não encontrado para exclusão");
        }

        try {
            repositorio.removerUnidadeCalculo(unidadeId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar unidade cálculo pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnUnidadeCalculo obterUnidadeCalculoPorId(int id) throws BusinessViolation, Exception {
        IssqnUnidadeCalculo u;

        try {
            u = repositorio.obterUnidadeCalculoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (u == null) {
            throw new BusinessViolation("Unidade Cálculo não encontrado!");
        } else {
            return u;
        }
    }

    /**
     * metodo que retorna unidade calculo por contribuinte
     *
     * @param idContribuinte
     * @return
     * @throws Exception
     */
    public List<IssqnUnidadeCalculo> obterUnidadeCalculoPorContribuinte(Integer idContribuinte) throws Exception {
        try {
            return repositorio.obterUnidadeCalculoPorContribuinte(idContribuinte);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
