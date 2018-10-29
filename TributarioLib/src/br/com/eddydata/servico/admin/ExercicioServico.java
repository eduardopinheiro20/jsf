/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.servico.admin;

import br.com.eddydata.entidade.admin.Exercicio;
import br.com.eddydata.repositorio.admin.ExercicioRepositorio;
import br.com.eddydata.servico.Servico;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ExercicioServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private ExercicioRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new ExercicioRepositorio(em);
    }

    public ExercicioServico() {

    }

    public Exercicio getExercicioPorId(int id) {
        return repositorio.getExercicioPorId(id);
    }

    public Exercicio setExercicio(Exercicio u) {
        return repositorio.setExercicio(u);
    }

    public void removerExercicio(Exercicio u) {
        repositorio.removerExercicio(u);
    }

    public Exercicio adicionarExercicio(Exercicio u) {
        return repositorio.adicionarExercicio(u);
    }

    public Exercicio getExercicioPorAno(Integer ano, String orgaoId) {
        try {
            return repositorio.getExercicioPorAno(ano, orgaoId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Exercicio> getExercicios() {
        return repositorio.getExercicios();
    }

}
