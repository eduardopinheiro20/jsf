/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geo.BairroLogradouro;
import br.com.eddydata.entidade.geo.Logradouro;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class LogradouroRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public LogradouroRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public Logradouro getLogradouro(int id) {
        return getEntidade(Logradouro.class, id);
    }

    public Logradouro setLogradouro(Logradouro l) {
        return setEntidade(Logradouro.class, l);
    }

    public void removerLogradouro(Logradouro l) {
        removerEntidade(l);
    }

    public Logradouro adicionarLogradouro(Logradouro l) {
        if (l.getLogradouroPK().getIdLogradouro() == null) {
            adicionarEntidade(Logradouro.class, l);
        } else {
            setEntidade(Logradouro.class, l);
        }
        return l;
    }

    public List<Logradouro> obterLogradourosPorNome(Integer idCidade, String nome) {
        return getListaPura(Logradouro.class,
                "select l from Logradouro l "
                + "\nwhere l.logradouroPK.idCidade = ?1"
                + "\nand UPPER(l.nome) LIKE ?2"
                + "\norder by l.nome", idCidade, nome.toUpperCase());

    }

    public List<BairroLogradouro> obterBairroLogradourosPorCidadeBairro(Integer cidadeId, Integer bairroId) {
        return getListaPura(BairroLogradouro.class,
                "select bl from BairroLogradouro bl "
                + "\n join bl.bairro b"
                + "\nwhere b.bairroPK.idCidade = ?1"
                + "\n  and b.bairroPK.idBairro = ?2"
                + "\norder by b.nome", cidadeId, bairroId
        );

    }

}
