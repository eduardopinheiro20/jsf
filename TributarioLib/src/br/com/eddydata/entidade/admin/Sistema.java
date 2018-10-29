/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sistema")
public class Sistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_sistema", nullable = false, length = 15)
    private String idSistema;

    @Column(name = "nome", length = 60)
    private String nome;

    public Sistema() {
    }

    public Sistema(String idSistema) {
        this.idSistema = idSistema;
    }

    public String getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSistema != null ? idSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sistema)) {
            return false;
        }
        Sistema other = (Sistema) object;
        return !((this.idSistema == null && other.idSistema != null) || (this.idSistema != null && !this.idSistema.equals(other.idSistema)));
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.Sistema[ idSistema=" + idSistema + " ]";
    }

}
