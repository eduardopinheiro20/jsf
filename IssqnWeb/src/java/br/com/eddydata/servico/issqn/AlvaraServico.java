/*
 * To change this license header, choose License Headers in Project Propertip.
 * To change this template file, choose Tools | Templatp
 * and open the template in the editor.
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.repositorio.issqn.AlvaraRepositorio;
import br.com.eddydata.servico.Servico;
import java.util.Date;
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
public class AlvaraServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private AlvaraRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new AlvaraRepositorio(em);
    }

    public void imprimirAlvara(String orgaoId, String ordem, String pesquisa,
            Issqn contribuinte, String data_formatada, Integer exercicio, Date validade,
            String tipo, String usuario, String observacao)
            throws RuntimeException, Exception {
        if (orgaoId == null) {
            throw new Exception("Orgão não passado como parâmetro");
        }
        if (ordem == null) {
            throw new Exception("Ordem não passada como parâmetro");
        }
        if (data_formatada == null) {
            throw new Exception("Data formatada não passada como parâmetro");
        }
        if (exercicio == null) {
            throw new Exception("Exercicio não passado como parâmetro");
        }

        repositorio.imprimirAlvara(orgaoId, ordem, pesquisa, contribuinte, data_formatada, exercicio, validade, tipo, usuario, observacao);
    }

    public void imprimirLicencasVencidas(String orgaoId, String filtro, String ordem) throws Exception {
        if (orgaoId == null || orgaoId.trim().equals("")) {
            throw new Exception("Orgão não passado como parâmetro");
        }
        if (ordem == null || ordem.trim().equals("")) {
            throw new Exception("Ordenação não passada como parâmetro");
        }
        repositorio.imprimirLicencasVencidas(orgaoId, filtro, ordem);
    }

    /**
     * relatório certidao Negativa
     *
     * @param orgaoId
     * @param filtro
     * @throws Exception
     */
    public void imprimirCertidaoNegativa(String orgaoId, String inscricao, Date validade, String contribuinte, int id_pessoa) throws Exception {
        repositorio.imprimirCertidaoNegativa(orgaoId, inscricao, validade, contribuinte, id_pessoa);
    }
}
