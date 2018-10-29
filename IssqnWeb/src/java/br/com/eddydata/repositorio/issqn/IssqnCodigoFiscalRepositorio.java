/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnCodigoFiscal;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class IssqnCodigoFiscalRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnCodigoFiscalRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnCodigoFiscal salvarCodigoFiscal(IssqnCodigoFiscal f) {
        if (f.getId() == null) {
            return adicionarEntidade(IssqnCodigoFiscal.class, f);
        } else {
            return setEntidade(IssqnCodigoFiscal.class, f);
        }
    }

    public synchronized void removerCodigoFiscal(int codigoFiscalId) {
        IssqnCodigoFiscal t = getEntidade(IssqnCodigoFiscal.class, codigoFiscalId);
        removerEntidade(t);
    }

    public IssqnCodigoFiscal obterCodigoFiscalPorId(int id) {
        return getEntidade(IssqnCodigoFiscal.class, id);
    }

    public List<IssqnCodigoFiscal> obterCodigosFiscais(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where f.id = " + filtro;
            } else {
                where += "\n where UPPER(f.codigoFiscal) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnCodigoFiscal.class,
                "select f from IssqnCodigoFiscal f "
                + where
                + "\norder by f.id", limite
        );

    }

}
