/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.bean.relatorio;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.bean.GlobalBean;
import br.com.eddydata.bean.OrgaoBean;
import br.com.eddydata.entidade.issqn.IssqnNotificacao;
import br.com.eddydata.servico.issqn.IssqnNotificacaoServico;
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
public class RelatorioAlertaNotificacaoBean implements Serializable{

    @EJB
    private IssqnNotificacaoServico servico;

    @Inject
    private GlobalBean global;

    @Inject
    private OrgaoBean orgao;

    private static final long serialVersionUID = 1L;
    private Date dataInicial = null;
    private Date dataFinal = null;
    private String filtro = null;
 

    @PostConstruct
    public void init() {

    }

    public void imprimir() {
        try {
            servico.imprimirAlertaNotificacao(dataInicial, dataFinal, global.getUsuarioLogado().getOrgao().getIdOrgao());
        } catch (Exception ex) {
            Funcao.avisoErro("Não foi possível fazer a impressão!");
            System.out.println(ex.getMessage());
        }
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }


    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }
    
    

    
}
