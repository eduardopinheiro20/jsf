/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_simples_nacional")
public class IssqnSimplesNacional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_simples_nacional")
    private Integer id;
    @Column(name = "dt_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dtPagamento;
    @Column(name = "dt_vencimento")
    @Temporal(TemporalType.DATE)
    private Date dtVencimento;
    @Column(name = "dt_movimento")
    @Temporal(TemporalType.DATE)
    private Date dtMovimento;
    @Column(name = "lote")
    private Integer lote;
    @Column(name = "cnpj", length = 20)
    private String cnpj;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "vl_parcela")
    private Double vlParcela;
    @Column(name = "vl_juros")
    private Double vlJuros;
    @Column(name = "vl_multa")
    private Double vlMulta;
    @Column(name = "vl_total")
    private Double vlTotal;
    @Column(name = "id_banco")
    private Integer idBanco;
    @Column(name = "agencia")
    private Integer agencia;
    @Column(name = "nome_arquivo", length = 50)
    private String nomeArquivo;
    @Column(name = "cod_simples_nacional", length = 20)
    private String codSimplesNacional;

    public IssqnSimplesNacional() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(Date dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Date getDtMovimento() {
        return dtMovimento;
    }

    public void setDtMovimento(Date dtMovimento) {
        this.dtMovimento = dtMovimento;
    }

    public Integer getLote() {
        return lote;
    }

    public void setLote(Integer lote) {
        this.lote = lote;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Double getVlParcela() {
        return vlParcela;
    }

    public void setVlParcela(Double vlParcela) {
        this.vlParcela = vlParcela;
    }

    public Double getVlJuros() {
        return vlJuros;
    }

    public void setVlJuros(Double vlJuros) {
        this.vlJuros = vlJuros;
    }

    public Double getVlMulta() {
        return vlMulta;
    }

    public void setVlMulta(Double vlMulta) {
        this.vlMulta = vlMulta;
    }

    public Double getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(Double vlTotal) {
        this.vlTotal = vlTotal;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCodSimplesNacional() {
        return codSimplesNacional;
    }

    public void setCodSimplesNacional(String codSimplesNacional) {
        this.codSimplesNacional = codSimplesNacional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IssqnSimplesNacional)) {
            return false;
        }
        IssqnSimplesNacional other = (IssqnSimplesNacional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnSimplesNacional[ idSimplesNacional=" + id + " ]";
    }

}
