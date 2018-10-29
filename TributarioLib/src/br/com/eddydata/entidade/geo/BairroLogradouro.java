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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "bairro_logradouro")
public class BairroLogradouro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bairrologradouro")
    private Integer id;
    @Column(name = "nr_inicial")
    private Integer nrInicial;
    @Column(name = "nr_final")
    private Integer nrFinal;
    @Column(name = "geo_logradouro", length = 15)
    private String geoLogradouro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade")
    private Cidade cidade;
    @JoinColumns({
        @JoinColumn(name = "id_bairro", referencedColumnName = "id_bairro"),
        @JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Bairro bairro;
    @JoinColumns({
        @JoinColumn(name = "id_logradouro", referencedColumnName = "id_logradouro"),
        @JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Logradouro logradouro;

    public BairroLogradouro() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNrInicial() {
        return nrInicial;
    }

    public void setNrInicial(Integer nrInicial) {
        this.nrInicial = nrInicial;
    }

    public Integer getNrFinal() {
        return nrFinal;
    }

    public void setNrFinal(Integer nrFinal) {
        this.nrFinal = nrFinal;
    }

    public String getGeoLogradouro() {
        return geoLogradouro;
    }

    public void setGeoLogradouro(String geoLogradouro) {
        this.geoLogradouro = geoLogradouro;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BairroLogradouro)) {
            return false;
        }
        BairroLogradouro other = (BairroLogradouro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BairroLogradouro[ idBairrologradouro=" + id + " ]";
    }

}
