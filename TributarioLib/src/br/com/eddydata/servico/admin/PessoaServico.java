/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.repositorio.admin.PessoaRepositorio;
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

/**
 *
 * @author Leandro
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PessoaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private PessoaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new PessoaRepositorio(em);
    }

    public PessoaServico() {

    }

    //insere um novo objeto Pessoa se o objeto for nulo ou atualiza se ele existir
    public Pessoa adicionarPessoa(Pessoa pessoa) {
            return repositorio.adicionarPessoa(pessoa);
    }

    //busca os dados de um objeto Pessoa
    public Pessoa obterPessoaPorId(int pessoaId) {
        return repositorio.obterPessoaPorId(pessoaId);
    }

    //remove um objeto Pessoa
    public void removerPessoa(int pessoaId) {
        repositorio.removerPessoa(pessoaId);
    }

    //Lista todos os objetos pessoa em ordem alfabética pelo nome da pessoa
    public List<Pessoa> getPessoas() {
        return repositorio.getPessoas();
    }

    //Lista todas as pessoas somente pelo nome em ordem alfabética
    public List<Pessoa> getPessoasPorNome(String nome) {
        return repositorio.getPessoasPorNome(nome);
    }

    //Lista todas as pessoas somente pelo nome com limite em ordem alfabética
    public List<Pessoa> getPessoasPorNomeLimite(String filtro, Integer limite) {
        return repositorio.getPessoasPorNomeLimite(filtro, limite);
    }

    //Lista todos os objetos pessoa pelo tipo da pessoa em ordem alfabética
    public List<Pessoa> getPessoasPorTipo(int tipo) {
        return repositorio.getPessoasPorTipo(tipo);
    }

    //Lista todos os objetos pessoa pela situação da pessoa em ordem alfabética
    public List<Pessoa> getPessoasPorSituacao(int situacao) {
        return repositorio.getPessoasPorSituacao(situacao);
    }
    
    //Lista todos os objetos pessoa por CpfCnpj
    public List<Pessoa> getPessoasPorCpfCnpj(String cpfCnpj) {
        return repositorio.getPessoasPorCpfCnpj(cpfCnpj);
    }
}
