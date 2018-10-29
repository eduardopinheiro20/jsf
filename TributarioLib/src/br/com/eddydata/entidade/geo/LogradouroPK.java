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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author eddydata
 */
@Embeddable
public class LogradouroPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_logradouro")
    private Integer idLogradouro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cidade")
    private Integer idCidade;

    public LogradouroPK() {
    }

    public LogradouroPK(Integer idLogradouro, Integer idCidade) {
        this.idLogradouro = idLogradouro;
        this.idCidade = idCidade;
    }

    public Integer getIdLogradouro() {
        return idLogradouro;
    }

    public void setIdLogradouro(Integer idLogradouro) {
        this.idLogradouro = idLogradouro;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (Integer) idLogradouro;
        hash += (Integer) idCidade;
        return hash;
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogradouroPK)) {
            return false;
        }
        LogradouroPK other = (LogradouroPK) object;
        if (this.idLogradouro != other.idLogradouro) {
            return false;
        }
        if (this.idCidade != other.idCidade) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.issqn.LogradouroPK[ idLogradouro=" + idLogradouro + ", idCidade=" + idCidade + " ]";
    }
    
}
