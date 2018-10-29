/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geo.Bairro;
import br.com.eddydata.repositorio.admin.BairroRepositorio;
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
public class BairroServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private BairroRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new BairroRepositorio(em);
    }

    /**
     * método para incluir ou salvar bairros
     *
     * @param b
     * @return
     * @throws Exception
     */
    public Bairro salvarBairro(Bairro b) throws Exception {
        if (b == null) {
            throw new Exception("Informe o bairro a ser salvo");
        }

        try {
            return repositorio.salvarBairro(b);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover bairros
     *
     * @param bairroId
     * @param cidadeId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerBairro(int bairroId, int cidadeId) throws BusinessViolation, Exception {
        Bairro b = repositorio.obterBairroPorId(bairroId, cidadeId);
        if (b == null) {
            throw new Exception("Bairro não encontrado para exclusão");
        }

        try {
            repositorio.removerBairro(b);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar bairro pelo id
     *
     * @param id
     * @param idCidade
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public Bairro obterBairroPorId(int id, int idCidade) throws BusinessViolation, Exception {
        Bairro b;

        try {
            b = repositorio.obterBairroPorId(id, idCidade);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (b == null) {
            throw new BusinessViolation("Bairro não encontrado!");
        } else {
            return b;
        }
    }

    /**
     * método para retornar as bancos
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<Bairro> obterBairros(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterBairros(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para retornar os logradouros por nome e cidade
     *
     * @param idCidade
     * @param nome
     * @return
     * @throws Exception
     */
    public List<Bairro> obterBairrosPorNome(Integer idCidade, String nome) throws Exception {
        try {
            return repositorio.obterBairrosPorNome(idCidade, nome);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para retornar os bairros por cidade
     *
     * @param cidadeId
     * @param estadoId
     * @return
     */
    public List<Bairro> obterBairrosPorCidade(Integer cidadeId, Integer estadoId) {
        return repositorio.obterBairrosPorCidade(cidadeId, estadoId);
    }
}
