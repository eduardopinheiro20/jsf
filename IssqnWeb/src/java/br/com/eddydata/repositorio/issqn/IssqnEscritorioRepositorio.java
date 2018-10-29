/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnEscritorio;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class IssqnEscritorioRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnEscritorioRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnEscritorio salvarEscritorio(IssqnEscritorio e) {
        if (e.getId() == null) {
            return adicionarEntidade(IssqnEscritorio.class, e);
        } else {
            return setEntidade(IssqnEscritorio.class, e);
        }
    }

    public synchronized void removerEscritorio(int escritorioId) {
        IssqnEscritorio t = getEntidade(IssqnEscritorio.class, escritorioId);
        removerEntidade(t);
    }

    public IssqnEscritorio obterEscritorioPorId(int id) {
        return getEntidade(IssqnEscritorio.class, id);
    }

    public List<IssqnEscritorio> obterEscritorios(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where e.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', e.nomeFantasia)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnEscritorio.class,
                "select e from IssqnEscritorio e "
                + where
                + "\norder by e.id", limite
        );

    }

}
