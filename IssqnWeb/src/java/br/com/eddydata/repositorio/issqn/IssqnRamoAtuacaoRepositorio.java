/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnRamoAtuacao;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David'
 */
public class IssqnRamoAtuacaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnRamoAtuacaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnRamoAtuacao salvarRamoAtuacao(IssqnRamoAtuacao a) {
        if (a.getId() == null) {
            return adicionarEntidade(IssqnRamoAtuacao.class, a);
        } else {
            return setEntidade(IssqnRamoAtuacao.class, a);
        }
    }

    public synchronized void removerRamoAtuacao(int ramoAtuacaoId) {
        IssqnRamoAtuacao t = getEntidade(IssqnRamoAtuacao.class, ramoAtuacaoId);
        removerEntidade(t);
    }

    public IssqnRamoAtuacao obterRamoAtuacaoPorId(int id) {
        return getEntidade(IssqnRamoAtuacao.class, id);
    }

    public List<IssqnRamoAtuacao> obterRamosAtuacao(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where a.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', a.nome)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnRamoAtuacao.class,
                "select a from IssqnRamoAtuacao a "
                + where
                + "\norder by a.id", limite
        );

    }

}
