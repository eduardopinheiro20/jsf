/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.geo;

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
@Table(name = "bairro")
public class Bairro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bairro")
    private Integer idBairro;

    @JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cidade cidade;

    @Column(name = "nome", length = 100)
    private String nome;

    @JoinColumn(name = "id_abreviatura", referencedColumnName = "id_abreviatura")
    @ManyToOne(fetch = FetchType.LAZY)
    private Abreviatura abreviatura;

    public Bairro() {
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

    public Integer getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(Integer idBairro) {
        this.idBairro = idBairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBairro != null ? idBairro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(obj instanceof Bairro)) {
            return false;
        }
        Bairro other = (Bairro) obj;
        if ((this.idBairro == null && other.idBairro != null) || (this.idBairro != null && !this.idBairro.equals(other.idBairro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.admin.Bairro[ idBairro=" + idBairro + " ]";
    }

}
