/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.auditoria;


import br.com.eddydata.auditoria.Auditoria;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.Util;
import java.util.List;
import javax.persistence.EntityManager;

public class AuditoriaRepositorio extends Repositorio {
    
    private static final long serialVersionUID = 1L;
    private static final int LIMITE = 1000;
    
    public AuditoriaRepositorio(EntityManager entityManager) {
        super(entityManager);
    }
    
    public List<Auditoria> obterAuditoriasPorCriteria(String sistema, String where, Integer limite) {
        limite = (limite == null ? LIMITE : (limite == 0 ? LIMITE : limite));
        sistema = (sistema == null ? "" : sistema);
        return getListaPuraLimite(Auditoria.class,
                "select a from Auditoria a"
                + "\n " + where
                + (sistema.equals("") ? "" : "\n and a.sistema = " + Util.quotarStr(sistema))
                + "\norder by a.data desc", limite
        );
    }
    
}
