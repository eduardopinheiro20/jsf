/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_codigo_fiscal")
public class IssqnCodigoFiscal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_codigo")
    private Integer id;

    @Column(name = "qtd_ufm")
    private Double qtdUfm;

    @Column(name = "porcentagem")
    private Double porcentagem;

    @Column(name = "codigo_fiscal", length = 20)
    private String codigoFiscal;

    public IssqnCodigoFiscal() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getQtdUfm() {
        return qtdUfm;
    }

    public void setQtdUfm(Double qtdUfm) {
        this.qtdUfm = qtdUfm;
    }

    public Double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public String getCodigoFiscal() {
        return codigoFiscal;
    }

    public void setCodigoFiscal(String codigoFiscal) {
        this.codigoFiscal = codigoFiscal;
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
        if (!(object instanceof IssqnCodigoFiscal)) {
            return false;
        }
        IssqnCodigoFiscal other = (IssqnCodigoFiscal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnCodigoFiscal[ idCodigo=" + id + " ]";
    }

}
