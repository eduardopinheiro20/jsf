/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2015, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.repositorio.admin.OrgaoRepositorio;
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
public class OrgaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private OrgaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new OrgaoRepositorio(em);
    }

    public OrgaoServico() {

    }

    public ContabilOrgao getOrgao(int id) {
        return repositorio.getOrgao(id);
    }

    public ContabilOrgao setOrgao(ContabilOrgao c) {
        return repositorio.setOrgao(c);
    }

    public void removerOrgao(ContabilOrgao c) {
        repositorio.removerOrgao(c);
    }

    public ContabilOrgao adicionarOrgao(ContabilOrgao c) {
        return repositorio.adicionarOrgao(c);
    }

    public List<ContabilOrgao> getOrgaos() {
        return repositorio.getOrgaos();
    }

    public List<ContabilOrgao> getOrgaoPorNome(String nome) {
        return repositorio.getOrgaoPorNome(nome);
    }   
}
