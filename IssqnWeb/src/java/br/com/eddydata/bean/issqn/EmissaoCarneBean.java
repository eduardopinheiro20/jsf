/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 */
package br.com.eddydata.bean.issqn;


import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;



import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@ViewScoped
public class EmissaoCarneBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int order = 0;
    private int opcao = 0;
    private Date dtVencimento = null;
    private String inscricao = "";

    @PostConstruct
    public void init() {

    }

    /*
    public String gerarArquivo() {
        return "pretty:arquivo";
    }
    */
    
    
    public StreamedContent gerarArquivo() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/anexo/carne.txt");
        return new DefaultStreamedContent(stream, "text/plain", "carne.txt");

    }
    


    public void Navegar() {


    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

  
}
