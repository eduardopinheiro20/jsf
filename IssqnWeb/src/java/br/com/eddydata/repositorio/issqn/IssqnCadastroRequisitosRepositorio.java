/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnCadastroRequisitos;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author d.morais
 */
public class IssqnCadastroRequisitosRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnCadastroRequisitosRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnCadastroRequisitos salvarCadastroRequisitos(IssqnCadastroRequisitos zona) {
        if (zona.getId() == null) {
            return adicionarEntidade(IssqnCadastroRequisitos.class, zona);
        } else {
            return setEntidade(IssqnCadastroRequisitos.class, zona);
        }
    }

    public synchronized void removerCadastroRequisitos(int zonaId) {
        IssqnCadastroRequisitos orgao = getEntidade(IssqnCadastroRequisitos.class, zonaId);
        removerEntidade(orgao);
    }

    public IssqnCadastroRequisitos obterCadastroRequisitosPorId(int id) {
        return getEntidade(IssqnCadastroRequisitos.class, id);
    }

    public List<IssqnCadastroRequisitos> obterCadastroRequisitos(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where z.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', z.nome)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnCadastroRequisitos.class,
                "select z from IssqnCadastroRequisitos z "
                + where
                + "\norder by z.nome", limite
        );

    }

    

}
