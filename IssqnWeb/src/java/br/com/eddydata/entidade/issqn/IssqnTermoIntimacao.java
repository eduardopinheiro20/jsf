/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.geo.BairroLogradouro;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_termo_intimacao")
public class IssqnTermoIntimacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_termo_intimacao")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "intimacao", length = 50)
    private String intimacao;
    @Column(name = "notificacao", length = 50)
    private String notificacao;
    @Column(name = "nr_imovel")
    private Integer nrImovel;
    @Column(name = "complemento", length = 50)
    private String complemento;
    @Column(name = "contribuinte", length = 300)
    private String contribuinte;
    @Column(name = "lei_municipal", length = 50)
    private String leiMunicipal;
    @Column(name = "fone", length = 20)
    private String fone;
    @Column(name = "cnpj_cpf", length = 21)
    private String cnpjCpf;
    @Column(name = "num_alvara", length = 20)
    private String numAlvara;
    @Column(name = "atividade", length = 300)
    private String atividade;
    @Column(name = "prazo_de", length = 1000)
    private String prazoDe;
    @Column(name = "observacao", length = 1000)
    private String observacao;
    @Column(name = "historico", length = 1000)
    private String historico;

    @JoinColumn(name = "id_bairrologradouro", referencedColumnName = "id_bairrologradouro")
    @ManyToOne(fetch = FetchType.LAZY)
    private BairroLogradouro bairrologradouro;
    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    public IssqnTermoIntimacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getIntimacao() {
        return intimacao;
    }

    public void setIntimacao(String intimacao) {
        this.intimacao = intimacao;
    }

    public String getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(String notificacao) {
        this.notificacao = notificacao;
    }

    public Integer getNrImovel() {
        return nrImovel;
    }

    public void setNrImovel(Integer nrImovel) {
        this.nrImovel = nrImovel;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }

    public String getLeiMunicipal() {
        return leiMunicipal;
    }

    public void setLeiMunicipal(String leiMunicipal) {
        this.leiMunicipal = leiMunicipal;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getNumAlvara() {
        return numAlvara;
    }

    public void setNumAlvara(String numAlvara) {
        this.numAlvara = numAlvara;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getPrazoDe() {
        return prazoDe;
    }

    public void setPrazoDe(String prazoDe) {
        this.prazoDe = prazoDe;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public BairroLogradouro getBairrologradouro() {
        return bairrologradouro;
    }

    public void setBairrologradouro(BairroLogradouro bairrologradouro) {
        this.bairrologradouro = bairrologradouro;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
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
        if (!(object instanceof IssqnTermoIntimacao)) {
            return false;
        }
        IssqnTermoIntimacao other = (IssqnTermoIntimacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnTermoIntimacao[ idTermoIntimacao=" + id + " ]";
    }

}
