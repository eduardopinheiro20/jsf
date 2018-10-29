/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.bean.issqn;

import br.com.eddydata.bean.Funcao;
import br.com.eddydata.entidade.issqn.IssqnHistoricoTaxa;
import br.com.eddydata.servico.issqn.IssqnHistoricoTaxaServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Joylson T
 */
@Named
@ViewScoped
public class HistoricoTaxaBean implements Serializable {

    private List<IssqnHistoricoTaxa> listHistorico;

    @EJB
    private IssqnHistoricoTaxaServico hitoricoTaxaServico;

    @PostConstruct
    public void init() {
        if (listHistorico == null) {
            carregarListagem();
        }
    }

    private void carregarListagem() {
        try {
            listHistorico = new ArrayList();
            listHistorico = hitoricoTaxaServico.obterHistoricoTaxas(null, null);
        } catch (Exception ex) {
            Funcao.avisoErro("Erro ao buscar registros");
            System.out.println("Erro ao buscar registros\n" + ex.getMessage());
        }
    }

    public List<IssqnHistoricoTaxa> getListHistorico() {
        return listHistorico;
    }

    public void setListHistorico(List<IssqnHistoricoTaxa> listHistorico) {
        this.listHistorico = listHistorico;
    }

    public IssqnHistoricoTaxaServico getHitoricoTaxaServico() {
        return hitoricoTaxaServico;
    }

    public void setHitoricoTaxaServico(IssqnHistoricoTaxaServico hitoricoTaxaServico) {
        this.hitoricoTaxaServico = hitoricoTaxaServico;
    }

}
