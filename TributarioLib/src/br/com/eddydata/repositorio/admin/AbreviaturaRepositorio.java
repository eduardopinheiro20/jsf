/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geo.Abreviatura;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

public class AbreviaturaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public AbreviaturaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized Abreviatura salvarAbreviatura(Abreviatura a) {
        if (a.getId() == null) {
            return adicionarEntidade(Abreviatura.class, a);
        } else {
            return setEntidade(Abreviatura.class, a);
        }
    }

    public synchronized void removerAbreviatura(int abreviaturaId) {
        Abreviatura a = getEntidade(Abreviatura.class, abreviaturaId);
        removerEntidade(a);
    }

    public Abreviatura obterAbreviaturaPorId(int id) {
        return getEntidade(Abreviatura.class, id);
    }

    public List<Abreviatura> obterAbreviaturas(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where a.id = " + filtro;
            } else {
                where += "\n where UPPER(a.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(Abreviatura.class,
                "select a from Abreviatura a "
                + where
                + "\norder by a.nome", limite
        );

    }

}
