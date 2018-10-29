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
@Table(name = "issqn_termo_fiscalizacao")
public class IssqnTermoFiscalizacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_termo_fiscalizacao")
    private Integer id;
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "nr_imovel")
    private Integer nrImovel;
    @Column(name = "complemento", length = 50)
    private String complemento;
    @Column(name = "atividade", length = 500)
    private String atividade;
    @Column(name = "estabelecimento", length = 300)
    private String estabelecimento;
    @Column(name = "nome_escritorio", length = 30)
    private String nomeEscritorio;
    @Column(name = "fone_escritorio", length = 20)
    private String foneEscritorio;
    @Column(name = "fone", length = 20)
    private String fone;
    @Column(name = "cnpj_cpf", length = 21)
    private String cnpjCpf;
    @Column(name = "proprietario", length = 300)
    private String proprietario;
    @Column(name = "proprietario_profissao", length = 150)
    private String proprietarioProfissao;
    @Column(name = "proprietario_cpf", length = 45)
    private String proprietarioCpf;
    @Column(name = "num_alvara", length = 20)
    private String numAlvara;
    @Column(name = "apuracao", length = 1000)
    private String apuracao;
    @Column(name = "observacao", length = 1000)
    private String observacao;
    @Column(name = "fiscal", length = 100)
    private String fiscal;

    @JoinColumn(name = "id_bairrologradouro", referencedColumnName = "id_bairrologradouro")
    @ManyToOne(fetch = FetchType.LAZY)
    private BairroLogradouro bairrologradouro;
    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    public IssqnTermoFiscalizacao() {
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

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getNomeEscritorio() {
        return nomeEscritorio;
    }

    public void setNomeEscritorio(String nomeEscritorio) {
        this.nomeEscritorio = nomeEscritorio;
    }

    public String getFoneEscritorio() {
        return foneEscritorio;
    }

    public void setFoneEscritorio(String foneEscritorio) {
        this.foneEscritorio = foneEscritorio;
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

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getProprietarioProfissao() {
        return proprietarioProfissao;
    }

    public void setProprietarioProfissao(String proprietarioProfissao) {
        this.proprietarioProfissao = proprietarioProfissao;
    }

    public String getProprietarioCpf() {
        return proprietarioCpf;
    }

    public void setProprietarioCpf(String proprietarioCpf) {
        this.proprietarioCpf = proprietarioCpf;
    }

    public String getNumAlvara() {
        return numAlvara;
    }

    public void setNumAlvara(String numAlvara) {
        this.numAlvara = numAlvara;
    }

    public String getApuracao() {
        return apuracao;
    }

    public void setApuracao(String apuracao) {
        this.apuracao = apuracao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
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
        if (!(object instanceof IssqnTermoFiscalizacao)) {
            return false;
        }
        IssqnTermoFiscalizacao other = (IssqnTermoFiscalizacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnTermoFiscalizacao[ idTermoFiscalizacao=" + id + " ]";
    }

}
