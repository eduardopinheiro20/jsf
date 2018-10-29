/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.admin;

import br.com.eddydata.entidade.admin.Exercicio;
import br.com.eddydata.repositorio.Repositorio;
import java.util.List;
import javax.persistence.EntityManager;

public class ExercicioRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public ExercicioRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public Exercicio getExercicioPorId(int id) {
        return getEntidade(Exercicio.class, id);
    }

    public Exercicio setExercicio(Exercicio e) {
        return setEntidade(Exercicio.class, e);
    }

    public Exercicio adicionarExercicio(Exercicio e) {
        adicionarEntidade(Exercicio.class, e);
        return e;
    }

    public void removerExercicio(Exercicio e) {
        removerEntidade(e);
    }

    public Exercicio getExercicioPorAno(Integer ano, String orgaoId) {
        return getEntidadePura(Exercicio.class, "select e "
                + "\nfrom Exercicio e "
                + "\njoin e.orgao o"
                + "\nwhere e.ano = ?1 and o.idOrgao = ?2", ano, orgaoId);
    }

    public List<Exercicio> getExercicios() {
        return getListaPura(Exercicio.class, "select e from Exercicio e order by e.ano desc");
    }

}
