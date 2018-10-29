/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.geo.BairroLogradouro;
import br.com.eddydata.repositorio.issqn.BairroLogradouroRepositorio;
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
 * @author eddydata
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BairroLogradouroServico extends Servico{

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private BairroLogradouroRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new BairroLogradouroRepositorio(em);
    }

    /**
     * metodo de validacao da BairroLogradouro
     *
     * @param bairroLogradouro
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarBairroLogradouro(BairroLogradouro bairroLogradouro) throws BusinessViolation, Exception {
        if (bairroLogradouro == null) {
            throw new Exception("Imovel não foi passada como parâmetro");
        }

        //retirado devido a solicitação da manutenção 116911
//        if (bairroLogradouro.getId() != null) {
//            if (!repositorio.verificarExistenciaBairroLogradouro(bairroLogradouro.getId())) {
//
//                throw new BusinessViolation("Já existe um imóvel cadastrado com esse número e complemento. Informe um complemento diferente!");
//
//            }
//        }
    }

    /**
     * método para incluir ou salvar BairroLogradouro
     *
     * @param c
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public BairroLogradouro salvarBairroLogradouro(BairroLogradouro c) throws BusinessViolation, Exception {
        try {
            verificarBairroLogradouro(c);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        try {
            return repositorio.salvarBairroLogradouro(c);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover bairroLogradouro
     *
     * @param bairroLogradouroId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerImovel(int bairroLogradouroId) throws BusinessViolation, Exception {
        BairroLogradouro t = repositorio.obterBairroLogradouroPorId(bairroLogradouroId);
        if (t == null) {
            throw new Exception("Imovel não encontrado para exclusão");
        }

        try {
            repositorio.removerBairroLogradouro(bairroLogradouroId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar codigo BairroLogradouro pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public BairroLogradouro obterBairroLogradouroPorId(int id) throws BusinessViolation, Exception {
        BairroLogradouro t;

        try {
            t = repositorio.obterBairroLogradouroPorId(id);
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
    public List<BairroLogradouro> obterImoveis(String filtro, Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterBairroLogradouros(filtro, limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public List<BairroLogradouro> obterBairroPorLogradouro(Integer bairro, Integer logradouro) {
        return repositorio.obterBairroPorLogradouro(bairro, logradouro);
    }

    /**
     * Método para verificar se imóvel já foi cadastrado com e sem complemento
     *
     * @param bairroLogradouroId
     * @param nrImovel
     * @param complemento
     * @return true para já existir registro
     * @throws Exception
     */
//    private boolean verificarExistenciaImovel(Integer bairroLogradouroId, Integer nrImovel, String complemento) throws Exception {
//        if (bairroLogradouroId == null) {
//            throw new Exception("Parametro bairro logradouro não passado");
//        }
//        if (nrImovel == null) {
//            throw new Exception("Parametro número do imóvel não passado");
//        }
//        complemento = (complemento == null ? "" : complemento.trim().toUpperCase());
//
//        return repositorio.verificarExistenciaImovel(bairroLogradouroId, nrImovel, complemento);
//    }
}
