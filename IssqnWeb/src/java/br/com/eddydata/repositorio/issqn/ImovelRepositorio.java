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
public class ImovelRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public ImovelRepositorio(EntityManager entityManager) {
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

    public List<Imovel> obterImovelPorNome(String filtro,String filtro2,String complemento, Integer limite) {
        String where = "";
        if (!filtro.isEmpty() && !filtro2.isEmpty()) {
            where += "\n where UPPER(c.bairroLogradouro.bairro.nome) = " + Util.quotarStr(filtro) + " and c.nrImovel = " + filtro2;
        }
        if(!complemento.isEmpty()){
            where += "\n and c.complemento = " + Util.quotarStr(complemento);
        }
        return getListaPuraLimite(Imovel.class,
                "select c from Imovel c "
                + where
                + "\norder by c.id", limite);
    }

    public List<Imovel> obterImoveis(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where c.id = " + filtro;
            } else {
                where += "\n where UPPER(c.bairroLogradouro.bairro.nome) like " + Util.quotarStr(filtro + "%");
            }
        }

        return getListaPuraLimite(Imovel.class,
                "select c from Imovel c "
                + where
                + "\norder by c.id", limite
        );

    }

    public boolean verificarExistenciaImovel(Integer bairroLogradouroId, Integer nrImovel, String complemento) {
        if (!complemento.equals("")) {
            complemento = "\n and UPPER(i.complemento) = " + Util.quotarStr(complemento);
        }
        Long count = getEntidadePura(Long.class,
                "select count(i.id) from Imovel i"
                + "\n join i.bairroLogradouro bl"
                + "\nwhere bl.id = ?1 and i.nrImovel = ?2"
                + complemento, bairroLogradouroId, nrImovel
        );

        return (count > 0);
    }

}
