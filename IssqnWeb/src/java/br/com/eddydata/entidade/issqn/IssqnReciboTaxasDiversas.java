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
@Table(name = "issqn_recibo_taxas_diversas")
public class IssqnReciboTaxasDiversas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recibo")
    private Integer id;

    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;
    @JoinColumn(name = "id_taxa_diversa", referencedColumnName = "id_taxa_diversa")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnTaxaDiversa taxaDiversa;

    public IssqnReciboTaxasDiversas() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
    }

    public IssqnTaxaDiversa getTaxaDiversa() {
        return taxaDiversa;
    }

    public void setTaxaDiversa(IssqnTaxaDiversa taxaDiversa) {
        this.taxaDiversa = taxaDiversa;
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
        if (!(object instanceof IssqnReciboTaxasDiversas)) {
            return false;
        }
        IssqnReciboTaxasDiversas other = (IssqnReciboTaxasDiversas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnReciboTaxasDiversas[ idRecibo=" + id + " ]";
    }

}
