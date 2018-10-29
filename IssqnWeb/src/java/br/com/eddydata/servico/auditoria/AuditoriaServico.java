/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.servico.auditoria;

import br.com.eddydata.auditoria.Auditoria;
import br.com.eddydata.repositorio.auditoria.AuditoriaRepositorio;
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
public class AuditoriaServico extends Servico {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    private AuditoriaRepositorio repositorio;

    @PostConstruct
    @PostActivate
    private void aposConstruir() {
        repositorio = new AuditoriaRepositorio(em);
    }

    /**
     * <pre>busca auditorias por criteria</pre>
     *
     * @param sistema
     * @param where criteria (alias 'a')
     * @param limite default 1000
     * @return
     */
    public List<Auditoria> obterAuditoriasPorCriteria(String sistema, String where, Integer limite) {
        return repositorio.obterAuditoriasPorCriteria(sistema, where, limite);
    }
}
