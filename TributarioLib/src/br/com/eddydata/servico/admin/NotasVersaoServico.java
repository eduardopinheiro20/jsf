/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2015, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.NotasVersao;
import br.com.eddydata.repositorio.admin.NotasVersaoRepositorio;
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
public class NotasVersaoServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private NotasVersaoRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new NotasVersaoRepositorio(em);
    }

    public NotasVersaoServico() {

    }

    public NotasVersao getNota(int id) {
        return repositorio.getNota(id);
    }

    public NotasVersao setNota(NotasVersao c) {
        return repositorio.setNota(c);
    }

    public void removerNota(NotasVersao c) {
        repositorio.removerNota(c);
    }

    public NotasVersao adicionarNota(NotasVersao c) {
        return repositorio.adicionarNota(c);
    }

    public List<NotasVersao> getNotas() {
        return repositorio.getNotas();
    }

    public List<NotasVersao> getNotaPorDescricao(String descricao) {
        return repositorio.getNotaPorDescricao(descricao);
    }

    public List<NotasVersao> getNotaPorSistema(String nome) {
        return repositorio.getNotaPorSistema(nome);
    }

    /**
     * pega a ultima versão do sistema
     *
     * @param sistema
     * @return y.m.d (0.0.0 caso nao exista)
     */
    public String getUltimaVersao(String sistema) {
        String v = repositorio.getUltimaVersao(sistema);
        return (v != null && !v.equals("") ? v : "0.0.0");
    }
}
