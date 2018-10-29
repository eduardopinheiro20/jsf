/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geral.Pessoa;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Leandro
 */
public class PessoaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public PessoaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public Pessoa adicionarPessoa(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            adicionarEntidade(Pessoa.class, pessoa);
        } else {
            setEntidade(Pessoa.class, pessoa);
        }

        return pessoa;
    }

    public Pessoa obterPessoaPorId(int pessoaId) {
        return getEntidade(Pessoa.class, pessoaId);
    }

    public void removerPessoa(int pessoaId) {
        Pessoa pessoa = obterPessoaPorId(pessoaId);
        removerEntidade(pessoa);

    }

    public List<Pessoa> getPessoas() {
        return getListaPura(Pessoa.class,
                "select p from Pessoa p "
                + "order by p.nome");
    }

    public List<Pessoa> getPessoasPorTipo(int tipo) {
        return getListaPura(Pessoa.class,
                "select p from Pessoa p"
                + "\nwhere p.tipo = ?1"
                + "\norder by p.nome", tipo);

    }

    public List<Pessoa> getPessoasPorNome(String nome) {
        return getListaPura(Pessoa.class,
                "select p from Pessoa p"
                + "\nwhere UPPER(p.nome) LIKE ?1"
                + "\norder by p.nome", nome.toUpperCase() + "%");
    }

    public List<Pessoa> getPessoasPorNomeLimite(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where p.id = " + filtro;
            } else {
                where += "\n where UPPER(p.nome) like " + Util.quotarStr(filtro.toUpperCase() + "%");
            }
        }

        return getListaPuraLimite(Pessoa.class,
                "select p from Pessoa p "
                + where
                + "\norder by p.id", limite);

    }

    public List<Pessoa> getPessoasPorSituacao(int situacao) {
        return getListaPura(Pessoa.class,
                "select p from Pessoa p"
                + "\nwhere p.situacao = ?1"
                + "\norder by p.nome", situacao);

    }
    
    public List<Pessoa> getPessoasPorCpfCnpj(String cpfCnpj) {
        return getListaPura(Pessoa.class,
                "select p from Pessoa p"
                + "\nwhere p.cpfCnpj = ?1"
                + "\norder by p.nome", cpfCnpj);

    }

}
