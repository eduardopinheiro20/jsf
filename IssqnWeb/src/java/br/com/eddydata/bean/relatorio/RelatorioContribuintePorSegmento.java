/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.bean.relatorio;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.OrgaoBean;
import br.com.eddydata.servico.issqn.IssqnServico;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author d.morais
 */
@Named
@ViewScoped
public class RelatorioContribuintePorSegmento implements Serializable{

    @EJB
    private IssqnServico servico;

    @Inject
    private GlobalBean global;

    @Inject
    private OrgaoBean orgao;

    private static final long serialVersionUID = 1L;
    private String ordem = "2";

    @PostConstruct
    public void init() {
    }

    public void imprimir() {
        try {
            servico.imprimirContribuintePorSegmento(global.getUsuarioLogado().getOrgao().getIdOrgao(), global.getExercicio().getAno(), ordem, global.getUsuarioLogado().getNome());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }
}
