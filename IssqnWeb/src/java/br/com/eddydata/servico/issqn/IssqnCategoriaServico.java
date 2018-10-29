/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnCategoria;
import br.com.eddydata.repositorio.issqn.IssqnCategoriaRepositorio;
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
public class IssqnCategoriaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnCategoriaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnCategoriaRepositorio(em);
    }

    /**
     * metodo de validacao da categoria
     *
     * @param categoria
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarCategoria(IssqnCategoria categoria) throws BusinessViolation, Exception {
        if (categoria == null) {
            throw new Exception("Categoria não foi passada como parâmetro");
        }
        if (categoria.getNome() == null || categoria.getNome().trim().equals("")) {
            throw new BusinessViolation("Categoria não informada!");
        }
        if (categoria.getValor() == null) {
            throw new BusinessViolation("Valor não informado!");
        }
    }

    /**
     * método para incluir ou salvar categoria
     *
     * @param c
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnCategoria salvarCategoria(IssqnCategoria c) throws BusinessViolation, Exception {
        try {
            verificarCategoria(c);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.salvarCategoria(c);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover categoria
     *
     * @param categoriaId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerCategoria(int categoriaId) throws BusinessViolation, Exception {
        IssqnCategoria t = repositorio.obterCategoriaPorId(categoriaId);
        if (t == null) {
            throw new Exception("Categoria não encontrada para exclusão");
        }

        try {
            repositorio.removerCategoria(categoriaId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo categoria pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnCategoria obterCategoriaPorId(int id) throws BusinessViolation, Exception {
        IssqnCategoria t;

        try {
            t = repositorio.obterCategoriaPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Categoria não encontrada!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar as categorias
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<IssqnCategoria> obterCategorias(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterCategorias(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
