/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.repositorio.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.issqn.IssqnZonaMunicipio;
import br.com.eddydata.repositorio.Repositorio;
import br.com.eddydata.suporte.ReportGenerator;
import br.com.eddydata.suporte.Util;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.ImageIcon;

/**
 *
 * @author d.morais
 */
public class IssqnZonaMunicipioRepositorio extends Repositorio {

    private static final long serialVersionUID = 1L;

    public IssqnZonaMunicipioRepositorio(EntityManager entityManager) {
        super(entityManager);
    }

    public synchronized IssqnZonaMunicipio salvarZonaMunicipio(IssqnZonaMunicipio zona) {
        if (zona.getId() == null) {
            return adicionarEntidade(IssqnZonaMunicipio.class, zona);
        } else {
            return setEntidade(IssqnZonaMunicipio.class, zona);
        }
    }

    public synchronized void removerZonaMunicipio(int zonaId) {
        IssqnZonaMunicipio orgao = getEntidade(IssqnZonaMunicipio.class, zonaId);
        removerEntidade(orgao);
    }

    public IssqnZonaMunicipio obterZonaMunicipioPorId(int id) {
        return getEntidade(IssqnZonaMunicipio.class, id);
    }

    public List<IssqnZonaMunicipio> obterZonaMunicipio(String filtro, Integer limite) {
        String where = "";
        if (!filtro.equals("")) {
            if (Util.isNumeric(filtro)) {
                where += "\n where z.id = " + filtro;
            } else {
                where += "\n where UPPER(function('rem_acento', z.nome)) like " + Util.quotarStr("%" + filtro + "%");
            }
        }

        return getListaPuraLimite(IssqnZonaMunicipio.class,
                "select z from IssqnZonaMunicipio z "
                + where
                + "\norder by z.nome", limite
        );

    }

    

}
