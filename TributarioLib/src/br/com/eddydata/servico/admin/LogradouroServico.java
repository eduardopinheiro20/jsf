/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geo.BairroLogradouro;
import br.com.eddydata.entidade.geo.Logradouro;
import br.com.eddydata.repositorio.admin.LogradouroRepositorio;
import br.com.eddydata.servico.Servico;
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
public class LogradouroServico extends Servico {
    
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext
    private EntityManager em;
    
    private LogradouroRepositorio repositorio;
    
    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new LogradouroRepositorio(em);
    }

    /**
     * método para retornar os logradouros por nome e cidade
     *
     * @param idCidade
     * @param nome
     * @return
     * @throws Exception
     */
    public List<Logradouro> obterLogradourosPorNome(Integer idCidade,String nome) throws Exception {
        try {
            return repositorio.obterLogradourosPorNome(idCidade,nome);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Método para retornar os bairros logradouros por cidade e bairro
     *
     * @param cidadeId
     * @param bairroId
     * @return
     * @throws Exception
     */
    public List<BairroLogradouro> obterBairroLogradourosPorCidadeBairro(Integer cidadeId, Integer bairroId) throws Exception {
        if (cidadeId == null) {
            throw new Exception("Cidade não passada como parametro para busca");
        }
        if (bairroId == null) {
            throw new Exception("Bairro não passado como parametro para busca");
        }
        return repositorio.obterBairroLogradourosPorCidadeBairro(cidadeId, bairroId);
    }
    
    public Logradouro getLogradouro(int id) {
        return repositorio.getLogradouro(id);
    }

    public Logradouro setLogradouro(Logradouro l) {
        return repositorio.setLogradouro(l);
    }

    public void removerLogradouro(Logradouro l) {
        repositorio.removerLogradouro(l);
    }
    
    public Logradouro adicionarLogradouro(Logradouro l) {
        return repositorio.adicionarLogradouro(l);
    }
}
