/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.issqn.IssqnTipoCobranca;
import br.com.eddydata.repositorio.Repositorio;
import java.util.*;
import javax.persistence.EntityManager;

public class TipoCobrancaRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public TipoCobrancaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public IssqnTipoCobranca getTipoCobrancaPorId(int id) {
        return getEntidade(IssqnTipoCobranca.class, id);
    }

    public IssqnTipoCobranca getTipoCobrancaPorNome(String nome) {
        List<IssqnTipoCobranca> tp = new ArrayList<>();
        tp = getListaPura(IssqnTipoCobranca.class, "select c from IssqnTipoCobranca c "
                + "\n where c.descricao like ?1 order by c.id", "%" + nome.trim().toUpperCase() + "%");
        return tp.get(0);
    }

    public IssqnTipoCobranca adicionarTipoCobranca(IssqnTipoCobranca c) throws RuntimeException {
        validarTipoCobranca(c);
        adicionarEntidade(IssqnTipoCobranca.class, c);
        return c;
    }

    public IssqnTipoCobranca setTipoCobranca(IssqnTipoCobranca c) {
        return setEntidade(IssqnTipoCobranca.class, c);
    }

    public void removerTipoCobranca(IssqnTipoCobranca c) {
        removerEntidade(c);
    }

    public List<IssqnTipoCobranca> getTodosCobrancas(Integer exercicio) {
        return getListaPura(IssqnTipoCobranca.class, "select c from IssqnTipoCobranca c "
                + "\n where c.idExercicio = ?1 order by c.id", exercicio);
    }

    private void validarTipoCobranca(IssqnTipoCobranca r) {
        boolean valid = true;
        String msg = "";
        if (r.getDescricao() == null || "".equals(r.getDescricao())) {
            valid = false;
            msg = "É necessário informar a descrição!";
        }
    }
}
