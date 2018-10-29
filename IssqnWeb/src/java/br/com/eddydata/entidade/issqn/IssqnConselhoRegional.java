/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.geo.Bairro;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.geo.Logradouro;
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
@Table(name = "issqn_conselho_regional")
public class IssqnConselhoRegional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conselho")
    private Integer id;
    @Column(name = "descricao", length = 50)
    private String descricao;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "sigla", length = 10)
    private String sigla;
    @Column(name = "conselho_regional", length = 50)
    private String conselhoRegional;
    @Column(name = "cep", length = 20)
    private String cep;

    @JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cidade cidade;

    @JoinColumns({
        @JoinColumn(name = "id_bairro", referencedColumnName = "id_bairro"),
        @JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade", insertable = false, updatable = false)})
    @ManyToOne(fetch = FetchType.LAZY)
    private Bairro bairro;
    @JoinColumns({
        @JoinColumn(name = "id_logradouro", referencedColumnName = "id_logradouro"),
        @JoinColumn(name = "id_cidade", referencedColumnName = "id_cidade", insertable = false, updatable = false)})
    @ManyToOne(fetch = FetchType.LAZY)
    private Logradouro logradouro;
    @Column(name = "complemento", length = 255)
    private String complemento;

    public IssqnConselhoRegional() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getConselhoRegional() {
        return conselhoRegional;
    }

    public void setConselhoRegional(String conselhoRegional) {
        this.conselhoRegional = conselhoRegional;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
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
        if (!(object instanceof IssqnConselhoRegional)) {
            return false;
        }
        IssqnConselhoRegional other = (IssqnConselhoRegional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnConselhoRegional[ idConselho=" + id + " ]";
    }

}
