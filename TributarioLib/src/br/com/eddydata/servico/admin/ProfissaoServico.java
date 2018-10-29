/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geral.Profissao;
import br.com.eddydata.repositorio.admin.ProfissaoRepositorio;
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
public class ProfissaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private ProfissaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new ProfissaoRepositorio(em);
    }

    /**
     * método para incluir ou salvar profissoes
     *
     * @param p
     * @return
     * @throws Exception
     */
    public Profissao salvarProfissao(Profissao p) throws Exception {
        if (p == null) {
            throw new Exception("Informe a profissão a ser salva");
        }

        try {
            return repositorio.salvarProfissao(p);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover profissao
     *
     * @param profissaoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerProfissao(int profissaoId) throws BusinessViolation, Exception {
        Profissao p = repositorio.obterProfissaoPorId(profissaoId);
        if (p == null) {
            throw new Exception("Profissão não encontrada para exclusão");
        }

        try {
            repositorio.removerProfissao(profissaoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar profissao pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public Profissao obterProfissaoPorId(int id) throws BusinessViolation, Exception {
        Profissao p;

        try {
            p = repositorio.obterProfissaoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (p == null) {
            throw new BusinessViolation("Profissão não encontrada!");
        } else {
            return p;
        }
    }

    /**
     * método para retornar as profissoes
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<Profissao> obterProfissoes(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterProfissoes(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
