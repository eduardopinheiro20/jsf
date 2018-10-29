/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "exercicio")
public class Exercicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id_exercicio")
    private Integer ano;

    @Column(name = "aut_despesa")
    private String autDespesa;

    @Column(name = "cargo_despesa")
    private String cargoDespesa;

    @Column(name = "contador")
    private String contador;

    @Column(name = "cargo_contador")
    private String cargoContador;

    @Column(name = "tesoureiro")
    private String tesoureiro;

    @Column(name = "cargo_tesoureiro")
    private String cargoTesoureiro;

    @Column(name = "AUT_PAGTO")
    private String autPagto;

    @Column(name = "cargo_pagto")
    private String cargoPagto;

    @Column(name = "assinatura1")
    private String assinatura1;

    @Column(name = "cargo_assina1")
    private String cargoAssina1;

    @Column(name = "assinatura2")
    private String assinatura2;

    @Column(name = "cargo_assina2")
    private String cargoAssina2;

    @Column(name = "assinatura3")
    private String assinatura3;

    @Column(name = "cargo_assina3")
    private String cargoAssina3;

    @Column(name = "nu_lei")
    private String nuLei;

    @Column(name = "nu_ldo")
    private String nuLdo;

    @Column(name = "responsavel_compras")
    private String responsavelCompras;

    @Column(name = "contato_fornecedor")
    private String contatoFornecedor;

    @Column(name = "controle")
    private String controle;

    @Column(name = "cargo_controle")
    private String cargoControle;

    @Column(name = "sec_educacao")
    private String secEducacao;

    @Column(name = "cargo_sec_educacao")
    private String cargoSecEducacao;

    @Column(name = "pres_educacao")
    private String presEducacao;

    @Column(name = "cargo_pres_educacao")
    private String cargoPresEducacao;

    @Column(name = "compras_contato1_nome")
    private String comprasContato1Nome;

    @Column(name = "compras_contato2_nome")
    private String comprasContato2Nome;

    @Column(name = "compras_contato1_email")
    private String comprasContato1Email;

    @Column(name = "compras_contato2_email")
    private String comprasContato2Email;

    @Column(name = "compras_contato1_telefone")
    private String comprasContato1Telefone;

    @Column(name = "compras_contato2_telefone")
    private String comprasContato2Telefone;

    @Column(name = "tec_contabil")
    private String tecContabil;

    @Column(name = "cargo_tec_contabil")
    private String cargoTecContabil;

    @Column(name = "resp_process")
    private String respProcess;

    @Column(name = "cargo_resp_process")
    private String cargoRespProcess;

    @Column(name = "publica_reo")
    private String publicaReo;

    @Column(name = "lei_adiantamento")
    private String leiAdiantamento;

    @Column(name = "comunicado_numero")
    private String comunicadoNumero;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_lei")
    private Date dtLei;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_ldo")
    private Date dtLdo;

    @Lob
    @Column(name = "projeto_lei")
    private byte[] projetoLei;

    @JoinColumn(name = "id_orgao", referencedColumnName = "id_orgao")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContabilOrgao orgao;

    public Exercicio() {
    }

    public Exercicio(Integer ano) {
        this.ano = ano;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getAutDespesa() {
        return autDespesa;
    }

    public void setAutDespesa(String autDespesa) {
        this.autDespesa = autDespesa;
    }

    public String getCargoDespesa() {
        return cargoDespesa;
    }

    public void setCargoDespesa(String cargoDespesa) {
        this.cargoDespesa = cargoDespesa;
    }

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getCargoContador() {
        return cargoContador;
    }

    public void setCargoContador(String cargoContador) {
        this.cargoContador = cargoContador;
    }

    public String getTesoureiro() {
        return tesoureiro;
    }

    public void setTesoureiro(String tesoureiro) {
        this.tesoureiro = tesoureiro;
    }

    public String getCargoTesoureiro() {
        return cargoTesoureiro;
    }

    public void setCargoTesoureiro(String cargoTesoureiro) {
        this.cargoTesoureiro = cargoTesoureiro;
    }

    public String getAutPagto() {
        return autPagto;
    }

    public void setAutPagto(String autPagto) {
        this.autPagto = autPagto;
    }

    public String getCargoPagto() {
        return cargoPagto;
    }

    public void setCargoPagto(String cargoPagto) {
        this.cargoPagto = cargoPagto;
    }

    public String getAssinatura1() {
        return assinatura1;
    }

    public void setAssinatura1(String assinatura1) {
        this.assinatura1 = assinatura1;
    }

    public String getCargoAssina1() {
        return cargoAssina1;
    }

    public void setCargoAssina1(String cargoAssina1) {
        this.cargoAssina1 = cargoAssina1;
    }

    public String getAssinatura2() {
        return assinatura2;
    }

    public void setAssinatura2(String assinatura2) {
        this.assinatura2 = assinatura2;
    }

    public String getCargoAssina2() {
        return cargoAssina2;
    }

    public void setCargoAssina2(String cargoAssina2) {
        this.cargoAssina2 = cargoAssina2;
    }

    public String getAssinatura3() {
        return assinatura3;
    }

    public void setAssinatura3(String assinatura3) {
        this.assinatura3 = assinatura3;
    }

    public String getCargoAssina3() {
        return cargoAssina3;
    }

    public void setCargoAssina3(String cargoAssina3) {
        this.cargoAssina3 = cargoAssina3;
    }

    public String getNuLei() {
        return nuLei;
    }

    public void setNuLei(String nuLei) {
        this.nuLei = nuLei;
    }

    public String getNuLdo() {
        return nuLdo;
    }

    public void setNuLdo(String nuLdo) {
        this.nuLdo = nuLdo;
    }

    public Date getDtLei() {
        return dtLei;
    }

    public void setDtLei(Date dtLei) {
        this.dtLei = dtLei;
    }

    public Date getDtLdo() {
        return dtLdo;
    }

    public void setDtLdo(Date dtLdo) {
        this.dtLdo = dtLdo;
    }

    public byte[] getProjetoLei() {
        return projetoLei;
    }

    public void setProjetoLei(byte[] projetoLei) {
        this.projetoLei = projetoLei;
    }

    public String getResponsavelCompras() {
        return responsavelCompras;
    }

    public void setResponsavelCompras(String responsavelCompras) {
        this.responsavelCompras = responsavelCompras;
    }

    public String getContatoFornecedor() {
        return contatoFornecedor;
    }

    public void setContatoFornecedor(String contatoFornecedor) {
        this.contatoFornecedor = contatoFornecedor;
    }

    public String getControle() {
        return controle;
    }

    public void setControle(String controle) {
        this.controle = controle;
    }

    public String getCargoControle() {
        return cargoControle;
    }

    public void setCargoControle(String cargoControle) {
        this.cargoControle = cargoControle;
    }

    public String getSecEducacao() {
        return secEducacao;
    }

    public void setSecEducacao(String secEducacao) {
        this.secEducacao = secEducacao;
    }

    public String getCargoSecEducacao() {
        return cargoSecEducacao;
    }

    public void setCargoSecEducacao(String cargoSecEducacao) {
        this.cargoSecEducacao = cargoSecEducacao;
    }

    public String getPresEducacao() {
        return presEducacao;
    }

    public void setPresEducacao(String presEducacao) {
        this.presEducacao = presEducacao;
    }

    public String getCargoPresEducacao() {
        return cargoPresEducacao;
    }

    public void setCargoPresEducacao(String cargoPresEducacao) {
        this.cargoPresEducacao = cargoPresEducacao;
    }

    public String getComprasContato1Nome() {
        return comprasContato1Nome;
    }

    public void setComprasContato1Nome(String comprasContato1Nome) {
        this.comprasContato1Nome = comprasContato1Nome;
    }

    public String getComprasContato2Nome() {
        return comprasContato2Nome;
    }

    public void setComprasContato2Nome(String comprasContato2Nome) {
        this.comprasContato2Nome = comprasContato2Nome;
    }

    public String getComprasContato1Email() {
        return comprasContato1Email;
    }

    public void setComprasContato1Email(String comprasContato1Email) {
        this.comprasContato1Email = comprasContato1Email;
    }

    public String getComprasContato2Email() {
        return comprasContato2Email;
    }

    public void setComprasContato2Email(String comprasContato2Email) {
        this.comprasContato2Email = comprasContato2Email;
    }

    public String getComprasContato1Telefone() {
        return comprasContato1Telefone;
    }

    public void setComprasContato1Telefone(String comprasContato1Telefone) {
        this.comprasContato1Telefone = comprasContato1Telefone;
    }

    public String getComprasContato2Telefone() {
        return comprasContato2Telefone;
    }

    public void setComprasContato2Telefone(String comprasContato2Telefone) {
        this.comprasContato2Telefone = comprasContato2Telefone;
    }

    public String getTecContabil() {
        return tecContabil;
    }

    public void setTecContabil(String tecContabil) {
        this.tecContabil = tecContabil;
    }

    public String getCargoTecContabil() {
        return cargoTecContabil;
    }

    public void setCargoTecContabil(String cargoTecContabil) {
        this.cargoTecContabil = cargoTecContabil;
    }

    public String getRespProcess() {
        return respProcess;
    }

    public void setRespProcess(String respProcess) {
        this.respProcess = respProcess;
    }

    public String getCargoRespProcess() {
        return cargoRespProcess;
    }

    public void setCargoRespProcess(String cargoRespProcess) {
        this.cargoRespProcess = cargoRespProcess;
    }

    public String getPublicaReo() {
        return publicaReo;
    }

    public void setPublicaReo(String publicaReo) {
        this.publicaReo = publicaReo;
    }

    public String getLeiAdiantamento() {
        return leiAdiantamento;
    }

    public void setLeiAdiantamento(String leiAdiantamento) {
        this.leiAdiantamento = leiAdiantamento;
    }

    public String getComunicadoNumero() {
        return comunicadoNumero;
    }

    public void setComunicadoNumero(String comunicadoNumero) {
        this.comunicadoNumero = comunicadoNumero;
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
        hash += (ano != null ? ano.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exercicio)) {
            return false;
        }
        Exercicio other = (Exercicio) object;
        return !((this.ano == null && other.ano != null) || (this.ano != null && !this.ano.equals(other.ano)));
    }

    @Override
    public String toString() {
        return "Exercicio[ idExercicio=" + ano + " ]";
    }

}
