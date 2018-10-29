/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.geo;

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
@Table(name = "abreviatura")
public class Abreviatura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abreviatura")
    private Integer id;
    @Column(name = "nome", length = 15)
    private String nome;
    @Column(name = "nome_extenso", length = 60)
    private String nomeExtenso;
    @Column(name = "tp_abrev", length = 1)
    private String tpAbrev;

    public Abreviatura() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeExtenso() {
        return nomeExtenso;
    }

    public void setNomeExtenso(String nomeExtenso) {
        this.nomeExtenso = nomeExtenso;
    }

    public String getTpAbrev() {
        return tpAbrev;
    }

    public void setTpAbrev(String tpAbrev) {
        this.tpAbrev = tpAbrev;
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
        if (!(object instanceof Abreviatura)) {
            return false;
        }
        Abreviatura other = (Abreviatura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Abreviatura[ idAbreviatura=" + id + " ]";
    }

}
