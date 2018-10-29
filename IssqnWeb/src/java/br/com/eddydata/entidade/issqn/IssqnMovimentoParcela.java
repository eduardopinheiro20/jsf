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
@Table(name = "issqn_movimento_parcela")
public class IssqnMovimentoParcela implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimento_parcela")
    private Integer id;
    @JoinColumn(name = "id_movimento", referencedColumnName = "id_movimento")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnMovimento movimento;
    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;
    @Column(name = "vl_total")
    private Double vlTotal;
    @Column(name = "vl_desconto")
    private Double vlDesconto;
    @Column(name = "parcela")
    private Integer parcela;
    @Column(name = "vl_parcela")
    private Double vlParcela;
    @Column(name = "vl_multa")
    private Double vlMulta;
    @Column(name = "vl_juros")
    private Double vlJuros;
    @Column(name = "id_banco")
    private Integer idBanco;
    @Column(name = "agencia", length = 8)
    private String agencia;
    @Column(name = "dt_vencimento")
    @Temporal(TemporalType.DATE)
    private Date dtVencimento;
    @Column(name = "dt_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dtPagamento;
    @Column(name = "dt_baixa")
    @Temporal(TemporalType.DATE)
    private Date dtBaixa;
    @Column(name = "lote")
    private Integer lote;
    @Column(name = "divida")
    private Integer divida;
    @Column(name = "arrecadado")
    private Integer arrecadado;
    @Column(name = "cancelado_pagamento")
    private Integer canceladoPagamento;
    @Column(name = "dt_cancelado_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dtCanceladoPagamento;
    @Column(name = "id_motivo_cancel")
    private Integer idMotivoCancel;
    @Column(name = "id_exercicio")
    private Integer idExercicio;
    @Column(name = "vl_pago")
    private Double vlPago;
    @Column(name = "obs_cancel", length = 1000)
    private String obsCancel;
    @Column(name = "nosso_numero")
    private Long nossoNumero;
    @Column(name = "id_situacao_parcela_baixada")
    private Integer idSituacaoParcelaBaixada;
    @Column(name = "item_baixa")
    private Integer itemBaixa;
    @Column(name = "vl_expediente")
    private Double vlExpediente;
    @Column(name = "status")
    private Integer status;
    @Column(name = "dt_banco")
    @Temporal(TemporalType.DATE)
    private Date dtBanco;
    @Column(name = "nosso_numero_agrupado")
    private Integer nossoNumeroAgrupado;
    @Column(name = "is_baixa_agrupado")
    private Integer isBaixaAgrupado;

    @JoinColumn(name = "id_tipo_cobranca", referencedColumnName = "id_tipo_cobranca")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnTipoCobranca idTipoCobranca;

    public IssqnMovimentoParcela() {
    }

    public IssqnMovimentoParcela(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(Double vlTotal) {
        this.vlTotal = vlTotal;
    }

    public Double getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(Double vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    public Double getVlParcela() {
        return vlParcela;
    }

    public void setVlParcela(Double vlParcela) {
        this.vlParcela = vlParcela;
    }

    public Double getVlMulta() {
        return vlMulta;
    }

    public void setVlMulta(Double vlMulta) {
        this.vlMulta = vlMulta;
    }

    public Double getVlJuros() {
        return vlJuros;
    }

    public void setVlJuros(Double vlJuros) {
        this.vlJuros = vlJuros;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Date getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(Date dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public Date getDtBaixa() {
        return dtBaixa;
    }

    public void setDtBaixa(Date dtBaixa) {
        this.dtBaixa = dtBaixa;
    }

    public Integer getLote() {
        return lote;
    }

    public void setLote(Integer lote) {
        this.lote = lote;
    }

    public Integer getDivida() {
        return divida;
    }

    public void setDivida(Integer divida) {
        this.divida = divida;
    }

    public Integer getArrecadado() {
        return arrecadado;
    }

    public void setArrecadado(Integer arrecadado) {
        this.arrecadado = arrecadado;
    }

    public Integer getCanceladoPagamento() {
        return canceladoPagamento;
    }

    public void setCanceladoPagamento(Integer canceladoPagamento) {
        this.canceladoPagamento = canceladoPagamento;
    }

    public Date getDtCanceladoPagamento() {
        return dtCanceladoPagamento;
    }

    public void setDtCanceladoPagamento(Date dtCanceladoPagamento) {
        this.dtCanceladoPagamento = dtCanceladoPagamento;
    }

    public Integer getIdMotivoCancel() {
        return idMotivoCancel;
    }

    public void setIdMotivoCancel(Integer idMotivoCancel) {
        this.idMotivoCancel = idMotivoCancel;
    }

    public IssqnTipoCobranca getIdTipoCobranca() {
        return idTipoCobranca;
    }

    public void setIdTipoCobranca(IssqnTipoCobranca idTipoCobranca) {
        this.idTipoCobranca = idTipoCobranca;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public Double getVlPago() {
        return vlPago;
    }

    public void setVlPago(Double vlPago) {
        this.vlPago = vlPago;
    }

    public String getObsCancel() {
        return obsCancel;
    }

    public void setObsCancel(String obsCancel) {
        this.obsCancel = obsCancel;
    }

    public Long getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(Long nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public Integer getIdSituacaoParcelaBaixada() {
        return idSituacaoParcelaBaixada;
    }

    public void setIdSituacaoParcelaBaixada(Integer idSituacaoParcelaBaixada) {
        this.idSituacaoParcelaBaixada = idSituacaoParcelaBaixada;
    }

    public Integer getItemBaixa() {
        return itemBaixa;
    }

    public void setItemBaixa(Integer itemBaixa) {
        this.itemBaixa = itemBaixa;
    }

    public Double getVlExpediente() {
        return vlExpediente;
    }

    public void setVlExpediente(Double vlExpediente) {
        this.vlExpediente = vlExpediente;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDtBanco() {
        return dtBanco;
    }

    public void setDtBanco(Date dtBanco) {
        this.dtBanco = dtBanco;
    }

    public Integer getNossoNumeroAgrupado() {
        return nossoNumeroAgrupado;
    }

    public void setNossoNumeroAgrupado(Integer nossoNumeroAgrupado) {
        this.nossoNumeroAgrupado = nossoNumeroAgrupado;
    }

    public Integer getIsBaixaAgrupado() {
        return isBaixaAgrupado;
    }

    public void setIsBaixaAgrupado(Integer isBaixaAgrupado) {
        this.isBaixaAgrupado = isBaixaAgrupado;
    }

    public IssqnMovimento getMovimento() {
        return movimento;
    }

    public void setMovimento(IssqnMovimento movimento) {
        this.movimento = movimento;
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
        if (!(object instanceof IssqnMovimentoParcela)) {
            return false;
        }
        IssqnMovimentoParcela other = (IssqnMovimentoParcela) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnMovimentoParcela[ idMovimentoParcela=" + id + " ]";
    }

}
