/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.UsuarioEmail;
import br.com.eddydata.repositorio.admin.UsuarioEmailRepositorio;
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
public class UsuarioEmailServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private UsuarioEmailRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new UsuarioEmailRepositorio(em);
    }

    public UsuarioEmailServico() {

    }

    public UsuarioEmail getUsuarioEmailPorId(int id) {
        return repositorio.getUsuarioEmailPorId(id);
    }

    public UsuarioEmail setUsuarioEmail(UsuarioEmail u) {
        return repositorio.setUsuarioEmail(u);
    }

    public void removerUsuarioEmail(UsuarioEmail u) {
        repositorio.removerUsuarioEmail(u);
    }

    public UsuarioEmail adicionarUsuarioEmail(UsuarioEmail u) {
        return repositorio.adicionarUsuarioEmail(u);
    }


    public List<UsuarioEmail> getUsuarioEmails() {
        return repositorio.getUsuarioEmails();
    }

}
