/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.geo.BairroLogradouro;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author eddydata
 */
public class BairroLogradouroRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public BairroLogradouroRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized BairroLogradouro salvarBairroLogradouro(BairroLogradouro c) {
        if (c.getId() == null) {
            return adicionarEntidade(BairroLogradouro.class, c);
        } else {
            return setEntidade(BairroLogradouro.class, c);
        }
    }

    public synchronized void removerBairroLogradouro(int categoriaId) {
        BairroLogradouro t = getEntidade(BairroLogradouro.class, categoriaId);
        removerEntidade(t);
    }

    public BairroLogradouro obterBairroLogradouroPorId(int id) {
        return getEntidade(BairroLogradouro.class, id);
    }

    public List<BairroLogradouro> obterBairroLogradouros(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where b.id = " + filtro;
            } else {
                where += "\n where UPPER(b.bairro.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(BairroLogradouro.class,
                "select b from BairroLogradouro b "
                + where
                + "\norder by b.id", limite
        );

    }

    public List<BairroLogradouro> obterBairroPorLogradouro(Integer bairro, Integer logradouro) {
        String where = "";
        if (bairro != null && logradouro != null) {
            where += "\n where b.bairro.idBairro = " + bairro + " and b.logradouro.logradouroPK.idLogradouro = " + logradouro;
        }

        return getListaPura(BairroLogradouro.class,
                "select b from BairroLogradouro b "
                + where
                + "\norder by b.id"
        );

    }

    public boolean verificarExistenciaBairroLogradouro(Integer bairroLogradouroId) {
        Long count = getEntidadePura(Long.class,
                "select count(1) from Imovel i"
                + "\n join i.bairroLogradouro bl"
                + "\nwhere bl.id = ?1", bairroLogradouroId
        );

        return (count > 0);
    }

}
