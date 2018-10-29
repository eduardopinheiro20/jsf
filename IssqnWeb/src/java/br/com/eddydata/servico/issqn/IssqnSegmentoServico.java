/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnSegmento;
import br.com.eddydata.repositorio.issqn.IssqnSegmentoRepositorio;
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
public class IssqnSegmentoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnSegmentoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnSegmentoRepositorio(em);
    }

    /**
     * metódo de validacao do segmento
     *
     * @param segmento
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarSegmento(IssqnSegmento segmento) throws BusinessViolation, Exception {
        if (segmento == null) {
            throw new Exception("Segmento não foi passado como parâmetro");
        }

        if (segmento.getNome() == null || segmento.getNome().trim().equals("")) {
            throw new BusinessViolation("Segmento não informado!");
        }
    }

    /**
     * método para incluir ou salvar segmento
     *
     * @param a
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnSegmento salvarSegmento(IssqnSegmento a) throws BusinessViolation, Exception {
        try {
            verificarSegmento(a);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarSegmento(a);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover segmento
     *
     * @param segmentoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerSegmento(int segmentoId) throws BusinessViolation, Exception {
        IssqnSegmento s = repositorio.obterSegmentoPorId(segmentoId);
        if (s == null) {
            throw new Exception("Segmento não encontrado para exclusão");
        }

        try {
            repositorio.removerSegmento(segmentoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo segmento pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnSegmento obterSegmentoPorId(int id) throws BusinessViolation, Exception {
        IssqnSegmento s;

        try {
            s = repositorio.obterSegmentoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (s == null) {
            throw new BusinessViolation("Segmento não encontrado!");
        } else {
            return s;
        }
    }

    /**
     * método para retornar segmentos
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnSegmento> obterSegmento(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterSegmento(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
