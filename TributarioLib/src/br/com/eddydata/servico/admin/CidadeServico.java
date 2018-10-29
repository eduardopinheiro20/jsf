/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2015, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.repositorio.admin.CidadeRepositorio;
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
public class CidadeServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private CidadeRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new CidadeRepositorio(em);
    }

    public CidadeServico() {

    }

    public Cidade getCidade(int id) {
        return repositorio.getCidade(id);
    }

    public Cidade setCidade(Cidade c) {
        return repositorio.setCidade(c);
    }

    public void removerCidade(Cidade c) {
        repositorio.removerCidade(c);
    }

    public Cidade adicionarCidade(Cidade c) {
        return repositorio.adicionarCidade(c);
    }

    public List<Cidade> getCidades() {
        return repositorio.getCidades();
    }

    public List<Cidade> getCidadePorNome(String nome) {
        return repositorio.getCidadePorNome(nome);
    }

    public List<Cidade> getCidadePorNomeEstado(String nome, int estado) {
        return repositorio.getCidadePorNomeEstado(nome, estado);
    }

    public List<Cidade> getCidadePorCep(String cep) {
        return repositorio.getCidadePorCep(cep);
    }

    /**
     * Método para buscar cidades por estado
     *
     * @param estadoId
     * @return
     * @throws Exception
     */
    public List<Cidade> obterCidadesPorEstado(Integer estadoId) throws Exception {
        if (estadoId == null) {
            throw new Exception("Estado não passado como parametro para consulta");
        }
        return repositorio.obterCidadesPorEstado(estadoId);
    }
}
