/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.admin.ContabilOrgao;
import br.com.eddydata.entidade.geral.Banco;
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
@Table(name = "issqn_configuracao")
public class IssqnConfiguracao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "febraban")
    private Integer febraban;
    @Column(name = "num_alvorada")
    private Integer numAlvorada;
    @Column(name = "mascara", length = 30)
    private String mascara;
    @Column(name = "local_arquivo", length = 150)
    private String localArquivo;
    @Column(name = "local_pagamento", length = 150)
    private String localPagamento;
    @Column(name = "modelo_carne")
    private Integer modeloCarne;
    @Column(name = "modelo_alvara")
    private Integer modeloAlvara;
    @Column(name = "gera_codigo", length = 5)
    private String geraCodigo;
    @Column(name = "separador", length = 1)
    private String separador;
    @Column(name = "alvara_exercicio", length = 5)
    private String alvaraExercicio;
    @Column(name = "carta", length = 5000)
    private String carta;
    @Column(name = "url_importar", length = 50)
    private String urlImportar;
    @Column(name = "url_exportar", length = 50)
    private String urlExportar;
    @Column(name = "local_importar", length = 150)
    private String localImportar;
    @Column(name = "local_exportar", length = 150)
    private String localExportar;
    @Column(name = "nome_1", length = 40)
    private String nome1;
    @Column(name = "cargo_1", length = 40)
    private String cargo1;
    @Column(name = "nome_2", length = 40)
    private String nome2;
    @Column(name = "cargo_2", length = 40)
    private String cargo2;
    @Column(name = "cobra_expediente")
    private Integer cobraExpediente;
    @Column(name = "alvara_diversao", length = 200)
    private String alvaraDiversao;
    @Column(name = "modelo_carta")
    private Integer modeloCarta;
    @Column(name = "tipo_boleto")
    private Integer tipoBoleto;
    @Column(name = "boleto")
    private Integer boleto;
    @Column(name = "id_tipo_cobranca")
    private Integer idTipoCobranca;
    @Column(name = "tp_parcelas_alvara")
    private Integer tpParcelasAlvara;
    @Column(name = "dt_proporcao_alvara")
    @Temporal(TemporalType.DATE)
    private Date dtProporcaoAlvara;
    @Column(name = "is_fixo")
    private Short isFixo;
    @Column(name = "usacarne_tresvias")
    private Integer usacarneTresvias;
    @Column(name = "numero_certidao")
    private Integer numeroCertidao;
    @Column(name = "modelo_certidao")
    private Integer modeloCertidao;
    @Column(name = "atribuicao_automatica_insc")
    private Integer atribuicaoAutomaticaInsc;
    @Column(name = "lei_termo_fiscalizacao", length = 100)
    private String leiTermoFiscalizacao;
    @Column(name = "observacao_diversao", length = 5000)
    private String observacaoDiversao;
    @Column(name = "escritorio_responsavel", length = 50)
    private String escritorioResponsavel;
    @Column(name = "utiliza_inscricao_municipal")
    private Integer utilizaInscricaoMunicipal;
    @Column(name = "sequencia_n_alvara")
    private Integer sequenciaNAlvara;
    @Column(name = "utiliza_alvara_sequencial")
    private Integer utilizaAlvaraSequencial;
    @Column(name = "trocar_nomenclatura_alvara")
    private Integer trocarNomenclaturaAlvara;
    @Column(name = "id_banco")
    private Integer idBanco;
    @Column(name = "end_ceat", length = 90)
    private String endCeat;
    @Column(name = "agrupar_boleto")
    private Integer agruparBoleto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orgao", referencedColumnName = "id_orgao")
    private ContabilOrgao orgao;
    @JoinColumn(name = "id_banco_boleto", referencedColumnName = "id_banco")
    @ManyToOne(fetch = FetchType.LAZY)
    private Banco bancoBoleto;
    @Column(name = "ufm")
    private Double ufm;
    @Column(name = "ufesp")
    private Double ufesp;
    @Column(name = "expediente")
    private Double expediente;
    @Column(name = "sequencia_n_protocolo")
    private Integer sequenciaNProtocolo;
    @Column(name = "sequencia_n_licenca")
    private Integer sequenciaNLicenca;
    @Column(name = "sequencia_n_certidao")
    private Integer sequenciaNCertidao;
    @Column(name = "vl_multa")
    private Double vlMulta;
    @Column(name = "vl_juros")
    private Double vlJuros;

    public IssqnConfiguracao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFebraban() {
        return febraban;
    }

    public void setFebraban(Integer febraban) {
        this.febraban = febraban;
    }

    public Integer getNumAlvorada() {
        return numAlvorada;
    }

    public void setNumAlvorada(Integer numAlvorada) {
        this.numAlvorada = numAlvorada;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public String getLocalArquivo() {
        return localArquivo;
    }

    public void setLocalArquivo(String localArquivo) {
        this.localArquivo = localArquivo;
    }

    public String getLocalPagamento() {
        return localPagamento;
    }

    public void setLocalPagamento(String localPagamento) {
        this.localPagamento = localPagamento;
    }

    public Integer getModeloCarne() {
        return modeloCarne;
    }

    public void setModeloCarne(Integer modeloCarne) {
        this.modeloCarne = modeloCarne;
    }

    public Integer getModeloAlvara() {
        return modeloAlvara;
    }

    public void setModeloAlvara(Integer modeloAlvara) {
        this.modeloAlvara = modeloAlvara;
    }

    public String getGeraCodigo() {
        return geraCodigo;
    }

    public void setGeraCodigo(String geraCodigo) {
        this.geraCodigo = geraCodigo;
    }

    public String getSeparador() {
        return separador;
    }

    public void setSeparador(String separador) {
        this.separador = separador;
    }

    public String getAlvaraExercicio() {
        return alvaraExercicio;
    }

    public void setAlvaraExercicio(String alvaraExercicio) {
        this.alvaraExercicio = alvaraExercicio;
    }

    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public String getUrlImportar() {
        return urlImportar;
    }

    public void setUrlImportar(String urlImportar) {
        this.urlImportar = urlImportar;
    }

    public String getUrlExportar() {
        return urlExportar;
    }

    public void setUrlExportar(String urlExportar) {
        this.urlExportar = urlExportar;
    }

    public String getLocalImportar() {
        return localImportar;
    }

    public void setLocalImportar(String localImportar) {
        this.localImportar = localImportar;
    }

    public String getLocalExportar() {
        return localExportar;
    }

    public void setLocalExportar(String localExportar) {
        this.localExportar = localExportar;
    }

    public String getNome1() {
        return nome1;
    }

    public void setNome1(String nome1) {
        this.nome1 = nome1;
    }

    public String getCargo1() {
        return cargo1;
    }

    public void setCargo1(String cargo1) {
        this.cargo1 = cargo1;
    }

    public String getNome2() {
        return nome2;
    }

    public void setNome2(String nome2) {
        this.nome2 = nome2;
    }

    public String getCargo2() {
        return cargo2;
    }

    public void setCargo2(String cargo2) {
        this.cargo2 = cargo2;
    }

    public Integer getCobraExpediente() {
        return cobraExpediente;
    }

    public void setCobraExpediente(Integer cobraExpediente) {
        this.cobraExpediente = cobraExpediente;
    }

    public String getAlvaraDiversao() {
        return alvaraDiversao;
    }

    public void setAlvaraDiversao(String alvaraDiversao) {
        this.alvaraDiversao = alvaraDiversao;
    }

    public Integer getModeloCarta() {
        return modeloCarta;
    }

    public void setModeloCarta(Integer modeloCarta) {
        this.modeloCarta = modeloCarta;
    }

    public Integer getTipoBoleto() {
        return tipoBoleto;
    }

    public void setTipoBoleto(Integer tipoBoleto) {
        this.tipoBoleto = tipoBoleto;
    }

    public Integer getBoleto() {
        return boleto;
    }

    public void setBoleto(Integer boleto) {
        this.boleto = boleto;
    }

    public ContabilOrgao getOrgao() {
        return orgao;
    }

    public void setOrgao(ContabilOrgao orgao) {
        this.orgao = orgao;
    }

    public Integer getIdTipoCobranca() {
        return idTipoCobranca;
    }

    public void setIdTipoCobranca(Integer idTipoCobranca) {
        this.idTipoCobranca = idTipoCobranca;
    }

    public Integer getTpParcelasAlvara() {
        return tpParcelasAlvara;
    }

    public void setTpParcelasAlvara(Integer tpParcelasAlvara) {
        this.tpParcelasAlvara = tpParcelasAlvara;
    }

    public Date getDtProporcaoAlvara() {
        return dtProporcaoAlvara;
    }

    public void setDtProporcaoAlvara(Date dtProporcaoAlvara) {
        this.dtProporcaoAlvara = dtProporcaoAlvara;
    }

    public Short getIsFixo() {
        return isFixo;
    }

    public void setIsFixo(Short isFixo) {
        this.isFixo = isFixo;
    }

    public Integer getUsacarneTresvias() {
        return usacarneTresvias;
    }

    public void setUsacarneTresvias(Integer usacarneTresvias) {
        this.usacarneTresvias = usacarneTresvias;
    }

    public Integer getNumeroCertidao() {
        return numeroCertidao;
    }

    public void setNumeroCertidao(Integer numeroCertidao) {
        this.numeroCertidao = numeroCertidao;
    }

    public Integer getModeloCertidao() {
        return modeloCertidao;
    }

    public void setModeloCertidao(Integer modeloCertidao) {
        this.modeloCertidao = modeloCertidao;
    }

    public Integer getAtribuicaoAutomaticaInsc() {
        return atribuicaoAutomaticaInsc;
    }

    public void setAtribuicaoAutomaticaInsc(Integer atribuicaoAutomaticaInsc) {
        this.atribuicaoAutomaticaInsc = atribuicaoAutomaticaInsc;
    }

    public String getLeiTermoFiscalizacao() {
        return leiTermoFiscalizacao;
    }

    public void setLeiTermoFiscalizacao(String leiTermoFiscalizacao) {
        this.leiTermoFiscalizacao = leiTermoFiscalizacao;
    }

    public String getObservacaoDiversao() {
        return observacaoDiversao;
    }

    public void setObservacaoDiversao(String observacaoDiversao) {
        this.observacaoDiversao = observacaoDiversao;
    }

    public String getEscritorioResponsavel() {
        return escritorioResponsavel;
    }

    public void setEscritorioResponsavel(String escritorioResponsavel) {
        this.escritorioResponsavel = escritorioResponsavel;
    }

    public Integer getUtilizaInscricaoMunicipal() {
        return utilizaInscricaoMunicipal;
    }

    public void setUtilizaInscricaoMunicipal(Integer utilizaInscricaoMunicipal) {
        this.utilizaInscricaoMunicipal = utilizaInscricaoMunicipal;
    }

    public Integer getSequenciaNAlvara() {
        return sequenciaNAlvara;
    }

    public void setSequenciaNAlvara(Integer sequenciaNAlvara) {
        this.sequenciaNAlvara = sequenciaNAlvara;
    }

    public Integer getUtilizaAlvaraSequencial() {
        return utilizaAlvaraSequencial;
    }

    public void setUtilizaAlvaraSequencial(Integer utilizaAlvaraSequencial) {
        this.utilizaAlvaraSequencial = utilizaAlvaraSequencial;
    }

    public Integer getTrocarNomenclaturaAlvara() {
        return trocarNomenclaturaAlvara;
    }

    public void setTrocarNomenclaturaAlvara(Integer trocarNomenclaturaAlvara) {
        this.trocarNomenclaturaAlvara = trocarNomenclaturaAlvara;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getEndCeat() {
        return endCeat;
    }

    public void setEndCeat(String endCeat) {
        this.endCeat = endCeat;
    }

    public Integer getAgruparBoleto() {
        return agruparBoleto;
    }

    public void setAgruparBoleto(Integer agruparBoleto) {
        this.agruparBoleto = agruparBoleto;
    }

    public Banco getBancoBoleto() {
        return bancoBoleto;
    }

    public void setBancoBoleto(Banco bancoBoleto) {
        this.bancoBoleto = bancoBoleto;
    }

    public Double getUfm() {
        return ufm;
    }

    public void setUfm(Double ufm) {
        this.ufm = ufm;
    }

    public Double getUfesp() {
        return ufesp;
    }

    public void setUfesp(Double ufesp) {
        this.ufesp = ufesp;
    }

    public Double getExpediente() {
        return expediente;
    }

    public void setExpediente(Double expediente) {
        this.expediente = expediente;
    }

    public Integer getSequenciaNProtocolo() {
        return sequenciaNProtocolo;
    }

    public void setSequenciaNProtocolo(Integer sequenciaNProtocolo) {
        this.sequenciaNProtocolo = sequenciaNProtocolo;
    }

    public Integer getSequenciaNLicenca() {
        return sequenciaNLicenca;
    }

    public void setSequenciaNLicenca(Integer sequenciaNLicenca) {
        this.sequenciaNLicenca = sequenciaNLicenca;
    }

    public Integer getSequenciaNCertidao() {
        return sequenciaNCertidao;
    }

    public void setSequenciaNCertidao(Integer sequenciaNCertidao) {
        this.sequenciaNCertidao = sequenciaNCertidao;
    }

    public Double getVlMulta() {
        return vlMulta;
    }

    public void setVlMulta(Double vlMulta) {
        this.vlMulta = vlMulta;
    }

    public Double getVlJuros() {
        return vlJuros;
    }

    public void setVlJuros(Double vlJuros) {
        this.vlJuros = vlJuros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (febraban != null ? febraban.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IssqnConfiguracao)) {
            return false;
        }
        IssqnConfiguracao other = (IssqnConfiguracao) object;
        if ((this.febraban == null && other.febraban != null) || (this.febraban != null && !this.febraban.equals(other.febraban))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnConfiguracao[ febraban=" + febraban + " ]";
    }
}
