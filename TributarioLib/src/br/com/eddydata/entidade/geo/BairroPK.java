/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.geo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author eddydata
 */
@Embeddable
public class BairroPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_bairro")
    private int idBairro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cidade")
    private int idCidade;

    public BairroPK() {
    }

    public BairroPK(int idBairro, int idCidade) {
        this.idBairro = idBairro;
        this.idCidade = idCidade;
    }

    public int getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(int idBairro) {
        this.idBairro = idBairro;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idBairro;
        hash += (int) idCidade;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BairroPK)) {
            return false;
        }
        BairroPK other = (BairroPK) object;
        if (this.idBairro != other.idBairro) {
            return false;
        }
        if (this.idCidade != other.idCidade) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.issqn.BairroPK[ idBairro=" + idBairro + ", idCidade=" + idCidade + " ]";
    }
    
}
