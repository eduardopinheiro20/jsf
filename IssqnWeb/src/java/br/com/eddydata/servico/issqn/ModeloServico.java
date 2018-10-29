/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.repositorio.issqn.ImovelRepositorio;
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
 * @author Thiago Martos
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ModeloServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private ImovelRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new ImovelRepositorio(em);
    }

    /**
     * método para incluir ou salvar imovel
     *
     * @param c
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public Imovel salvarImovel(Imovel c) throws BusinessViolation, Exception {
        try {
            return repositorio.salvarImovel(c);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover imovel
     *
     * @param imovelId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerImovel(int imovelId) throws BusinessViolation, Exception {
        Imovel t = repositorio.obterImovelPorId(imovelId);
        if (t == null) {
            throw new Exception("Imovel não encontrado para exclusão");
        }

        try {
            repositorio.removerImovel(imovelId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo imovel pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public Imovel obterImovelPorId(int id) throws BusinessViolation, Exception {
        Imovel t;

        try {
            t = repositorio.obterImovelPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (t == null) {
            throw new BusinessViolation("Imovel não encontrado!");
        } else {
            return t;
        }
    }

    /**
     * método para retornar as imoveis
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<Imovel> obterImoveis(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterImoveis(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
