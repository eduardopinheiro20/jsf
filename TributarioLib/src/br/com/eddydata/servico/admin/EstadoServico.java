/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2015, Eddydata ltda.
 * Diretors Reservados.
 * @author David
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geo.Estado;
import br.com.eddydata.repositorio.admin.EstadoRepositorio;
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
public class EstadoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private EstadoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new EstadoRepositorio(em);
    }

    public EstadoServico() {

    }

    public Estado getEstado(int id) {
        return repositorio.getEstado(id);
    }

    public Estado setEstado(Estado e) {
        return repositorio.setEstado(e);
    }

    public void removerEstado(Estado e) {
        repositorio.removerEstado(e);
    }

    public Estado adicionarEstado(Estado e) {
        return repositorio.adicionarEstado(e);
    }

    public List<Estado> getEstados() {
        return repositorio.getEstados();
    }

    public List<Estado> getEstadoPorNome(String nome) {
        return repositorio.getEstadoPorNome(nome);
    }

}
