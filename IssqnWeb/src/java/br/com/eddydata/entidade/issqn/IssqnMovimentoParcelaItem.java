/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_movimento_parcela_item")
public class IssqnMovimentoParcelaItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimento_parcela_item")
    private Integer id;
    @JoinColumn(name = "id_movimento_parcela", referencedColumnName = "id_movimento_parcela")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnMovimentoParcela parcela;
    @Column(name = "id_taxa", length = 255)
    private Integer idTaxa;
    @Column(name = "vl_taxa")
    private Double vlTaxa;
    @Column(name = "vl_desconto")
    private Double vlDesconto;
    @Column(name = "vl_pago")
    private Double vlPago;

    public IssqnMovimentoParcelaItem() {
    }

    public IssqnMovimentoParcelaItem(Integer idMovimentoParcelaItem) {
        this.id = idMovimentoParcelaItem;
    }

    public Integer getIdTaxa() {
        return idTaxa;
    }

    public void setIdTaxa(Integer idTaxa) {
        this.idTaxa = idTaxa;
    }

    public Double getVlTaxa() {
        return vlTaxa;
    }

    public void setVlTaxa(Double vlTaxa) {
        this.vlTaxa = vlTaxa;
    }

    public Double getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(Double vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public Double getVlPago() {
        return vlPago;
    }

    public void setVlPago(Double vlPago) {
        this.vlPago = vlPago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public IssqnMovimentoParcela getParcela() {
        return parcela;
    }

    public void setParcela(IssqnMovimentoParcela parcela) {
        this.parcela = parcela;
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
        if (!(object instanceof IssqnMovimentoParcelaItem)) {
            return false;
        }
        IssqnMovimentoParcelaItem other = (IssqnMovimentoParcelaItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnMovimentoParcelaItem[ idMovimentoParcelaItem=" + id + " ]";
    }

}
