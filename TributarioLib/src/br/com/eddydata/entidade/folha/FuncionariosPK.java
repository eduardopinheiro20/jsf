/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.folha;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author lucas
 */
@Embeddable
public class FuncionariosPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPRESA")
    private int empresa;
    @Column(name = "CHAPA")
    private Integer chapa;

    public FuncionariosPK() {
    }

    public FuncionariosPK(int empresa, Integer chapa) {
        this.empresa = empresa;
        this.chapa = chapa;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public Integer getChapa() {
        return chapa;
    }

    public void setChapa(Integer chapa) {
        this.chapa = chapa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empresa;
        hash += (chapa != null ? chapa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuncionariosPK)) {
            return false;
        }
        FuncionariosPK other = (FuncionariosPK) object;
        if (this.empresa != other.empresa) {
            return false;
        }
        if ((this.chapa == null && other.chapa != null) || (this.chapa != null && !this.chapa.equals(other.chapa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.eddydata.holerite.entidade.FuncionariosPK[ empresa=" + empresa + ", chapa=" + chapa + " ]";
    }
    
}
