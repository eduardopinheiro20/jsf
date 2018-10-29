/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import br.com.eddydata.entidade.geo.BairroLogradouro;
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
@Table(name = "imovel")
public class Imovel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imovel")
    private Integer id;
    @Column(name = "nr_imovel", length = 10)
    private String nrImovel;
    @Column(name = "cep", length = 20)
    private String cep;
    @Column(name = "complemento", length = 5000)
    private String complemento;
    @Column(name = "ponto_referencia", length = 100)
    private String pontoReferencia;
    @Column(name = "quadra", length = 100)
    private String quadra;
    @Column(name = "lote", length = 100)
    private String lote;
    @JoinColumn(name = "id_bairrologradouro", referencedColumnName = "id_bairrologradouro")
    @ManyToOne(fetch = FetchType.LAZY)
    private BairroLogradouro bairroLogradouro;

    public Imovel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNrImovel() {
        return nrImovel;
    }

    public void setNrImovel(String nrImovel) {
        this.nrImovel = nrImovel;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getPontoReferencia() {
        return pontoReferencia;
    }

    public void setPontoReferencia(String pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public BairroLogradouro getBairrologradouro() {
        return bairroLogradouro;
    }

    public void setBairrologradouro(BairroLogradouro bairroLogradouro) {
        this.bairroLogradouro = bairroLogradouro;
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
        if (!(object instanceof Imovel)) {
            return false;
        }
        Imovel other = (Imovel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Imovel[ idImovel=" + id + " ]";
    }

}
