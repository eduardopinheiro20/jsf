/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

public class CidadeRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public CidadeRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public Cidade getCidade(int id) {
        return getEntidade(Cidade.class, id);
    }

    public Cidade setCidade(Cidade c) {
        return setEntidade(Cidade.class, c);
    }

    public Cidade adicionarCidade(Cidade c) {
        if (c.getIdCidade() == null) {
            adicionarEntidade(Cidade.class, c);
        } else {
            setEntidade(Cidade.class, c);
        }
        return c;
    }

    public void removerCidade(Cidade c) {
        removerEntidade(c);
    }

    public Cidade getCidadePorId(int id) {
        return getEntityManager().find(Cidade.class, id);
    }

    public List<Cidade> getCidades() {
        return getListaPura(Cidade.class, "select c from Cidade c order by c.nome");
    }

    public List<Cidade> getCidadePorNome(String nome) {
        return getListaPura(Cidade.class, "select c from Cidade c where upper(c.nome) like ?1 order by c.nome", Util.Texto.removeAcentos(nome).toUpperCase());
    }

    public List<Cidade> getCidadePorNomeEstado(String nome, int estado) {
        return getListaPura(Cidade.class, "select c "
                + "\nfrom Cidade c "
                + "\nwhere upper(c.nome) like ?1 and c.estado.id = ?2 order by c.nome", Util.Texto.removeAcentos(nome).toUpperCase(), estado);
    }

    public List<Cidade> getCidadePorCep(String cep) {
        return getListaPura(Cidade.class, "select distinct br.cidade from BairroRua br where br.cep = ?1", cep.replace("-", ""));
    }

    public List<Cidade> obterCidadesPorEstado(Integer estadoId) {
        return getListaPura(Cidade.class,
                "select c from Cidade c "
                + "\nwhere c.estado.id = ?1"
                + "\norder by c.nome", estadoId
        );
    }
}
