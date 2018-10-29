/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ContabilUnidadePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_exercicio")
    private int idExercicio;

    @Basic(optional = false)
    @Column(name = "id_unidade")
    private String idUnidade;

    public ContabilUnidadePK() {
    }

    public ContabilUnidadePK(int idExercicio, String idUnidade) {
        this.idExercicio = idExercicio;
        this.idUnidade = idUnidade;
    }

    public int getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(int idExercicio) {
        this.idExercicio = idExercicio;
    }

    public String getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idExercicio;
        hash += (idUnidade != null ? idUnidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContabilUnidadePK)) {
            return false;
        }
        ContabilUnidadePK other = (ContabilUnidadePK) object;
        if (this.idExercicio != other.idExercicio) {
            return false;
        }
        return !((this.idUnidade == null && other.idUnidade != null) || (this.idUnidade != null && !this.idUnidade.equals(other.idUnidade)));
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.loa.ContabilUnidadePK[ idExercicio=" + idExercicio + ", idUnidade=" + idUnidade + " ]";
    }

}
