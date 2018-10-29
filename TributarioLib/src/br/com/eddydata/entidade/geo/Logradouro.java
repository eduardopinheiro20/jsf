/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.geo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "logradouro")
public class Logradouro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LogradouroPK logradouroPK;
    @Column(name = "nome", length = 150)
    private String nome;

    @JoinColumn(name = "id_abreviatura", referencedColumnName = "id_abreviatura")
    @ManyToOne(fetch = FetchType.LAZY)
    private Abreviatura abreviatura;

    public Logradouro() {
    }

    public LogradouroPK getLogradouroPK() {
        return logradouroPK;
    }

    public void setLogradouroPK(LogradouroPK logradouroPK) {
        this.logradouroPK = logradouroPK;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Abreviatura getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(Abreviatura abreviatura) {
        this.abreviatura = abreviatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logradouroPK != null ? logradouroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logradouro)) {
            return false;
        }
        Logradouro other = (Logradouro) object;
        if ((this.logradouroPK == null && other.logradouroPK != null) || (this.logradouroPK != null && !this.logradouroPK.equals(other.logradouroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logradouro[ logradouroPK=" + logradouroPK + " ]";
    }

}
