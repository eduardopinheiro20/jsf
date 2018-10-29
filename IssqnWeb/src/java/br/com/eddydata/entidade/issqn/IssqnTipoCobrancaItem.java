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
@Table(name = "issqn_tipo_cobranca_item")
public class IssqnTipoCobrancaItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cobranca_item")
    private Integer id;
    @Column(name = "parcela")
    private Integer parcela;
    @Column(name = "dt_vencimento")
    @Temporal(TemporalType.DATE)
    private Date dtVencimento;
    @Column(name = "id_exercicio")
    private Integer idExercicio;

    @JoinColumn(name = "id_tipo_cobranca", referencedColumnName = "id_tipo_cobranca")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private IssqnTipoCobranca tipoCobranca;

    public IssqnTipoCobrancaItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public IssqnTipoCobranca getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(IssqnTipoCobranca tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
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
        if (!(object instanceof IssqnTipoCobrancaItem)) {
            return false;
        }
        IssqnTipoCobrancaItem other = (IssqnTipoCobrancaItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnTipoCobrancaItem[ idTipoCobrancaItem=" + id + " ]";
    }

}
