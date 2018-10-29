/*
 * Sistema Eddydata de Administração Pública
 * Copyright (C) 2016, Eddydata ltda.
 * Diretors Reservados.
 * @author Rodrigo Teixeira
 **/
package br.com.eddydata.bean;

import br.com.eddydata.entidade.admin.Exercicio;
import br.com.eddydata.entidade.admin.Usuario;
import br.com.eddydata.entidade.issqn.Issqn;
import br.com.eddydata.suporte.Util;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.tempuri.AuthenticationHeader;

@Named(value = "globalBean")
@SessionScoped
public class GlobalBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String maskReceita = "9.9.9.9.99.99.99.99.99";
    private String maskDespesa = "9.9.99.99.99";
    private String maskSubDespesa = "9.9.99.99.99.99";
    private String maskPlano = "9.9.9.9.9.99.99";
    private String dataExtenso;

    private Usuario usuarioLogado;
    private Issqn issqnLogado;
    private Exercicio exercicio;
    private Integer mesReferencia;
    private String referencia;
    private Boolean mostrarAviso = false;

    private String sistema = "";
    private String nomePPA = "";

    private boolean somenteConsulta = false;

    AuthenticationHeader authenticationHeader = null;

    public String getMaskReceita() {
        return maskReceita;
    }

    public void setMaskReceita(String maskReceita) {
        this.maskReceita = maskReceita;
    }

    public String getMaskDespesa() {
        return maskDespesa;
    }

    public void setMaskDespesa(String maskDespesa) {
        this.maskDespesa = maskDespesa;
    }

    public String getMaskSubDespesa() {
        return maskSubDespesa;
    }

    public void setMaskSubDespesa(String maskSubDespesa) {
        this.maskSubDespesa = maskSubDespesa;
    }

    public String getMaskPlano() {
        return maskPlano;
    }

    public void setMaskPlano(String maskPlano) {
        this.maskPlano = maskPlano;
    }

    public String getDataExtenso() {
        return dataExtenso;
    }

    public void setDataExtenso(String dataExtenso) {
        this.dataExtenso = dataExtenso;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getNomePPA() {
        return nomePPA;
    }

    public void setNomePPA(String nomePPA) {
        this.nomePPA = nomePPA;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public Integer getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(Integer mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public Boolean getMostrarAviso() {
        return mostrarAviso;
    }

    public void setMostrarAviso(Boolean mostrarAviso) {
        this.mostrarAviso = mostrarAviso;
    }

    public String getReferencia() {
        referencia = Util.getNameMonth(mesReferencia) + "/" + (exercicio == null ? Util.getYear(new Date()) : exercicio.getAno());
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public AuthenticationHeader getAuthenticationHeader() {
        if (authenticationHeader == null) {
            authenticationHeader = new AuthenticationHeader();
            authenticationHeader.setUsername("ws_americo");
            authenticationHeader.setPassword("123456");
        }
        return authenticationHeader;
    }

    public void setAuthenticationHeader(AuthenticationHeader authenticationHeader) {
        this.authenticationHeader = authenticationHeader;
    }

    public Issqn getIssqnLogado() {
        return issqnLogado;
    }

    public void setIssqnLogado(Issqn issqnLogado) {
        this.issqnLogado = issqnLogado;
    }

    public boolean isSomenteConsulta() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("somenteConsulta")) {
            somenteConsulta = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("somenteConsulta");
        }
        return somenteConsulta;
    }

    public void setSomenteConsulta(boolean somenteConsulta) {
        this.somenteConsulta = somenteConsulta;
    }
}
