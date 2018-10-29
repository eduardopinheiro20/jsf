/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.entidade.geral.Pessoa;
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
@Table(name = "issqn")
public class Issqn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_iss")
    private Integer id;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "cep", length = 20)
    private String cep;
    @Column(name = "complemento", length = 20)
    private String complemento;
    @Column(name = "dt_abertura")
    @Temporal(TemporalType.DATE)
    private Date dtAbertura;
    @Column(name = "dt_inatividade")
    @Temporal(TemporalType.DATE)
    private Date dtInatividade;
    @Column(name = "dt_encerramento")
    @Temporal(TemporalType.DATE)
    private Date dtEncerramento;
    @Column(name = "proc_abertura", length = 10)
    private String procAbertura;
    @Column(name = "proc_encerra", length = 10)
    private String procEncerra;
    @Column(name = "num_alvara", length = 20)
    private String numAlvara;
    @Column(name = "observacao", length = 5000)
    private String observacao;
    @Column(name = "cadastro", length = 20)
    private String cadastro;
    @Column(name = "capital_social")
    private Double capitalSocial;
    @Column(name = "dt_transferencia")
    @Temporal(TemporalType.DATE)
    private Date dtTransferencia;
    @Column(name = "id_identificacao")
    private Integer idIdentificacao;
    @Column(name = "dt_alvara_vencimento")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraVencimento;
    @Column(name = "internet", length = 4)
    private String internet;
    @Column(name = "sequencia_alvara")
    private Integer sequenciaAlvara;
    @Column(name = "obs_alvara", length = 5000)
    private String obsAlvara;
    @Column(name = "dt_inatividade_comunicacao")
    @Temporal(TemporalType.DATE)
    private Date dtInatividadeComunicacao;
    @Column(name = "dt_encerramento_comunicacao")
    @Temporal(TemporalType.DATE)
    private Date dtEncerramentoComunicacao;
    @Column(name = "inscricao", length = 20)
    private String inscricao;
    @Column(name = "id_imovel_cobranca")
    private Integer idImovelCobranca;
    @Column(name = "inscricao_anterior", length = 20)
    private String inscricaoAnterior;
    @Column(name = "id_exercicio")
    private Integer idExercicio;
    @Column(name = "fantasia", length = 100)
    private String fantasia;
    @Column(name = "simples_nacional")
    private Boolean simplesNacional;
    @Column(name = "fone", length = 20)
    private String fone;
    @Column(name = "cnpj_cpf", length = 20)
    private String cnpjCpf;
    @Column(name = "contr_anterior", length = 20)
    private String contrAnterior;
    @Column(name = "processo_n", length = 20)
    private String processoN;
    @Column(name = "numero_funcionarios")
    private Integer numeroFuncionarios;
    @Column(name = "testada")
    private Double testada;
    @Column(name = "isento", length = 5)
    private String isento;
    @Column(name = "obs_simples", length = 5000)
    private String obsSimples;
    @Column(name = "dt_simples")
    @Temporal(TemporalType.DATE)
    private Date dtSimples;
    @Column(name = "status_carne")
    private Integer statusCarne;
    @Column(name = "vl_estimado")
    private Double vlEstimado;
    @Column(name = "horario_especial")
    private Boolean horarioEspecial;
    @Column(name = "faturamento_estimado")
    private Double faturamentoEstimado;
    @Column(name = "area_construida")
    private Double areaConstruida;
    @Column(name = "area_terreno")
    private Double areaTerreno;
    @Column(name = "area_imovel")
    private Double areaImovel;
    @Column(name = "area_estabelecimento")
    private Double areaEstabelecimento;
    @Column(name = "numero_registro_conselho")
    private Integer numeroRegistroConselho;
    @Column(name = "inscr_estadual", length = 30)
    private String inscrEstadual;
    @Column(name = "inativo")
    private Boolean inativo;
    @Column(name = "encerrado")
    private Boolean encerrado;
    @Column(name = "auto_lancado")
    private Boolean autoLancado;
    @Column(name = "eventual")
    public Boolean eventual;
    @Column(name = "bloqueado")
    private Boolean bloqueado;
    @Column(name = "dt_suspensao_inicio")
    @Temporal(TemporalType.DATE)
    private Date dtSuspensaoInicio;
    @Column(name = "dt_suspensao_fim")
    @Temporal(TemporalType.DATE)
    private Date dtSuspensaoFim;
    @Column(name = "dt_bloqueado")
    @Temporal(TemporalType.DATE)
    private Date dtBloqueado;
    @Column(name = "dt_inicio")
    @Temporal(TemporalType.DATE)
    private Date dtInicio;
    @Column(name = "qtd_ufm")
    private Double qtdUfm;
    @Column(name = "dt_alvara")
    @Temporal(TemporalType.DATE)
    private Date dtAlvara;
    @Column(name = "alvara_provisorio")
    private Boolean alvaraProvisorio;
    @Column(name = "intr_simpliss")
    private Integer intrSimpliss;
    @Column(name = "dt_intr_simpliss")
    @Temporal(TemporalType.DATE)
    private Date dtIntrSimpliss;
    @Column(name = "mei")
    private Boolean mei;
    @Column(name = "dt_mei")
    @Temporal(TemporalType.DATE)
    private Date dtMei;
    @Column(name = "cod_identificacao")
    private Integer codIdentificacao;
    @Column(name = "dt_reabertura")
    @Temporal(TemporalType.DATE)
    private Date dtReabertura;
    @Column(name = "dt_isento")
    @Temporal(TemporalType.DATE)
    private Date dtIsento;
    @Column(name = "tipo_predio", length = 500)
    private String tipoPredio;
    @Column(name = "pendente_fiscalizacao")
    private Boolean pendenteFiscalizacao;
    @Column(name = "pendente_documentacao")
    private Boolean pendenteDocumentacao;
    @Column(name = "dt_validade_alvara")
    @Temporal(TemporalType.DATE)
    private Date dtValidadeAlvara;
    @Column(name = "cod_iss", length = 20)
    private String codIss;
    @Column(name = "tmp_id_atividade")
    private Integer tmpIdAtividade;
    @Column(name = "nr_veiculos")
    private Integer nrVeiculos;
    @Column(name = "num_alvara_func", length = 20)
    private String numAlvaraFunc;
    @Column(name = "num_alvara_especial", length = 20)
    private String numAlvaraEspecial;
    @Column(name = "num_alvara_bombeiro", length = 20)
    private String numAlvaraBombeiro;
    @Column(name = "num_alvara_sanitaria", length = 20)
    private String numAlvaraSanitaria;
    @Column(name = "num_alvara_ambiental", length = 20)
    private String numAlvaraAmbiental;
    @Column(name = "num_alvara_anp", length = 20)
    private String numAlvaraAnp;
    @Column(name = "num_alvara_cetesb", length = 20)
    private String numAlvaraCetesb;
    @Column(name = "num_alvara_solo", length = 20)
    private String numAlvaraSolo;
    @Column(name = "num_alvara_outros", length = 20)
    private String numAlvaraOutros;
    @Column(name = "dt_alvara_func")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraFunc;
    @Column(name = "dt_alvara_especial")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraEspecial;
    @Column(name = "dt_alvara_bombeiro")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraBombeiro;
    @Column(name = "dt_alvara_sanitaria")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraSanitaria;
    @Column(name = "dt_alvara_ambiental")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraAmbiental;
    @Column(name = "dt_alvara_anp")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraAnp;
    @Column(name = "dt_alvara_cetesb")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraCetesb;
    @Column(name = "dt_alvara_solo")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraSolo;
    @Column(name = "dt_alvara_outros")
    @Temporal(TemporalType.DATE)
    private Date dtAlvaraOutros;
    @Column(name = "nr_junta_comercial", length = 20)
    private String nrJuntaComercial;
    @Column(name = "dt_junta_comercial")
    @Temporal(TemporalType.DATE)
    private Date dtJuntaComercial;
    @Column(name = "dt_eventual")
    @Temporal(TemporalType.DATE)
    private Date dtEventual;
    @Column(name = "protocolo_diversoes", length = 50)
    private String protocoloDiversoes;
    @Column(name = "protocolo_autonomo", length = 50)
    private String protocoloAutonomo;
    @Column(name = "protocolo_autonomo_domicilio_fiscal", length = 50)
    private String protocoloAutonomoDomicilioFiscal;
    @Column(name = "protocolo_funcionamento", length = 50)
    private String protocoloFuncionamento;
    @Column(name = "protocolo_provisorio", length = 50)
    private String protocoloProvisorio;
    @Column(name = "protocolo_baixa_inscricao", length = 50)
    private String protocoloBaixaInscricao;
    @Column(name = "protocolo_inativacao", length = 50)
    private String protocoloInativacao;
    @Column(name = "vl_bilheteria")
    private Double vlBilheteria;
    @Column(name = "eventualidade")
    private Boolean eventualidade;
    @Column(name = "passivel_vigilancia")
    private Boolean passivelVigilancia;
    @JoinColumn(name = "id_imovel", referencedColumnName = "id_imovel")
    @ManyToOne(fetch = FetchType.LAZY)
    private Imovel imovel;
    @JoinColumn(name = "id_atividade", referencedColumnName = "id_atividade")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnAtividade atividade;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnCategoria categoria;
    @JoinColumn(name = "id_conselho", referencedColumnName = "id_conselho")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnConselhoRegional conselho;
    @JoinColumn(name = "id_escritorio", referencedColumnName = "id_escritorio")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnEscritorio escritorio;
    @JoinColumn(name = "id_ramo_atuacao", referencedColumnName = "id_ramo_atuacao")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnRamoAtuacao ramoAtuacao;
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id_pessoa")
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa pessoa;
    @JoinColumn(name = "id_segmento", referencedColumnName = "id_segmento")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnSegmento segmento;
    @Column(name = "senha", length = 255)
    private String senha;
    @Column(name = "senha_base", length = 255)
    private String senhaBase; 
    @Column(name = "pendente")
    private Boolean pendente;

    @Column(name = "hora_especial", length = 20)
    private String horaEspecial;

    public Issqn() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Date getDtAbertura() {
        return dtAbertura;
    }

    public void setDtAbertura(Date dtAbertura) {
        this.dtAbertura = dtAbertura;
    }

    public Date getDtInatividade() {
        return dtInatividade;
    }

    public void setDtInatividade(Date dtInatividade) {
        this.dtInatividade = dtInatividade;
    }

    public Date getDtEncerramento() {
        return dtEncerramento;
    }

    public void setDtEncerramento(Date dtEncerramento) {
        this.dtEncerramento = dtEncerramento;
    }

    public String getProcAbertura() {
        return procAbertura;
    }

    public void setProcAbertura(String procAbertura) {
        this.procAbertura = procAbertura;
    }

    public String getProcEncerra() {
        return procEncerra;
    }

    public void setProcEncerra(String procEncerra) {
        this.procEncerra = procEncerra;
    }

    public String getNumAlvara() {
        return numAlvara;
    }

    public void setNumAlvara(String numAlvara) {
        this.numAlvara = numAlvara;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCadastro() {
        return cadastro;
    }

    public void setCadastro(String cadastro) {
        this.cadastro = cadastro;
    }

    public Double getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(Double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public Date getDtTransferencia() {
        return dtTransferencia;
    }

    public void setDtTransferencia(Date dtTransferencia) {
        this.dtTransferencia = dtTransferencia;
    }

    public Integer getIdIdentificacao() {
        return idIdentificacao;
    }

    public void setIdIdentificacao(Integer idIdentificacao) {
        this.idIdentificacao = idIdentificacao;
    }

    public Date getDtAlvaraVencimento() {
        return dtAlvaraVencimento;
    }

    public void setDtAlvaraVencimento(Date dtAlvaraVencimento) {
        this.dtAlvaraVencimento = dtAlvaraVencimento;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public Integer getSequenciaAlvara() {
        return sequenciaAlvara;
    }

    public void setSequenciaAlvara(Integer sequenciaAlvara) {
        this.sequenciaAlvara = sequenciaAlvara;
    }

    public String getObsAlvara() {
        return obsAlvara;
    }

    public void setObsAlvara(String obsAlvara) {
        this.obsAlvara = obsAlvara;
    }

    public Date getDtInatividadeComunicacao() {
        return dtInatividadeComunicacao;
    }

    public void setDtInatividadeComunicacao(Date dtInatividadeComunicacao) {
        this.dtInatividadeComunicacao = dtInatividadeComunicacao;
    }

    public Date getDtEncerramentoComunicacao() {
        return dtEncerramentoComunicacao;
    }

    public void setDtEncerramentoComunicacao(Date dtEncerramentoComunicacao) {
        this.dtEncerramentoComunicacao = dtEncerramentoComunicacao;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public Integer getIdImovelCobranca() {
        return idImovelCobranca;
    }

    public void setIdImovelCobranca(Integer idImovelCobranca) {
        this.idImovelCobranca = idImovelCobranca;
    }

    public String getInscricaoAnterior() {
        return inscricaoAnterior;
    }

    public void setInscricaoAnterior(String inscricaoAnterior) {
        this.inscricaoAnterior = inscricaoAnterior;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public Boolean getSimplesNacional() {
        return simplesNacional;
    }

    public void setSimplesNacional(Boolean simplesNacional) {
        this.simplesNacional = simplesNacional;
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

    public String getContrAnterior() {
        return contrAnterior;
    }

    public void setContrAnterior(String contrAnterior) {
        this.contrAnterior = contrAnterior;
    }

    public String getProcessoN() {
        return processoN;
    }

    public void setProcessoN(String processoN) {
        this.processoN = processoN;
    }

    public Integer getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(Integer numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public Double getTestada() {
        return testada;
    }

    public void setTestada(Double testada) {
        this.testada = testada;
    }

    public String getObsSimples() {
        return obsSimples;
    }

    public void setObsSimples(String obsSimples) {
        this.obsSimples = obsSimples;
    }

    public Date getDtSimples() {
        return dtSimples;
    }

    public void setDtSimples(Date dtSimples) {
        this.dtSimples = dtSimples;
    }

    public Integer getStatusCarne() {
        return statusCarne;
    }

    public void setStatusCarne(Integer statusCarne) {
        this.statusCarne = statusCarne;
    }

    public Double getVlEstimado() {
        return vlEstimado;
    }

    public void setVlEstimado(Double vlEstimado) {
        this.vlEstimado = vlEstimado;
    }

    public Boolean getHorarioEspecial() {
        return horarioEspecial;
    }

    public void setHorarioEspecial(Boolean horarioEspecial) {
        this.horarioEspecial = horarioEspecial;
    }

    public String getHoraEspecial() {
        return horaEspecial;
    }

    public void setHoraEspecial(String horaEspecial) {
        this.horaEspecial = horaEspecial;
    }

    public Double getFaturamentoEstimado() {
        return faturamentoEstimado;
    }

    public void setFaturamentoEstimado(Double faturamentoEstimado) {
        this.faturamentoEstimado = faturamentoEstimado;
    }

    public Double getAreaConstruida() {
        return areaConstruida;
    }

    public void setAreaConstruida(Double areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public Integer getNumeroRegistroConselho() {
        return numeroRegistroConselho;
    }

    public void setNumeroRegistroConselho(Integer numeroRegistroConselho) {
        this.numeroRegistroConselho = numeroRegistroConselho;
    }

    public String getInscrEstadual() {
        return inscrEstadual;
    }

    public void setInscrEstadual(String inscrEstadual) {
        this.inscrEstadual = inscrEstadual;
    }

    public Date getDtSuspensaoInicio() {
        return dtSuspensaoInicio;
    }

    public void setDtSuspensaoInicio(Date dtSuspensaoInicio) {
        this.dtSuspensaoInicio = dtSuspensaoInicio;
    }

    public Date getDtSuspensaoFim() {
        return dtSuspensaoFim;
    }

    public void setDtSuspensaoFim(Date dtSuspensaoFim) {
        this.dtSuspensaoFim = dtSuspensaoFim;
    }

    public Date getDtBloqueado() {
        return dtBloqueado;
    }

    public void setDtBloqueado(Date dtBloqueado) {
        this.dtBloqueado = dtBloqueado;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Double getQtdUfm() {
        return qtdUfm;
    }

    public void setQtdUfm(Double qtdUfm) {
        this.qtdUfm = qtdUfm;
    }

    public Date getDtAlvara() {
        return dtAlvara;
    }

    public void setDtAlvara(Date dtAlvara) {
        this.dtAlvara = dtAlvara;
    }

    public Boolean getAlvaraProvisorio() {
        return alvaraProvisorio;
    }

    public void setAlvaraProvisorio(Boolean alvaraProvisorio) {
        this.alvaraProvisorio = alvaraProvisorio;
    }

    public Integer getIntrSimpliss() {
        return intrSimpliss;
    }

    public void setIntrSimpliss(Integer intrSimpliss) {
        this.intrSimpliss = intrSimpliss;
    }

    public Date getDtIntrSimpliss() {
        return dtIntrSimpliss;
    }

    public void setDtIntrSimpliss(Date dtIntrSimpliss) {
        this.dtIntrSimpliss = dtIntrSimpliss;
    }

    public String getIsento() {
        return isento;
    }

    public void setIsento(String isento) {
        this.isento = isento;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Boolean getEncerrado() {
        return encerrado;
    }

    public void setEncerrado(Boolean encerrado) {
        this.encerrado = encerrado;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Boolean getMei() {
        return mei;
    }

    public void setMei(Boolean mei) {
        this.mei = mei;
    }

    public Date getDtMei() {
        return dtMei;
    }

    public void setDtMei(Date dtMei) {
        this.dtMei = dtMei;
    }

    public Integer getCodIdentificacao() {
        return codIdentificacao;
    }

    public void setCodIdentificacao(Integer codIdentificacao) {
        this.codIdentificacao = codIdentificacao;
    }

    public Date getDtReabertura() {
        return dtReabertura;
    }

    public void setDtReabertura(Date dtReabertura) {
        this.dtReabertura = dtReabertura;
    }

    public Date getDtIsento() {
        return dtIsento;
    }

    public void setDtIsento(Date dtIsento) {
        this.dtIsento = dtIsento;
    }

    public String getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(String tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

    public Boolean getAutoLancado() {
        return autoLancado;
    }

    public void setAutoLancado(Boolean autoLancado) {
        this.autoLancado = autoLancado;
    }

    public Boolean getEventual() {
        return eventual;
    }

    public void setEventual(Boolean eventual) {
        this.eventual = eventual;
    }

    public Boolean getPendenteFiscalizacao() {
        return pendenteFiscalizacao;
    }

    public void setPendenteFiscalizacao(Boolean pendenteFiscalizacao) {
        this.pendenteFiscalizacao = pendenteFiscalizacao;
    }

    public Boolean getPendenteDocumentacao() {
        return pendenteDocumentacao;
    }

    public void setPendenteDocumentacao(Boolean pendenteDocumentacao) {
        this.pendenteDocumentacao = pendenteDocumentacao;
    }

    public Date getDtValidadeAlvara() {
        return dtValidadeAlvara;
    }

    public void setDtValidadeAlvara(Date dtValidadeAlvara) {
        this.dtValidadeAlvara = dtValidadeAlvara;
    }

    public String getCodIss() {
        return codIss;
    }

    public void setCodIss(String codIss) {
        this.codIss = codIss;
    }

    public Integer getTmpIdAtividade() {
        return tmpIdAtividade;
    }

    public void setTmpIdAtividade(Integer tmpIdAtividade) {
        this.tmpIdAtividade = tmpIdAtividade;
    }

    public Integer getNrVeiculos() {
        return nrVeiculos;
    }

    public void setNrVeiculos(Integer nrVeiculos) {
        this.nrVeiculos = nrVeiculos;
    }

    public String getNumAlvaraFunc() {
        return numAlvaraFunc;
    }

    public void setNumAlvaraFunc(String numAlvaraFunc) {
        this.numAlvaraFunc = numAlvaraFunc;
    }

    public String getNumAlvaraEspecial() {
        return numAlvaraEspecial;
    }

    public void setNumAlvaraEspecial(String numAlvaraEspecial) {
        this.numAlvaraEspecial = numAlvaraEspecial;
    }

    public String getNumAlvaraBombeiro() {
        return numAlvaraBombeiro;
    }

    public void setNumAlvaraBombeiro(String numAlvaraBombeiro) {
        this.numAlvaraBombeiro = numAlvaraBombeiro;
    }

    public String getNumAlvaraSanitaria() {
        return numAlvaraSanitaria;
    }

    public void setNumAlvaraSanitaria(String numAlvaraSanitaria) {
        this.numAlvaraSanitaria = numAlvaraSanitaria;
    }

    public String getNumAlvaraAmbiental() {
        return numAlvaraAmbiental;
    }

    public void setNumAlvaraAmbiental(String numAlvaraAmbiental) {
        this.numAlvaraAmbiental = numAlvaraAmbiental;
    }

    public String getNumAlvaraAnp() {
        return numAlvaraAnp;
    }

    public void setNumAlvaraAnp(String numAlvaraAnp) {
        this.numAlvaraAnp = numAlvaraAnp;
    }

    public String getNumAlvaraCetesb() {
        return numAlvaraCetesb;
    }

    public void setNumAlvaraCetesb(String numAlvaraCetesb) {
        this.numAlvaraCetesb = numAlvaraCetesb;
    }

    public String getNumAlvaraSolo() {
        return numAlvaraSolo;
    }

    public void setNumAlvaraSolo(String numAlvaraSolo) {
        this.numAlvaraSolo = numAlvaraSolo;
    }

    public String getNumAlvaraOutros() {
        return numAlvaraOutros;
    }

    public void setNumAlvaraOutros(String numAlvaraOutros) {
        this.numAlvaraOutros = numAlvaraOutros;
    }

    public Date getDtAlvaraFunc() {
        return dtAlvaraFunc;
    }

    public void setDtAlvaraFunc(Date dtAlvaraFunc) {
        this.dtAlvaraFunc = dtAlvaraFunc;
    }

    public Date getDtAlvaraEspecial() {
        return dtAlvaraEspecial;
    }

    public void setDtAlvaraEspecial(Date dtAlvaraEspecial) {
        this.dtAlvaraEspecial = dtAlvaraEspecial;
    }

    public Date getDtAlvaraBombeiro() {
        return dtAlvaraBombeiro;
    }

    public void setDtAlvaraBombeiro(Date dtAlvaraBombeiro) {
        this.dtAlvaraBombeiro = dtAlvaraBombeiro;
    }

    public Date getDtAlvaraSanitaria() {
        return dtAlvaraSanitaria;
    }

    public void setDtAlvaraSanitaria(Date dtAlvaraSanitaria) {
        this.dtAlvaraSanitaria = dtAlvaraSanitaria;
    }

    public Date getDtAlvaraAmbiental() {
        return dtAlvaraAmbiental;
    }

    public void setDtAlvaraAmbiental(Date dtAlvaraAmbiental) {
        this.dtAlvaraAmbiental = dtAlvaraAmbiental;
    }

    public Date getDtAlvaraAnp() {
        return dtAlvaraAnp;
    }

    public void setDtAlvaraAnp(Date dtAlvaraAnp) {
        this.dtAlvaraAnp = dtAlvaraAnp;
    }

    public Date getDtAlvaraCetesb() {
        return dtAlvaraCetesb;
    }

    public void setDtAlvaraCetesb(Date dtAlvaraCetesb) {
        this.dtAlvaraCetesb = dtAlvaraCetesb;
    }

    public Date getDtAlvaraSolo() {
        return dtAlvaraSolo;
    }

    public void setDtAlvaraSolo(Date dtAlvaraSolo) {
        this.dtAlvaraSolo = dtAlvaraSolo;
    }

    public Date getDtAlvaraOutros() {
        return dtAlvaraOutros;
    }

    public void setDtAlvaraOutros(Date dtAlvaraOutros) {
        this.dtAlvaraOutros = dtAlvaraOutros;
    }

    public String getNrJuntaComercial() {
        return nrJuntaComercial;
    }

    public void setNrJuntaComercial(String nrJuntaComercial) {
        this.nrJuntaComercial = nrJuntaComercial;
    }

    public Date getDtJuntaComercial() {
        return dtJuntaComercial;
    }

    public void setDtJuntaComercial(Date dtJuntaComercial) {
        this.dtJuntaComercial = dtJuntaComercial;
    }

    public Imovel getImovel() {
        if (imovel == null) {
            imovel = new Imovel();
        }
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public IssqnAtividade getAtividade() {
        return atividade;
    }

    public void setAtividade(IssqnAtividade atividade) {
        this.atividade = atividade;
    }

    public IssqnCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(IssqnCategoria categoria) {
        this.categoria = categoria;
    }

    public IssqnConselhoRegional getConselho() {
        return conselho;
    }

    public void setConselho(IssqnConselhoRegional conselho) {
        this.conselho = conselho;
    }

    public IssqnEscritorio getEscritorio() {
        return escritorio;
    }

    public void setEscritorio(IssqnEscritorio escritorio) {
        this.escritorio = escritorio;
    }

    public IssqnRamoAtuacao getRamoAtuacao() {
        return ramoAtuacao;
    }

    public void setRamoAtuacao(IssqnRamoAtuacao ramoAtuacao) {
        this.ramoAtuacao = ramoAtuacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public IssqnSegmento getSegmento() {
        return segmento;
    }

    public void setSegmento(IssqnSegmento segmento) {
        this.segmento = segmento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaBase() {
        return senhaBase;
    }

    public void setSenhaBase(String senhaBase) {
        this.senhaBase = senhaBase;
    }

    public Boolean getPendente() {
        return pendente;
    }

    public void setPendente(Boolean pendente) {
        this.pendente = pendente;
    }

    public Double getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(Double areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public Double getAreaImovel() {
        return areaImovel;
    }

    public void setAreaImovel(Double areaImovel) {
        this.areaImovel = areaImovel;
    }

    public Double getAreaEstabelecimento() {
        return areaEstabelecimento;
    }

    public void setAreaEstabelecimento(Double areaEstabelecimento) {
        this.areaEstabelecimento = areaEstabelecimento;
    }

    public Date getDtEventual() {
        return dtEventual;
    }

    public void setDtEventual(Date dtEventual) {
        this.dtEventual = dtEventual;
    }

    public String getProtocoloDiversoes() {
        return protocoloDiversoes;
    }

    public void setProtocoloDiversoes(String protocoloDiversoes) {
        this.protocoloDiversoes = protocoloDiversoes;
    }

    public String getProtocoloAutonomo() {
        return protocoloAutonomo;
    }

    public void setProtocoloAutonomo(String protocoloAutonomo) {
        this.protocoloAutonomo = protocoloAutonomo;
    }

    public String getProtocoloAutonomoDomicilioFiscal() {
        return protocoloAutonomoDomicilioFiscal;
    }

    public void setProtocoloAutonomoDomicilioFiscal(String protocoloAutonomoDomicilioFiscal) {
        this.protocoloAutonomoDomicilioFiscal = protocoloAutonomoDomicilioFiscal;
    }

    public String getProtocoloFuncionamento() {
        return protocoloFuncionamento;
    }

    public void setProtocoloFuncionamento(String protocoloFuncionamento) {
        this.protocoloFuncionamento = protocoloFuncionamento;
    }

    public String getProtocoloBaixaInscricao() {
        return protocoloBaixaInscricao;
    }

    public void setProtocoloBaixaInscricao(String protocoloBaixaInscricao) {
        this.protocoloBaixaInscricao = protocoloBaixaInscricao;
    }

    public String getProtocoloInativacao() {
        return protocoloInativacao;
    }

    public void setProtocoloInativacao(String protocoloInativacao) {
        this.protocoloInativacao = protocoloInativacao;
    }

    public Double getVlBilheteria() {
        return vlBilheteria;
    }

    public void setVlBilheteria(Double vlBilheteria) {
        this.vlBilheteria = vlBilheteria;
    }

    public String getProtocoloProvisorio() {
        return protocoloProvisorio;
    }

    public void setProtocoloProvisorio(String protocoloProvisorio) {
        this.protocoloProvisorio = protocoloProvisorio;
    }

    public Boolean getEventualidade() {
        return eventualidade;
    }

    public void setEventualidade(Boolean eventualidade) {
        this.eventualidade = eventualidade;
    }

    public Boolean getPassivelVigilancia() {
        return passivelVigilancia;
    }

    public void setPassivelVigilancia(Boolean passivelVigilancia) {
        this.passivelVigilancia = passivelVigilancia;
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
        if (!(object instanceof Issqn)) {
            return false;
        }
        Issqn other = (Issqn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Issqn[ idIss=" + id + " ]";
    }

}
