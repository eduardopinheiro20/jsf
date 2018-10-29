/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geral.Profissao;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

public class ProfissaoRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public ProfissaoRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized Profissao salvarProfissao(Profissao p) {
        if (p.getId() == null) {
            return adicionarEntidade(Profissao.class, p);
        } else {
            return setEntidade(Profissao.class, p);
        }
    }

    public synchronized void removerProfissao(int profissaoId) {
        Profissao b = getEntidade(Profissao.class, profissaoId);
        removerEntidade(b);
    }

    public Profissao obterProfissaoPorId(int id) {
        return getEntidade(Profissao.class, id);
    }

    public List<Profissao> obterProfissoes(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where p.id = " + filtro;
            } else {
                where += "\n where UPPER(p.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(Profissao.class,
                "select p from Profissao p "
                + where
                + "\norder by p.nome", limite
        );

    }

}
