/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnSegmento;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David'
 */
public class IssqnSegmentoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnSegmentoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnSegmento salvarSegmento(IssqnSegmento a) {
        if (a.getId() == null) {
            return adicionarEntidade(IssqnSegmento.class, a);
        } else {
            return setEntidade(IssqnSegmento.class, a);
        }
    }

    public synchronized void removerSegmento(int segmentoId) {
        IssqnSegmento t = getEntidade(IssqnSegmento.class, segmentoId);
        removerEntidade(t);
    }

    public IssqnSegmento obterSegmentoPorId(int id) {
        return getEntidade(IssqnSegmento.class, id);
    }

    public List<IssqnSegmento> obterSegmento(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where a.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', a.nome)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnSegmento.class,
                "select a from IssqnSegmento a "
                + where
                + "\norder by a.id", limite
        );

    }

}
