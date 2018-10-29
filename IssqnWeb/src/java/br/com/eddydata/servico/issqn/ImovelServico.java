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
import br.com.eddydata.suporte.Util;
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
public class ImovelServico extends Servico {

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
     * metodo de validacao da imovel
     *
     * @param imovel
     * @throws BusinessViolation
     * @throws Exception
     */
    private void verificarImovel(Imovel imovel) throws BusinessViolation, Exception {
        if (imovel.getId() == null) {
            throw new Exception("Imovel não foi passada como parâmetro");
        }
        if (imovel.getBairrologradouro() == null) {
            throw new BusinessViolation("Dados de localização não informados!");
        }

        if (verificarExistenciaImovel(imovel.getBairrologradouro().getId(), Util.extractInt(imovel.getNrImovel()), imovel.getComplemento()) && imovel.getId() == null) {
            if (imovel.getComplemento() == null || imovel.getComplemento().equals("")) {
                throw new BusinessViolation("Já existe um imóvel cadastrado com esse número. Informe um complemento!");
            } else {
                throw new BusinessViolation("Já existe um imóvel cadastrado com esse número e complemento. Informe um complemento diferente!");
            }
        }
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
//        try {
//            verificarImovel(c);
//        } catch (BusinessViolation e) {
//            throw new BusinessViolation(e.getMessage());
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
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
    
    /**
     * método para retornar as imoveis
     *
     * @param filtro
     * @param limite
     * @return
     * @throws Exception
     */
    public List<Imovel> obterImovel(String filtro, String filtro2 ,String complemento,Integer limite) throws Exception {
        filtro = (filtro == null ? "" : filtro.trim().toUpperCase());
        filtro = (filtro2 == null ? "" : filtro.trim().toUpperCase());
        filtro = (complemento == null ? "" : filtro.trim().toUpperCase());
        limite = (limite == null ? 100 : limite);

        try {
            return repositorio.obterImovelPorNome(filtro, filtro2,complemento,limite);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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
    private boolean verificarExistenciaImovel(Integer bairroLogradouroId, Integer nrImovel, String complemento) throws Exception {
        if (bairroLogradouroId == null) {
            throw new Exception("Parametro bairro logradouro não passado");
        }
        if (nrImovel == null) {
            throw new Exception("Parametro número do imóvel não passado");
        }
        complemento = (complemento == null ? "" : complemento.trim().toUpperCase());

        return repositorio.verificarExistenciaImovel(bairroLogradouroId, nrImovel, complemento);
    }
}
