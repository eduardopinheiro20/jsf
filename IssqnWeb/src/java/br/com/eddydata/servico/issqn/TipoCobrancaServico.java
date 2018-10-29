/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.issqn;

import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.repositorio.issqn.TipoCobrancaRepositorio;
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
public class TipoCobrancaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private TipoCobrancaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new TipoCobrancaRepositorio(em);
    }

    public TipoCobrancaServico() {

    }

    public IssqnTipoCobranca getTipoCobrancaPorId(int id) {
        return repositorio.getTipoCobrancaPorId(id);
    }
    
    public IssqnTipoCobranca getTipoCobrancaPorNome(String nome) {
        return repositorio.getTipoCobrancaPorNome(nome);
    }

    public IssqnTipoCobranca setTipoCobranca(IssqnTipoCobranca u) {
        return repositorio.setTipoCobranca(u);
    }

    public void removerTipoCobranca(IssqnTipoCobranca u) {
        repositorio.removerTipoCobranca(u);
    }

    public IssqnTipoCobranca adicionarTipoCobranca(IssqnTipoCobranca u) throws RuntimeException {
        return repositorio.adicionarTipoCobranca(u);
    }

    public List<IssqnTipoCobranca> getTodosCobrancas(Integer exercicio) {
        return repositorio.getTodosCobrancas(exercicio);
    }
}
