/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geral.Religiao;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

public class ReligiaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public ReligiaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized Religiao salvarReligiao(Religiao r) {
        if (r.getId() == null) {
            return adicionarEntidade(Religiao.class, r);
        } else {
            return setEntidade(Religiao.class, r);
        }
    }

    public synchronized void removerReligiao(int religiaoId) {
        Religiao b = getEntidade(Religiao.class, religiaoId);
        removerEntidade(b);
    }

    public Religiao obterReligiaoPorId(int id) {
        return getEntidade(Religiao.class, id);
    }

    public List<Religiao> obterReligioes(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where r.id = " + filtro;
            } else {
                where += "\n where UPPER(r.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(Religiao.class,
                "select r from Religiao r "
                + where
                + "\norder by r.nome", limite
        );

    }

}
