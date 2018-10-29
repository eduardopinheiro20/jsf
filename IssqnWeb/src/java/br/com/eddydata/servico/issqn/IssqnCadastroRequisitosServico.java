/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnCadastroRequisitos;
import br.com.eddydata.repositorio.issqn.IssqnCadastroRequisitosRepositorio;
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
public class IssqnCadastroRequisitosServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnCadastroRequisitosRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnCadastroRequisitosRepositorio(em);
    }

    /**
     * metódo de validacao da zona
     *
     * @param zona
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarCadastroRequisitos(IssqnCadastroRequisitos zona) throws BusinessViolation, Exception {
        if (zona == null) {
            throw new Exception("Zona do Município não foi passado como parâmetro");
        }

        if (zona.getNome() == null || zona.getNome().isEmpty()) {
            throw new BusinessViolation("Nome da Zona do Município não informado!");
        }
    }

    /**
     * método para incluir ou salvar zona
     *
     * @param zona
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnCadastroRequisitos salvarCadastroRequisitos (IssqnCadastroRequisitos zona) throws BusinessViolation, Exception {
        try {
            verificarCadastroRequisitos(zona);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarCadastroRequisitos(zona);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover zona
     *
     * @param zonaId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerCadastroRequisitos (int zonaId) throws BusinessViolation, Exception {
        IssqnCadastroRequisitos o = repositorio.obterCadastroRequisitosPorId(zonaId);
        if (o == null) {
            throw new Exception("Zona do Município não encontrado para exclusão");
        }

        try {
            repositorio.removerCadastroRequisitos(zonaId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo zona pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnCadastroRequisitos obterCadastroRequisitosPorId(int id) throws BusinessViolation, Exception {
        IssqnCadastroRequisitos o;

        try {
            o = repositorio.obterCadastroRequisitosPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (o == null) {
            throw new BusinessViolation("Zona do Municípioo não encontrado!");
        } else {
            return o;
        }
    }

    /**
     * método para retornar os questionario
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnCadastroRequisitos> obterCadastroRequisitos(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterCadastroRequisitos(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
}
