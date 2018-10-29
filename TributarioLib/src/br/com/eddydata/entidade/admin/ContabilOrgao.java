/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.admin;

import br.com.eddydata.entidade.geo.Cidade;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contabil_orgao")
public class ContabilOrgao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "id_orgao")
    private String idOrgao;

    @Column(name = "tipo_orgao")
    private String tipoOrgao;
    @Column(name = "funcao")
    private Character funcao;
    @Column(name = "febraban_tributos")
    private Integer febrabanTributos;
    @Column(name = "febraban_agua")
    private Integer febrabanAgua;
    @Lob
    @Column(name = "brasao")
    private byte[] brasao;
    @Lob
    @Column(name = "logotipo")
    private byte[] logotipo;
    @Size(max = 50)
    @Column(name = "prefeito")
    private String prefeito;
    @Size(max = 50)
    @Column(name = "titulo_prefeito")
    private String tituloPrefeito;
    @Size(max = 50)
    @Column(name = "contador")
    private String contador;
    @Size(max = 20)
    @Column(name = "crc")
    private String crc;
    @Size(max = 50)
    @Column(name = "tesoureiro")
    private String tesoureiro;
    @Size(max = 50)
    @Column(name = "titulo_tesoureiro")
    private String tituloTesoureiro;
    @Size(max = 50)
    @Column(name = "titulo_contador")
    private String tituloContador;
    @Column(name = "id_logradouro_manual")
    private Integer idLogradouroManual;
    @Column(name = "usa_cartao_cidadao")
    private Integer usaCartaoCidadao;
    @Column(name = "usa_itbi")
    private Integer usaItbi;
    @Column(name = "usa_num_cert_geral")
    private Integer usaNumCertGeral;
    @Column(name = "numero_certidao")
    private Integer numeroCertidao;
    @Column(name = "controle_automatico")
    private Integer controleAutomatico;
    @Column(name = "baixa_inconsistencia")
    private Integer baixaInconsistencia;
    @Column(name = "baixa_mov_automatico")
    private Integer baixaMovAutomatico;
    @Column(name = "agua_firebird")
    private Integer aguaFirebird;
    @Size(max = 50)
    @Column(name = "febraban_local")
    private String febrabanLocal;
    @Size(max = 100)
    @Column(name = "febraban_observacao")
    private String febrabanObservacao;
    @Column(name = "itbi_inscricao_cadastral")
    private Integer itbiInscricaoCadastral;
    @Column(name = "permitir_certidao_negativa")
    private Integer permitirCertidaoNegativa;
    @Column(name = "bloquear_febraban")
    private Integer bloquearFebraban;
    @Column(name = "iptu_remessa")
    private Integer iptuRemessa;
    @Column(name = "issqn_remessa")
    private Integer issqnRemessa;
    @Column(name = "divida_remessa")
    private Integer dividaRemessa;
    @Column(name = "receita_remessa")
    private Integer receitaRemessa;
    @Column(name = "lote_sequencial")
    private Integer loteSequencial;
    @Size(max = 20)
    @Column(name = "cpf_chefe")
    private String cpfChefe;
    @Size(max = 20)
    @Column(name = "rg_chefe")
    private String rgChefe;
    @Column(name = "tipo_juros")
    private Integer tipoJuros;
    @Column(name = "calculo_correcao")
    private Integer calculoCorrecao;
    @Column(name = "nome")
    private String nome;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "cep")
    private String cep;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "estado")
    private String estado;
    @Column(name = "id_tribunal")
    private Integer idTribunal;
    @Column(name = "id_siafi")
    private Integer idSiafi;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "uf")
    private String uf;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "fax")
    private String fax;
    @Column(name = "insc_estadual")
    private String inscEstadual;
    @Column(name = "site")
    private String site;
    @Column(name = "email")
    private String email;
    @Column(name = "id_ibge")
    private String idIbge;
    @Column(name = "fiscal_rendas")
    private String fiscalRendas;
    @Column(name = "chefe_empreendedorismo")
    private String chefeEmpreendedorismo;

    @JoinColumn(name = "id_cidade", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cidade idCidade;

    public ContabilOrgao() {
    }

    public ContabilOrgao(String idOrgao) {
        this.idOrgao = idOrgao;
    }

    public String getIdOrgao() {
        return idOrgao;
    }

    public void setIdOrgao(String idOrgao) {
        this.idOrgao = idOrgao;
    }

    public String getTipoOrgao() {
        return tipoOrgao;
    }

    public void setTipoOrgao(String tipoOrgao) {
        this.tipoOrgao = tipoOrgao;
    }

    public Character getFuncao() {
        return funcao;
    }

    public void setFuncao(Character funcao) {
        this.funcao = funcao;
    }

    public Integer getFebrabanTributos() {
        return febrabanTributos;
    }

    public void setFebrabanTributos(Integer febrabanTributos) {
        this.febrabanTributos = febrabanTributos;
    }

    public Integer getFebrabanAgua() {
        return febrabanAgua;
    }

    public void setFebrabanAgua(Integer febrabanAgua) {
        this.febrabanAgua = febrabanAgua;
    }

    public byte[] getBrasao() {
        return brasao;
    }

    public void setBrasao(byte[] brasao) {
        this.brasao = brasao;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public String getPrefeito() {
        return prefeito;
    }

    public void setPrefeito(String prefeito) {
        this.prefeito = prefeito;
    }

    public String getTituloPrefeito() {
        return tituloPrefeito;
    }

    public void setTituloPrefeito(String tituloPrefeito) {
        this.tituloPrefeito = tituloPrefeito;
    }

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getTesoureiro() {
        return tesoureiro;
    }

    public void setTesoureiro(String tesoureiro) {
        this.tesoureiro = tesoureiro;
    }

    public String getTituloTesoureiro() {
        return tituloTesoureiro;
    }

    public void setTituloTesoureiro(String tituloTesoureiro) {
        this.tituloTesoureiro = tituloTesoureiro;
    }

    public String getTituloContador() {
        return tituloContador;
    }

    public void setTituloContador(String tituloContador) {
        this.tituloContador = tituloContador;
    }

    public Integer getIdLogradouroManual() {
        return idLogradouroManual;
    }

    public void setIdLogradouroManual(Integer idLogradouroManual) {
        this.idLogradouroManual = idLogradouroManual;
    }

    public Integer getUsaCartaoCidadao() {
        return usaCartaoCidadao;
    }

    public void setUsaCartaoCidadao(Integer usaCartaoCidadao) {
        this.usaCartaoCidadao = usaCartaoCidadao;
    }

    public Integer getUsaItbi() {
        return usaItbi;
    }

    public void setUsaItbi(Integer usaItbi) {
        this.usaItbi = usaItbi;
    }

    public Integer getUsaNumCertGeral() {
        return usaNumCertGeral;
    }

    public void setUsaNumCertGeral(Integer usaNumCertGeral) {
        this.usaNumCertGeral = usaNumCertGeral;
    }

    public Integer getNumeroCertidao() {
        return numeroCertidao;
    }

    public void setNumeroCertidao(Integer numeroCertidao) {
        this.numeroCertidao = numeroCertidao;
    }

    public Integer getControleAutomatico() {
        return controleAutomatico;
    }

    public void setControleAutomatico(Integer controleAutomatico) {
        this.controleAutomatico = controleAutomatico;
    }

    public Integer getBaixaInconsistencia() {
        return baixaInconsistencia;
    }

    public void setBaixaInconsistencia(Integer baixaInconsistencia) {
        this.baixaInconsistencia = baixaInconsistencia;
    }

    public Integer getBaixaMovAutomatico() {
        return baixaMovAutomatico;
    }

    public void setBaixaMovAutomatico(Integer baixaMovAutomatico) {
        this.baixaMovAutomatico = baixaMovAutomatico;
    }

    public Integer getAguaFirebird() {
        return aguaFirebird;
    }

    public void setAguaFirebird(Integer aguaFirebird) {
        this.aguaFirebird = aguaFirebird;
    }

    public String getFebrabanLocal() {
        return febrabanLocal;
    }

    public void setFebrabanLocal(String febrabanLocal) {
        this.febrabanLocal = febrabanLocal;
    }

    public String getFebrabanObservacao() {
        return febrabanObservacao;
    }

    public void setFebrabanObservacao(String febrabanObservacao) {
        this.febrabanObservacao = febrabanObservacao;
    }

    public Integer getItbiInscricaoCadastral() {
        return itbiInscricaoCadastral;
    }

    public void setItbiInscricaoCadastral(Integer itbiInscricaoCadastral) {
        this.itbiInscricaoCadastral = itbiInscricaoCadastral;
    }

    public Integer getPermitirCertidaoNegativa() {
        return permitirCertidaoNegativa;
    }

    public void setPermitirCertidaoNegativa(Integer permitirCertidaoNegativa) {
        this.permitirCertidaoNegativa = permitirCertidaoNegativa;
    }

    public Integer getBloquearFebraban() {
        return bloquearFebraban;
    }

    public void setBloquearFebraban(Integer bloquearFebraban) {
        this.bloquearFebraban = bloquearFebraban;
    }

    public Integer getIptuRemessa() {
        return iptuRemessa;
    }

    public void setIptuRemessa(Integer iptuRemessa) {
        this.iptuRemessa = iptuRemessa;
    }

    public Integer getIssqnRemessa() {
        return issqnRemessa;
    }

    public void setIssqnRemessa(Integer issqnRemessa) {
        this.issqnRemessa = issqnRemessa;
    }

    public Integer getDividaRemessa() {
        return dividaRemessa;
    }

    public void setDividaRemessa(Integer dividaRemessa) {
        this.dividaRemessa = dividaRemessa;
    }

    public Integer getReceitaRemessa() {
        return receitaRemessa;
    }

    public void setReceitaRemessa(Integer receitaRemessa) {
        this.receitaRemessa = receitaRemessa;
    }

    public Integer getLoteSequencial() {
        return loteSequencial;
    }

    public void setLoteSequencial(Integer loteSequencial) {
        this.loteSequencial = loteSequencial;
    }

    public String getCpfChefe() {
        return cpfChefe;
    }

    public void setCpfChefe(String cpfChefe) {
        this.cpfChefe = cpfChefe;
    }

    public String getRgChefe() {
        return rgChefe;
    }

    public void setRgChefe(String rgChefe) {
        this.rgChefe = rgChefe;
    }

    public Integer getTipoJuros() {
        return tipoJuros;
    }

    public void setTipoJuros(Integer tipoJuros) {
        this.tipoJuros = tipoJuros;
    }

    public Integer getCalculoCorrecao() {
        return calculoCorrecao;
    }

    public void setCalculoCorrecao(Integer calculoCorrecao) {
        this.calculoCorrecao = calculoCorrecao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdTribunal() {
        return idTribunal;
    }

    public void setIdTribunal(Integer idTribunal) {
        this.idTribunal = idTribunal;
    }

    public Integer getIdSiafi() {
        return idSiafi;
    }

    public void setIdSiafi(Integer idSiafi) {
        this.idSiafi = idSiafi;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdIbge() {
        return idIbge;
    }

    public void setIdIbge(String idIbge) {
        this.idIbge = idIbge;
    }

    public Cidade getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Cidade idCidade) {
        this.idCidade = idCidade;
    }

    public String getFiscalRendas() {
        return fiscalRendas;
    }

    public void setFiscalRendas(String fiscalRendas) {
        this.fiscalRendas = fiscalRendas;
    }

    public String getChefeEmpreendedorismo() {
        return chefeEmpreendedorismo;
    }

    public void setChefeEmpreendedorismo(String chefeEmpreendedorismo) {
        this.chefeEmpreendedorismo = chefeEmpreendedorismo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.idOrgao);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContabilOrgao other = (ContabilOrgao) obj;
        if (!Objects.equals(this.idOrgao, other.idOrgao)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ContabilOrgao{" + "idOrgao=" + idOrgao + '}';
    }

}
