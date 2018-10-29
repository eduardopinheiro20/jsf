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
public class CancelamentoDTO {

    private String formaPagto;
    private Integer parc;
    private String nossoNumero;
    private Double Valor;
    private Date dtCancelamento;
    private String descricaoParc;
    private String detalheCancelamento;

    public CancelamentoDTO() {
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

    public Date getDtCancelamento() {
        return dtCancelamento;
    }

    public void setDtCancelamento(Date dtCancelamento) {
        this.dtCancelamento = dtCancelamento;
    }

    public String getDescricaoParc() {
        return descricaoParc;
    }

    public void setDescricaoParc(String descricaoParc) {
        this.descricaoParc = descricaoParc;
    }

    public String getDetalheCancelamento() {
        return detalheCancelamento;
    }

    public void setDetalheCancelamento(String detalheCancelamento) {
        this.detalheCancelamento = detalheCancelamento;
    }

    public String getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

}
