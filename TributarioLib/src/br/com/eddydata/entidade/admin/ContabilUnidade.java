/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contabil_unidade")
public class ContabilUnidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ContabilUnidadePK unidadePK;

    @Size(max = 70)
    @Column(name = "nome")
    private String nome;
    @Size(max = 60)
    @Column(name = "secretario")
    private String secretario;
    @Size(max = 30)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 18)
    @Column(name = "cpf")
    private String cpf;
    @Size(max = 10)
    @Column(name = "ato")
    private String ato;
    @Size(max = 10)
    @Column(name = "tipo_unidade")
    private String tipoUnidade;
    @Size(max = 900)
    @Column(name = "atuacao")
    private String atuacao;
    @Size(max = 900)
    @Column(name = "legislacao")
    private String legislacao;
    @Column(name = "id_tribunal")
    private Integer idTribunal;
    @Column(name = "id_unidade_frota")
    private Integer idUnidadeFrota;

    @Column(name = "id_parente")
    private String idParente;

    @JoinColumn(name = "id_exercicio", referencedColumnName = "id_exercicio", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Exercicio exercicio;

    @JoinColumns({
        @JoinColumn(name = "id_parente", referencedColumnName = "id_unidade", insertable = false, updatable = false),
        @JoinColumn(name = "id_exercicio", referencedColumnName = "id_exercicio", insertable = false, updatable = false)})
    @ManyToOne(fetch = FetchType.LAZY)
    private ContabilUnidade parente;

    @JoinColumn(name = "id_orgao", referencedColumnName = "id_orgao")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContabilOrgao orgao;

    public ContabilUnidade() {
    }

    public ContabilUnidade(ContabilUnidadePK unidadePK) {
        this.unidadePK = unidadePK;
    }

    public ContabilUnidade(int idExercicio, String idUnidade) {
        this.unidadePK = new ContabilUnidadePK(idExercicio, idUnidade);
    }

    public ContabilUnidadePK getUnidadePK() {
        return unidadePK;
    }

    public void setUnidadePK(ContabilUnidadePK unidadePK) {
        this.unidadePK = unidadePK;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSecretario() {
        return secretario;
    }

    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAto() {
        return ato;
    }

    public void setAto(String ato) {
        this.ato = ato;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }

    public String getAtuacao() {
        return atuacao;
    }

    public void setAtuacao(String atuacao) {
        this.atuacao = atuacao;
    }

    public String getLegislacao() {
        return legislacao;
    }

    public void setLegislacao(String legislacao) {
        this.legislacao = legislacao;
    }

    public Integer getIdTribunal() {
        return idTribunal;
    }

    public void setIdTribunal(Integer idTribunal) {
        this.idTribunal = idTribunal;
    }

    public Integer getIdUnidadeFrota() {
        return idUnidadeFrota;
    }

    public void setIdUnidadeFrota(Integer idUnidadeFrota) {
        this.idUnidadeFrota = idUnidadeFrota;
    }

    public String getIdParente() {
        return idParente;
    }

    public void setIdParente(String idParente) {
        this.idParente = idParente;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public ContabilUnidade getParente() {
        return parente;
    }

    public void setParente(ContabilUnidade parente) {
        this.parente = parente;
    }

    public ContabilOrgao getOrgao() {
        return orgao;
    }

    public void setOrgao(ContabilOrgao orgao) {
        this.orgao = orgao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unidadePK != null ? unidadePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContabilUnidade)) {
            return false;
        }
        ContabilUnidade other = (ContabilUnidade) object;
        return !((this.unidadePK == null && other.unidadePK != null) || (this.unidadePK != null && !this.unidadePK.equals(other.unidadePK)));
    }

    @Override
    public String toString() {
        return "br.com.eddydata.entidade.loa.ContabilUnidade[ unidadePK=" + unidadePK + " ]";
    }

}
