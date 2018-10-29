/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnSocio;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class IssqnSocioRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnSocioRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnSocio salvarSocio(IssqnSocio s) {
        if (s.getId() == null) {
            return adicionarEntidade(IssqnSocio.class, s);
        } else {
            return setEntidade(IssqnSocio.class, s);
        }
    }

    public synchronized void removerSocio(int socioId) {
        IssqnSocio s = getEntidade(IssqnSocio.class, socioId);
        removerEntidade(s);
    }

    public IssqnSocio obterSocioPorId(int id) {
        return getEntidade(IssqnSocio.class, id);
    }

    public List<IssqnSocio> obterSocioPorContribuinte(Integer idContribuinte) {
        return getListaPura(IssqnSocio.class,
                "select s from IssqnSocio s"
                + "\njoin s.iss i"
                + "\nwhere i.id = ?1"
                + "\norder by s.id", idContribuinte);

    }

    public List<IssqnSocio> getSocioPorNome(String nome) {
        return getListaPura(IssqnSocio.class,
                "select s from IssqnSocio s"
                + "\njoin s.pessoa p"
                + "\nwhere UPPER(p.nome) LIKE ?1"
                + "\norder by p.nome", nome.toUpperCase() + "%");
    }
    
}
