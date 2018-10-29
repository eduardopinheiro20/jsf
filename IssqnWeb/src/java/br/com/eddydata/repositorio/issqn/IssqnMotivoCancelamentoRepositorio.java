/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnMotivoCancel;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class IssqnMotivoCancelamentoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnMotivoCancelamentoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnMotivoCancel salvarMotivoCancelamento(IssqnMotivoCancel c) {
        if (c.getId() == null) {
            return adicionarEntidade(IssqnMotivoCancel.class, c);
        } else {
            return setEntidade(IssqnMotivoCancel.class, c);
        }
    }

    public synchronized void removerMotivoCancelamento(int motivoId) {
        IssqnMotivoCancel c = getEntidade(IssqnMotivoCancel.class, motivoId);
        removerEntidade(c);
    }

    public IssqnMotivoCancel obterMotivoCancelamentoPorId(int id) {
        return getEntidade(IssqnMotivoCancel.class, id);
    }

    public List<IssqnMotivoCancel> obterMotivosCancelamento(String filtro, Integer limite) {
        String where = "";
        if (!filtro.isEmpty()) {
            if (Util.isNumeric(filtro)) {
                where += "\n where c.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', c.descricao)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnMotivoCancel.class,
                "select c from IssqnMotivoCancel c "
                + where
                + "\norder by c.id", limite
        );

    }

}
