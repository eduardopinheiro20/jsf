/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnConselhoRegional;
import br.com.eddydata.repositorio.issqn.IssqnConselhoRegionalRepositorio;
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
public class IssqnConselhoRegionalServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnConselhoRegionalRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnConselhoRegionalRepositorio(em);
    }

    /**
     * metódo de validacao do conselho regional
     *
     * @param conselho
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarConselhoRegional(IssqnConselhoRegional conselho) throws BusinessViolation, Exception {
        if (conselho == null) {
            throw new Exception("Conselho Regional não foi passado como parâmetro");
        }

        if (conselho.getDescricao() == null || conselho.getDescricao().trim().equals("")) {
            throw new BusinessViolation("Descrição dao conselho regional não informado!");
        }
    }

    /**
     * método para incluir ou salvar conselho regional
     *
     * @param c
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnConselhoRegional salvarConselhoRegional(IssqnConselhoRegional c) throws BusinessViolation, Exception {
        try {
            verificarConselhoRegional(c);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarConselhoRegional(c);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover conselho regional
     *
     * @param conselhoRegionalId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerConselhoRegional(int conselhoRegionalId) throws BusinessViolation, Exception {
        IssqnConselhoRegional c = repositorio.obterConselhoRegionalPorId(conselhoRegionalId);
        if (c == null) {
            throw new Exception("Conselho regional não encontrado para exclusão");
        }

        try {
            repositorio.removerConselhoRegional(conselhoRegionalId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo conselho regional pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnConselhoRegional obterConselhoRegionalPorId(int id) throws BusinessViolation, Exception {
        IssqnConselhoRegional c;

        try {
            c = repositorio.obterConselhoRegionalPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (c == null) {
            throw new BusinessViolation("Conselho regional não encontrado!");
        } else {
            return c;
        }
    }

    /**
     * método para retornar os conselhos regionais
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnConselhoRegional> obterConselhosRegionais(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterConselhosRegionais(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
