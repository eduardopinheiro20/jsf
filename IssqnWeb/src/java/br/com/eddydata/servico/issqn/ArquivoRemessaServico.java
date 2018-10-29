/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.repositorio.issqn.ArquivoRemessaBB;
import br.com.eddydata.servico.Servico;
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
public class ArquivoRemessaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private ArquivoRemessaBB repositorioRemessaBB;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorioRemessaBB = new ArquivoRemessaBB(em);
    }

    public void gerarArquivo(int exercicio, String orgao, Banco banco)
            throws RuntimeException, Exception {

        if (banco.getNumero().equals("1")) {
            repositorioRemessaBB.gerarRemessa(exercicio, orgao, banco);
        } else {
            throw new Exception("Remessa não implementada");
        }
    }
}
