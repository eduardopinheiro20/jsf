/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnSocio;
import br.com.eddydata.repositorio.issqn.IssqnSocioRepositorio;
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
public class IssqnSocioServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnSocioRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnSocioRepositorio(em);
    }

    /**
     * método para incluir ou salvar socio
     *
     * @param s
     * @return
     * @throws Exception
     */
    public IssqnSocio salvarSocio(IssqnSocio s) throws Exception {
        if (s == null) {
            throw new Exception("Informe o socio a ser salvo");
        }

        try {
            return repositorio.salvarSocio(s);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover socio
     *
     * @param socioId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerSocio(int socioId) throws BusinessViolation, Exception {
        IssqnSocio s = repositorio.obterSocioPorId(socioId);
        if (s == null) {
            throw new Exception("Sócio não encontrado para exclusão");
        }

        try {
            repositorio.removerSocio(socioId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar socio pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnSocio obterSocioPorId(int id) throws BusinessViolation, Exception {
        IssqnSocio s;

        try {
            s = repositorio.obterSocioPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (s == null) {
            throw new BusinessViolation("Socio não encontrado!");
        } else {
            return s;
        }
    }

    /**
     * metodo que retorna os socios por contribuinte
     *
     * @param idContribuinte
     * @return
     * @throws Exception
     */
    public List<IssqnSocio> obterSocioPorContribuinte(Integer idContribuinte) throws Exception {
        try {
            return repositorio.obterSocioPorContribuinte(idContribuinte);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * metodo que atualiza o socio 
     *
     * @param s
     * @return
     * @throws Exception
     */
    public IssqnSocio atualizarSocio(IssqnSocio s) throws Exception {
        try {
            IssqnSocio anterior = obterSocioPorId(s.getId());
            
            anterior.setPessoa(s.getPessoa());
            
            return repositorio.salvarSocio(anterior);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public List<IssqnSocio> getSocioPorNome(String nome) {
        return repositorio.getSocioPorNome(nome);
    }

}
