/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnOrgaoFiscalizacao;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.ImageIcon;

/**
 *
 * @author d.morais
 */
public class IssqnOrgaoFiscalizacaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnOrgaoFiscalizacaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnOrgaoFiscalizacao salvarOrgao(IssqnOrgaoFiscalizacao orgao) {
        if (orgao.getId() == null) {
            return adicionarEntidade(IssqnOrgaoFiscalizacao.class, orgao);
        } else {
            return setEntidade(IssqnOrgaoFiscalizacao.class, orgao);
        }
    }

    public synchronized void removerOrgaoFiscalizacao(int orgaoId) {
        IssqnOrgaoFiscalizacao orgao = getEntidade(IssqnOrgaoFiscalizacao.class, orgaoId);
        removerEntidade(orgao);
    }

    public IssqnOrgaoFiscalizacao obterOrgaoFiscalizacaoPorId(int id) {
        return getEntidade(IssqnOrgaoFiscalizacao.class, id);
    }

    public List<IssqnOrgaoFiscalizacao> obterOrgaoFiscalizacao(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where o.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', o.nome)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnOrgaoFiscalizacao.class,
                "select o from IssqnOrgaoFiscalizacao o "
                + where
                + "\norder by o.nome", limite
        );

    }

    

}
