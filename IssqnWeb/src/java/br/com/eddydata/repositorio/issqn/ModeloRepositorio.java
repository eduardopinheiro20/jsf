/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Thiago Martos
 */
public class ModeloRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public ModeloRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized Imovel salvarImovel(Imovel c) {
        if (c.getId() == null) {
            return adicionarEntidade(Imovel.class, c);
        } else {
            return setEntidade(Imovel.class, c);
        }
    }

    public synchronized void removerImovel(int categoriaId) {
        Imovel t = getEntidade(Imovel.class, categoriaId);
        removerEntidade(t);
    }

    public Imovel obterImovelPorId(int id) {
        return getEntidade(Imovel.class, id);
    }

    public List<Imovel> obterImoveis(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where c.id = " + filtro;
            } else {
                where += "\n where UPPER(c.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(Imovel.class,
                "select c from Imovel c "
                + where
                + "\norder by c.id", limite
        );

    }

}
