/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnNotificacao;
import br.com.eddydata.repositorio.issqn.IssqnNotificacaoRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
import java.util.Date;
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
public class IssqnNotificacaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnNotificacaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnNotificacaoRepositorio(em);
    }

    /**
     * método para incluir ou salvar Notificacao
     *
     * @param n
     * @return
     * @throws Exception
     */
    public IssqnNotificacao salvarNotificacao(IssqnNotificacao n) throws Exception {
        if (n == null) {
            throw new Exception("Informe o Notificação a ser salvo");
        }
        try {
            return repositorio.salvarNotificacao(n);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para remover Notificacao
     *
     * @param notificacaoId
     * @throws BusinessViolation
     * @throws Exception
     */
    public void removerNotificacao(int notificacaoId) throws BusinessViolation, Exception {
        IssqnNotificacao n = repositorio.obterNotificacaoPorId(notificacaoId);
        if (n == null) {
            throw new Exception("Notificação não encontrado para exclusão");
        }

        try {
            repositorio.removerNotificacao(notificacaoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para buscar Notificacao pelo id
     *
     * @param id
     * @return
     * @throws BusinessViolation
     * @throws Exception
     */
    public IssqnNotificacao obterNotificacaoPorId(int id) throws BusinessViolation, Exception {
        IssqnNotificacao n;

        try {
            n = repositorio.obterNotificacaoPorId(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (n == null) {
            throw new BusinessViolation("Notificação não encontrado!");
        } else {
            return n;
        }
    }

    /**
     * metodo que retorna os Notificacaos por contribuinte
     *
     * @param idContribuinte
     * @return
     * @throws Exception
     */
    public List<IssqnNotificacao> obterNotificacaoPorContribuinte(Integer idContribuinte) throws Exception {
        try {
            return repositorio.obterNotificacaoPorContribuinte(idContribuinte);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    public void imprimirAlertaNotificacao(Date dtInicial,Date dtTermino,String orgaoId) throws Exception
    {
       if (orgaoId == null || orgaoId.trim().equals("")) {
            throw new Exception("Orgão não passado como parâmetro");
        } 
      repositorio.imprimirAlertaNotificacao(dtInicial, dtTermino, orgaoId);
    }

}
