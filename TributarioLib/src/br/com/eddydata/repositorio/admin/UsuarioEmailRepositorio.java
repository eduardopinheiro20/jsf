/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.UsuarioEmail;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioEmailRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public UsuarioEmailRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public UsuarioEmail getUsuarioEmailPorId(int id) {
        return getEntidade(UsuarioEmail.class, id);
    }

    public UsuarioEmail setUsuarioEmail(UsuarioEmail u) {
        return setEntidade(UsuarioEmail.class, u);
    }

    public UsuarioEmail adicionarUsuarioEmail(UsuarioEmail u) {
        adicionarEntidade(UsuarioEmail.class, u);
        return u;
    }

    public void removerUsuarioEmail(UsuarioEmail u) {
        removerEntidade(u);
    }
   

    public List<UsuarioEmail> getUsuarioEmails() {
        return getListaPura(UsuarioEmail.class, "select ue from UsuarioEmail ue");
    }

}
