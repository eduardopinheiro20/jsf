/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author David
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geo.Estado;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

public class EstadoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public EstadoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public Estado getEstado(int id) {
        return getEntidade(Estado.class, id);
    }

    public Estado setEstado(Estado c) {
        return setEntidade(Estado.class, c);
    }

    public Estado adicionarEstado(Estado c) {
        adicionarEntidade(Estado.class, c);
        return c;
    }

    public void removerEstado(Estado c) {
        removerEntidade(c);
    }

    public Estado getEstadoPorId(int id) {
        return getEntityManager().find(Estado.class, id);
    }

    public List<Estado> getEstados() {
        return getListaPura(Estado.class, "select e from Estado e order by e.nome");
    }

    public List<Estado> getEstadoPorNome(String nome) {
        return getListaPura(Estado.class, "select e from Estado e where upper(e.nome) like ?1 order by e.nome", Util.Texto.removeAcentos(nome).toUpperCase());
    }

}
