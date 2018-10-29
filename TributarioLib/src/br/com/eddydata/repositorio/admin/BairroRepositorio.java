/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geo.Bairro;
import br.com.eddydata.entidade.geo.BairroPK;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class BairroRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public BairroRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized Bairro salvarBairro(Bairro b) {
        if (b.getIdBairro() == null) {
            return adicionarEntidade(Bairro.class, b);
        } else {
            return setEntidade(Bairro.class, b);
        }
    }

    public synchronized void removerBairro(Bairro b) {
        removerEntidade(b);
    }

    public Bairro obterBairroPorId(int id, int idCidade) {
        BairroPK pk = new BairroPK();
        pk.setIdBairro(id);
        pk.setIdCidade(idCidade);
        return getEntidade(Bairro.class, pk);
    }

    public List<Bairro> obterBairros(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where b.bairroPK.idBairro = " + filtro;
            } else {
                where += "\n where UPPER(b.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(Bairro.class,
                "select b from Bairro b "
                + where
                + "\norder by b.nome", limite);

    }

    public List<Bairro> obterBairrosPorNome(Integer idCidade, String nome) {
        return getListaPura(Bairro.class,
                "select b from Bairro b "
                + "\nwhere b.cidade.idCidade = ?1"
                + "\nand UPPER(b.nome) LIKE ?2"
                + "\norder by b.nome", idCidade, nome.toUpperCase());

    }

    public List<Bairro> obterBairrosPorCidade(Integer cidadeId, Integer estadoId) {
        return getListaPura(Bairro.class,
                "select b from Bairro b, Cidade c "
                + "\nwhere b.cidade = c.cidade"
                + "\nand c.idCidade = ?1"
                + "\nand c.estado.id = ?2"
                + "\norder by b.nome", cidadeId, estadoId
        );

    }

}
