/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.repositorio.issqn.ArquivoCobrancaBoletoRepositorio;
import br.com.eddydata.repositorio.issqn.ArquivoCobrancaFebrabanRepositorio;
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
public class ArquivoCobrancaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private ArquivoCobrancaFebrabanRepositorio repositorioFebraban;
    private ArquivoCobrancaBoletoRepositorio repositorioBoleto;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorioFebraban = new ArquivoCobrancaFebrabanRepositorio(em);
        repositorioBoleto = new ArquivoCobrancaBoletoRepositorio(em);
    }

    public void gerarArquivo(int exercicio, String order, String orgao, String tipo, String opcao_cobranca)
            throws RuntimeException, Exception {

        if (opcao_cobranca.equals("F")) {
            repositorioFebraban.gerarArquivo(exercicio, order, orgao, tipo, opcao_cobranca);
        } else {
            repositorioBoleto.gerarArquivo(exercicio, order, orgao, tipo, opcao_cobranca);
        }
    }
}
