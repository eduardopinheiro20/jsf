/**
 * @Copyright (C) 2015
 * @Description Regras de negócio para Orgãos
 * @author Eddydata Informática ltda.
 * @version 1.0
 *
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.Sistema;
import br.com.eddydata.repositorio.admin.SistemaRepositorio;
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

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SistemaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;
    private SistemaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void postConstruct() {
        repositorio = new SistemaRepositorio(em);
    }

    public SistemaServico() {

    }

    public Sistema obterSistema(String id) {
        return repositorio.obterSistema(id);
    }

    public Sistema salvarSistema(Sistema model) {
        return repositorio.salvarSistema(model);
    }

    public void removerSistema(Sistema model) {
        repositorio.removerSistema(model);
    }

    public List<Sistema> obterTodosSistemas() {
        return repositorio.obterTodosSistemas();
    }

    public List<Sistema> obterSistemaByNome(String nome) {
        return repositorio.obterSistemaPorNome(nome);
    }

    public Sistema obterSistemaPorNomeExato(String nome) {
        return repositorio.obterSistemaPorNomeExato(nome);
    }

}
