/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.dto.issqn;

import java.util.Date;

/**
 *
 * @author eddydata
 */
public class ParcelaDTO {

    private String tipoTaxa;
    private String formaPagto;
    private Integer parc;
    private Double Valor;
    private Date dtVenc;
    private Date dtPagto;
    private Double vlPago;
    private String banco;
    private String lote;
    private String nossoNumero;
    private Double vlTotal;

    public ParcelaDTO() {
    }

    public String getTipoTaxa() {
        return tipoTaxa;
    }

    public void setTipoTaxa(String tipoTaxa) {
        this.tipoTaxa = tipoTaxa;
    }

    public String getFormaPagto() {
        return formaPagto;
    }

    public void setFormaPagto(String formaPagto) {
        this.formaPagto = formaPagto;
    }

    public Integer getParc() {
        return parc;
    }

    public void setParc(Integer parc) {
        this.parc = parc;
    }

    public Double getValor() {
        return Valor;
    }

    public void setValor(Double Valor) {
        this.Valor = Valor;
    }

    public Date getDtVenc() {
        return dtVenc;
    }

    public void setDtVenc(Date dtVenc) {
        this.dtVenc = dtVenc;
    }

    public Date getDtPagto() {
        return dtPagto;
    }

    public void setDtPagto(Date dtPagto) {
        this.dtPagto = dtPagto;
    }

    public Double getVlPago() {
        return vlPago;
    }

    public void setVlPago(Double vlPago) {
        this.vlPago = vlPago;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public Double getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(Double vlTotal) {
        this.vlTotal = vlTotal;
    }

    @Override
    public String toString() {
        return "ParcelaDTO{" + "formaPagto=" + formaPagto + ", parc=" + parc + '}';
    }

}
