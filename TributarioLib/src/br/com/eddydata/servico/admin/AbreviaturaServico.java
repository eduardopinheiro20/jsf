/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geo.Abreviatura;
import br.com.eddydata.repositorio.admin.AbreviaturaRepositorio;
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
public class AbreviaturaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private AbreviaturaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new AbreviaturaRepositorio(em);
    }

    /**
     * método para incluir ou salvar abreviaturas
     *
     * @param a
     * @return
     * @throws Exception
     */
    public Abreviatura salvarAbreviatura(Abreviatura a) throws Exception {
        if (a == null) {
            throw new Exception("Informe a abreviatura a ser salva");
        }

        try {
            return repositorio.salvarAbreviatura(a);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover abreviatura
     *
     * @param abreviaturaId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerAbreviatura(int abreviaturaId) throws BusinessViolation, Exception {
        Abreviatura a = repositorio.obterAbreviaturaPorId(abreviaturaId);
        if (a == null) {
            throw new Exception("Abreviatura não encontrada para exclusão");
        }

        try {
            repositorio.removerAbreviatura(abreviaturaId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar abreviatura pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public Abreviatura obterAbreviaturaPorId(int id) throws BusinessViolation, Exception {
        Abreviatura b;

        try {
            b = repositorio.obterAbreviaturaPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (b == null) {
            throw new BusinessViolation("Abreviatura não encontrada!");
        } else {
            return b;
        }
    }

    /**
     * método para retornar as abreviaturas
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<Abreviatura> obterAbreviaturas(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterAbreviaturas(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
