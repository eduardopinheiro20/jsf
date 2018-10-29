/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnConfiguracao;
import br.com.eddydata.repositorio.issqn.IssqnConfiguracaoRepositorio;
import br.com.eddydata.servico.Servico;
import br.com.eddydata.suporte.BusinessViolation;
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
 * @author Eddydata
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IssqnConfiguracaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private IssqnConfiguracaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new IssqnConfiguracaoRepositorio(em);
    }

    private void verificarConfiguracao(IssqnConfiguracao configuracao) throws BusinessViolation, Exception {
        if (configuracao == null) {
            throw new Exception("Configuracao não foi passada como parâmetro");
        }

        if (configuracao.getFebraban() == null) {
            throw new BusinessViolation("Informe o Febraban!");
        }
    }

    public void verificarExercicio(Integer ano) {
        repositorio.abrirExercicio(ano);
    }

    /**
     * método para incluir ou salvar configuracaos
     *
     * @param c
     * @return
     * @throws br.com.eddydata.suporte.BusinessViolation
     * @throws Exception
     */
    public IssqnConfiguracao salvarConfiguracao(IssqnConfiguracao c) throws BusinessViolation, Exception {
        if (c == null) {
            throw new Exception("Informe a configuracao a ser salva");
        }

        try {
            verificarConfiguracao(c);
        } catch (BusinessViolation e) {
            throw new BusinessViolation(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        try {
            return repositorio.salvarConfiguracao(c);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * método para retornar a configuracao atual por orgão
     *
     * @param orgaoId
     * @return
     * @throws Exception
     */
    public IssqnConfiguracao obterConfiguracao(String orgaoId) throws Exception {
        IssqnConfiguracao c;
        try {
            c = repositorio.obterConfiguracao(orgaoId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (c == null) {
            ContabilOrgao orgao = new ContabilOrgao();
            orgao.setIdOrgao(orgaoId);

            c = new IssqnConfiguracao();
            c.setOrgao(orgao);
            c.setFebraban(0);
            c.setModeloAlvara(0);
            c.setModeloCertidao(0);
            c.setModeloCarne(0);

            try {
                c = salvarConfiguracao(c);
            } catch (Exception e) {
                throw new Exception("Parametro não encontrado e ocorreu um erro ao salvar novo parametro " + e.getMessage());
            }
        }

        return c;
    }
}
