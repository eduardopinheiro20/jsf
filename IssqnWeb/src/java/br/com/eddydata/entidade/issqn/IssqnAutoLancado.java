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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_auto_lancado")
public class IssqnAutoLancado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lancamento")
    private Integer id;
    @Column(name = "inscricao", length = 30)
    private String inscricao;
    @Column(name = "mes_ref")
    private Integer mesRef;
    @Column(name = "ano_ref")
    private Integer anoRef;
    @Column(name = "notas_emitidas", length = 50)
    private String notasEmitidas;
    @Column(name = "base_calculo")
    private Double baseCalculo;
    @Column(name = "aliquota")
    private Double aliquota;
    @Column(name = "valor")
    private Double valor;
    @Column(name = "juros")
    private Double juros;
    @Column(name = "multa")
    private Double multa;
    @Column(name = "valor_total")
    private Double valorTotal;
    @Column(name = "dt_vencimento")
    @Temporal(TemporalType.DATE)
    private Date dtVencimento;
    @Column(name = "lote")
    private Integer lote;
    @Column(name = "dt_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dtPagamento;
    @Column(name = "id_banco")
    private Integer idBanco;
    @Column(name = "vl_pago")
    private Double vlPago;
    @Column(name = "tp_arrecadacao")
    private Integer tpArrecadacao;
    @Column(name = "id_situacao_parcela_baixada")
    private Integer idSituacaoParcelaBaixada;
    @Column(name = "observacao",length = 200)
    private String observacao;
    @Column(name = "dt_cancelamento")
    @Temporal(TemporalType.DATE)
    private Date dtCancelamento;
    @Column(name = "nosso_numero")
    private Integer nossoNumero;
    @Column(name = "dt_baixa")
    @Temporal(TemporalType.DATE)
    private Date dtBaixa;
    @Column(name = "item_baixa")
    private Integer itemBaixa;
    @Column(name = "vl_juros_baixa")
    private Double vlJurosBaixa;
    
    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Issqn iss;

    public IssqnAutoLancado() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public Integer getMesRef() {
        return mesRef;
    }

    public void setMesRef(Integer mesRef) {
        this.mesRef = mesRef;
    }

    public Integer getAnoRef() {
        return anoRef;
    }

    public void setAnoRef(Integer anoRef) {
        this.anoRef = anoRef;
    }

    public String getNotasEmitidas() {
        return notasEmitidas;
    }

    public void setNotasEmitidas(String notasEmitidas) {
        this.notasEmitidas = notasEmitidas;
    }

    public Double getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(Double baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public Double getAliquota() {
        return aliquota;
    }

    public void setAliquota(Double aliquota) {
        this.aliquota = aliquota;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getJuros() {
        return juros;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Integer getLote() {
        return lote;
    }

    public void setLote(Integer lote) {
        this.lote = lote;
    }

    public Date getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(Date dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public Double getVlPago() {
        return vlPago;
    }

    public void setVlPago(Double vlPago) {
        this.vlPago = vlPago;
    }

    public Integer getTpArrecadacao() {
        return tpArrecadacao;
    }

    public void setTpArrecadacao(Integer tpArrecadacao) {
        this.tpArrecadacao = tpArrecadacao;
    }

    public Integer getIdSituacaoParcelaBaixada() {
        return idSituacaoParcelaBaixada;
    }

    public void setIdSituacaoParcelaBaixada(Integer idSituacaoParcelaBaixada) {
        this.idSituacaoParcelaBaixada = idSituacaoParcelaBaixada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDtCancelamento() {
        return dtCancelamento;
    }

    public void setDtCancelamento(Date dtCancelamento) {
        this.dtCancelamento = dtCancelamento;
    }

    public Integer getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(Integer nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public Date getDtBaixa() {
        return dtBaixa;
    }

    public void setDtBaixa(Date dtBaixa) {
        this.dtBaixa = dtBaixa;
    }

    public Integer getItemBaixa() {
        return itemBaixa;
    }

    public void setItemBaixa(Integer itemBaixa) {
        this.itemBaixa = itemBaixa;
    }

    public Double getVlJurosBaixa() {
        return vlJurosBaixa;
    }

    public void setVlJurosBaixa(Double vlJurosBaixa) {
        this.vlJurosBaixa = vlJurosBaixa;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
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
        if (!(object instanceof IssqnAutoLancado)) {
            return false;
        }
        IssqnAutoLancado other = (IssqnAutoLancado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnAutoLancado[ idLancamento=" + id + " ]";
    }
    
}
