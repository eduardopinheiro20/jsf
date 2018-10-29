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
@Table(name = "issqn_escritorio")
public class IssqnEscritorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escritorio")
    private Integer id;
    @Column(name = "nome_fantasia", length = 50)
    private String nomeFantasia;
    @Column(name = "telefone", length = 20)
    private String telefone;
    @Column(name = "responsavel", length = 50)
    private String responsavel;
    @Column(name = "orgao_classe", length = 50)
    private String orgaoClasse;
    @Column(name = "numero_classe", length = 15)
    private String numeroClasse;
    @Column(name = "cnpj", length = 20)
    private String cnpj;
    @Column(name = "cpf", length = 20)
    private String cpf;
    @Column(name = "fax", length = 20)
    private String fax;
    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "razao_social", length = 50)
    private String razaoSocial;
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

    @Column(name = "uf", length = 2)
    private String uf;
    @Column(name = "complemento", length = 30)
    private String complemento;

    public IssqnEscritorio() {
    }

    public IssqnEscritorio(Integer idEscritorio) {
        this.id = idEscritorio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getOrgaoClasse() {
        return orgaoClasse;
    }

    public void setOrgaoClasse(String orgaoClasse) {
        this.orgaoClasse = orgaoClasse;
    }

    public String getNumeroClasse() {
        return numeroClasse;
    }

    public void setNumeroClasse(String numeroClasse) {
        this.numeroClasse = numeroClasse;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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
        if (!(object instanceof IssqnEscritorio)) {
            return false;
        }
        IssqnEscritorio other = (IssqnEscritorio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnEscritorio[ idEscritorio=" + id + " ]";
    }

}
