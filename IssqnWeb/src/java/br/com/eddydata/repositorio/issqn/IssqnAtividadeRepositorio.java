/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnCnae;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class IssqnAtividadeRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnAtividadeRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnCnae salvarAtividade(IssqnCnae c) {
        if (c.getId() == null) {
            return adicionarEntidade(IssqnCnae.class, c);
        } else {
            return setEntidade(IssqnCnae.class, c);
        }
    }

    public synchronized void removerAtividade(int atividadeId) {
        IssqnCnae t = getEntidade(IssqnCnae.class, atividadeId);
        removerEntidade(t);
    }

    public IssqnCnae obterAtividadePorId(int id) {
        return getEntidade(IssqnCnae.class, id);
    }

    public List<IssqnCnae> obterAtividades(String filtro, Integer tpAtividade, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where a.codigoCnae like " + Util.quotarStr(filtro + "%") + (tpAtividade == null ? "" : " and a.tpAtividade = " + tpAtividade);
            } else {
                where += "\n where UPPER(function('rem_acento', a.nome)) like " + Util.quotarStr("%" + filtro + "%") + (tpAtividade == null ? "" : " and a.tpAtividade = " + tpAtividade);
            }
        } else {
            where += (tpAtividade == null ? "" : " where a.tpAtividade = " + tpAtividade);
        }

        return getListaPuraLimite(IssqnCnae.class,
                "select a from IssqnCnae a "
                + where
                + "\norder by a.id", limite
        );

    }

    public List<IssqnCnae> obterCnaePorNome(String filtro, Integer limite) {
        String where = "";
        if (!filtro.isEmpty()) {
            where += "\n where UPPER(a.nome) = " + Util.quotarStr(filtro.toUpperCase());
        } 

        return getListaPuraLimite(IssqnCnae.class,
                 "select a from IssqnCnae a "
                + where
                + "\norder by a.id", limite
        );

    }

}
