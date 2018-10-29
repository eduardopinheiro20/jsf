/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.geral;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "banco")
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banco")
    private Integer id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "numero", length = 20)
    private String numero;
    @Column(name = "agencia", length = 15)
    private String agencia;
    @Column(name = "conta", length = 25)
    private String conta;
    @Column(name = "telefone", length = 13)
    private String telefone;
    @Column(name = "fax", length = 13)
    private String fax;
    @Column(name = "gerente", length = 300)
    private String gerente;
    @Lob
    @Column(name = "logotipo")
    private byte[] logotipo;
    @Column(name = "dv_conta", length = 1)
    private String dvConta;
    @Column(name = "dv_agencia", length = 1)
    private String dvAgencia;
    @Column(name = "corpo_boleto", length = 5000)
    private String corpoBoleto;
    @Column(name = "boleto_local_pagto_1", length = 60)
    private String boletoLocalPagto1;
    @Column(name = "boleto_local_pagto_2", length = 60)
    private String boletoLocalPagto2;
    @Column(name = "boleto_carteira", length = 5)
    private String boletoCarteira;
    @Column(name = "boleto_especie_documento", length = 5)
    private String boletoEspecieDocumento;
    @Column(name = "numero_convenio", length = 10)
    private String numeroConvenio;
    @Column(name = "conta_orcamentaria", length = 25)
    private String contaOrcamentaria;
    @Column(name = "remessa")
    private Integer remessa;
    @Column(name = "cod_banco")
    private Integer codBanco;
    @Column(name = "cnab")
    private Integer cnab;

    public Banco() {
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getDvConta() {
        return dvConta;
    }

    public void setDvConta(String dvConta) {
        this.dvConta = dvConta;
    }

    public String getDvAgencia() {
        return dvAgencia;
    }

    public void setDvAgencia(String dvAgencia) {
        this.dvAgencia = dvAgencia;
    }

    public String getCorpoBoleto() {
        return corpoBoleto;
    }

    public void setCorpoBoleto(String corpoBoleto) {
        this.corpoBoleto = corpoBoleto;
    }

    public String getBoletoLocalPagto1() {
        return boletoLocalPagto1;
    }

    public void setBoletoLocalPagto1(String boletoLocalPagto1) {
        this.boletoLocalPagto1 = boletoLocalPagto1;
    }

    public String getBoletoLocalPagto2() {
        return boletoLocalPagto2;
    }

    public void setBoletoLocalPagto2(String boletoLocalPagto2) {
        this.boletoLocalPagto2 = boletoLocalPagto2;
    }

    public String getBoletoCarteira() {
        return boletoCarteira;
    }

    public void setBoletoCarteira(String boletoCarteira) {
        this.boletoCarteira = boletoCarteira;
    }

    public String getBoletoEspecieDocumento() {
        return boletoEspecieDocumento;
    }

    public void setBoletoEspecieDocumento(String boletoEspecieDocumento) {
        this.boletoEspecieDocumento = boletoEspecieDocumento;
    }

    public String getNumeroConvenio() {
        return numeroConvenio;
    }

    public void setNumeroConvenio(String numeroConvenio) {
        this.numeroConvenio = numeroConvenio;
    }

    public String getContaOrcamentaria() {
        return contaOrcamentaria;
    }

    public void setContaOrcamentaria(String contaOrcamentaria) {
        this.contaOrcamentaria = contaOrcamentaria;
    }

    public Integer getRemessa() {
        return remessa;
    }

    public void setRemessa(Integer remessa) {
        this.remessa = remessa;
    }

    public Integer getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(Integer codBanco) {
        this.codBanco = codBanco;
    }

    public Integer getCnab() {
        return cnab;
    }

    public void setCnab(Integer cnab) {
        this.cnab = cnab;
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
        if (!(object instanceof Banco)) {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Banco[ idBanco=" + id + " ]";
    }

}
