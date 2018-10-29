/*
 * Sistema Eddydata de Gestão e Administração Pública
 * Copyright (C) 2014, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.repositorio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class Repositorio {

    private static final long serialVersionUID = 1L;

    private final EntityManager entityManager;

    public Repositorio(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected <T> T adicionarEntidade(Class<T> classeToCast, Object entidade) {
        getEntityManager().persist(entidade);
        return (T) entidade;
    }

    protected <T> T adicionarEntidade(Class<T> classeToCast, Object entidade, boolean autoFLush) {
        getEntityManager().persist(entidade);
        if (autoFLush) {
            getEntityManager().flush();
        }
        return (T) entidade;
    }

    protected <T> T getEntidade(Class<T> classeToCast, Serializable pk) {
        return getEntityManager().find(classeToCast, pk);
    }

    protected <T> T setEntidade(Class<T> classeToCast, Object entidade) {
        Object obj = getEntityManager().merge(entidade);
        return (T) obj;
    }

    protected <T> T setEntidade(Class<T> classeToCast, Object entidade, boolean autoFLush) {
        Object obj = getEntityManager().merge(entidade);
        if (autoFLush) {
            getEntityManager().flush();
        }
        return (T) obj;
    }

    protected void removerEntidade(Object entidade) {
        Object obj = getEntityManager().merge(entidade);
        getEntityManager().remove(obj);
    }

    protected <T> List<T> getListaPura(Class<T> classeToCast, String query, Object... valores) {
        Query qr = createQuery(query, valores);
        return qr.getResultList();
    }

    protected <T> List<T> getListaPuraComFlush(Class<T> classeToCast, String query, Object... valores) {
        Query qr = createQuery(query, valores);
        entityManager.flush();
        return qr.getResultList();
    }

    protected <T> List<T> getListaPuraLimite(Class<T> classeToCast, String query, Integer limite, Object... valores) {
        Query qr;
        if (limite > 0) {
            qr = createQuery(query, valores).setMaxResults(limite);
        } else {
            qr = createQuery(query, valores);
        }
        return qr.getResultList();
    }

    protected <T> T getEntidadePura(Class<T> classeToCast, String query, Object... valores) {
        Query qr = createQuery(query, valores);
        qr.setMaxResults(1);
        List list = qr.getResultList();
        return list.isEmpty() ? null : (T) list.get(0);
    }

    protected int executeCommand2(String query, Object... valores) {
        Query qr = createQuery(query, valores);
        return qr.executeUpdate();
    }

    protected int executeCommand(String query, Object... valores) {
        Query qr = getEntityManager().createNativeQuery(query);
        for (int i = 0; i < valores.length; i++) {
            qr.setParameter((i + 1), valores[i]);
        }
        int result = qr.executeUpdate();
        return result;
    }

    protected Query createQuery(String query, Object... valores) {
        Query qr = getEntityManager().createQuery(query);
        for (int i = 0; i < valores.length; i++) {
            qr.setParameter((i + 1), valores[i]);
        }
        return qr;
    }

    protected <T> List<T> createNativeQuery(String query) {
        return createNativeQuery(query, false);
    }

    protected <T> List<T> createNativeQuery(String query, boolean flush) {
        Query qr = entityManager.createNativeQuery(query);
        List list = qr.getResultList();
        if (flush) {
            entityManager.flush();
        }
        return list;
    }
}
