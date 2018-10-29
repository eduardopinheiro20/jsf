/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

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
@Table(name = "issqn_atividade")
public class IssqnAtividade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atividade")
    private Integer id;
    @Column(name = "nome", length = 500)
    private String nome;
    @Column(name = "observacao", length = 150)
    private String observacao;
    @Column(name = "tipo_pessoa", length = 2)
    private String tipoPessoa;
    @Column(name = "aliquota")
    private Double aliquota;
    @Column(name = "valor")
    private Double valor;
    @Column(name = "inativo")
    private Integer inativo;
    @Column(name = "cod_atividade", length = 255)
    private String codAtividade;
    @Column(name = "cod_atv", length = 255)
    private String codAtv;
    @Column(name = "tp_atividade")
    private Integer tpAtividade;
    
    @JoinColumn(name = "id_codigo", referencedColumnName = "id_codigo")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnCodigoFiscal codigoFiscal;
    
    @Column(name = "risco", length = 5000)
    private String risco;
    
    @JoinColumn(name = "id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnOrgaoFiscalizacao orgaoFiscalizacao;

    public IssqnAtividade() {
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Double getAliquota() {
        return aliquota;
    }

    public void setAliquota(Double aliquota) {
        this.aliquota = aliquota;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getInativo() {
        return inativo;
    }

    public void setInativo(Integer inativo) {
        this.inativo = inativo;
    }

    public String getCodAtividade() {
        return codAtividade;
    }

    public void setCodAtividade(String codAtividade) {
        this.codAtividade = codAtividade;
    }

    public String getCodAtv() {
        return codAtv;
    }

    public void setCodAtv(String codAtv) {
        this.codAtv = codAtv;
    }

    public IssqnCodigoFiscal getCodigoFiscal() {
        return codigoFiscal;
    }

    public void setCodigoFiscal(IssqnCodigoFiscal codigoFiscal) {
        this.codigoFiscal = codigoFiscal;
    }

    public String getRisco() {
        return risco;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }

    public IssqnOrgaoFiscalizacao getOrgaoFiscalizacao() {
        return orgaoFiscalizacao;
    }

    public void setOrgaoFiscalizacao(IssqnOrgaoFiscalizacao orgaoFiscalizacao) {
        this.orgaoFiscalizacao = orgaoFiscalizacao;
    }

    public Integer getTpAtividade() {
        return tpAtividade;
    }

    public void setTpAtividade(Integer tpAtividade) {
        this.tpAtividade = tpAtividade;
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
        if (!(object instanceof IssqnAtividade)) {
            return false;
        }
        IssqnAtividade other = (IssqnAtividade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnAtividade[ idAtividade=" + id + " ]";
    }

}
