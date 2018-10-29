/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geral.Banco;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

public class BancoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public BancoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized Banco salvarBanco(Banco b) {
        if (b.getId() == null) {
            return adicionarEntidade(Banco.class, b);
        } else {
            return setEntidade(Banco.class, b);
        }
    }

    public synchronized void removerBanco(int bancoId) {
        Banco b = getEntidade(Banco.class, bancoId);
        removerEntidade(b);
    }

    public Banco obterBancoPorId(int id) {
        return getEntidade(Banco.class, id);
    }

    public List<Banco> obterBancos(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n and b.id = " + filtro;
            } else {
                where += "\n and UPPER(function('rem_acento', b.nome)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(Banco.class,
                "select b from Banco b where 1=1 "
                + where
                + "\norder by b.nome", limite
        );

    }

}
