/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.folha;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lucas
 */
@Entity
@Table(name = "FUNCIONARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionarios.findAll", query = "SELECT f FROM Funcionarios f")})
public class Funcionarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FuncionariosPK funcionariosPK;
    @Size(max = 40)
    @Column(name = "NOME_FUNCIONARIO")
    private String nomeFuncionario;
    @Size(max = 40)
    @Column(name = "ENDERECO")
    private String endereco;
    @Size(max = 5)
    @Column(name = "NUMERO")
    private String numero;
    @Size(max = 20)
    @Column(name = "BAIRRO")
    private String bairro;
    @Size(max = 20)
    @Column(name = "CIDADE")
    private String cidade;
    @Size(max = 9)
    @Column(name = "CEP")
    private String cep;
    @Size(max = 2)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 20)
    @Column(name = "TELEFONE")
    private String telefone;
    @Size(max = 20)
    @Column(name = "CELULAR")
    private String celular;
    @Size(max = 40)
    @Column(name = "TELEFONE_CONTATO")
    private String telefoneContato;
    @Size(max = 1)
    @Column(name = "SEXO")
    private String sexo;
    @Size(max = 40)
    @Column(name = "PAI")
    private String pai;
    @Size(max = 40)
    @Column(name = "MAE")
    private String mae;
    @Column(name = "DATA_NASCIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;
    @Size(max = 25)
    @Column(name = "LOCAL_NASCIMENTO")
    private String localNascimento;
    @Size(max = 15)
    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;
    @Size(max = 40)
    @Column(name = "CONJUGE")
    private String conjuge;
    @Column(name = "IDIOMAS")
    private Short idiomas;
    @Size(max = 40)
    @Column(name = "ESPECIALIZACAO")
    private String especializacao;
    @Size(max = 7)
    @Column(name = "CARTEIRA_PROFISSIONAL")
    private String carteiraProfissional;
    @Size(max = 5)
    @Column(name = "SERIE")
    private String serie;
    @Size(max = 2)
    @Column(name = "UF_CART_PROF")
    private String ufCartProf;
    @Column(name = "DATA_CART_PROF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCartProf;
    @Size(max = 11)
    @Column(name = "PIS_PASEP")
    private String pisPasep;
    @Size(max = 14)
    @Column(name = "CPF")
    private String cpf;
    @Size(max = 15)
    @Column(name = "RG")
    private String rg;
    @Column(name = "RG_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rgData;
    @Size(max = 2)
    @Column(name = "RG_UF")
    private String rgUf;
    @Size(max = 5)
    @Column(name = "RG_EMISSAO")
    private String rgEmissao;
    @Size(max = 20)
    @Column(name = "TITULO_ELEITOR")
    private String tituloEleitor;
    @Column(name = "TITULO_ELEITOR_ZONA")
    private Integer tituloEleitorZona;
    @Column(name = "TITULO_ELEITOR_SESSAO")
    private Integer tituloEleitorSessao;
    @Size(max = 20)
    @Column(name = "RESERVISTA")
    private String reservista;
    @Size(max = 20)
    @Column(name = "CARTEIRA_MOTORISTA")
    private String carteiraMotorista;
    @Size(max = 15)
    @Column(name = "CARTEIRA_MOTORISTA_CATEGORIA")
    private String carteiraMotoristaCategoria;
    @Column(name = "CARTEIRA_MOTORISTA_VALIDADE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date carteiraMotoristaValidade;
    @Size(max = 1)
    @Column(name = "INFORMAR_RAIS")
    private String informarRais;
    @Column(name = "TIPO_ADMISSAO")
    private Short tipoAdmissao;
    @Column(name = "TIPO_MOVIMENTO")
    private Short tipoMovimento;
    @Column(name = "RACA")
    private Short raca;
    @Size(max = 1)
    @Column(name = "DEFICIENTE_FISICO")
    private String deficienteFisico;
    @Column(name = "NACIONALIDADE")
    private Short nacionalidade;
    @Column(name = "ANO_CHEGADA")
    private Integer anoChegada;
    @Column(name = "GRAU_INSTRUCAO")
    private Short grauInstrucao;
    @Column(name = "VINCULO_EMPREGATICIO")
    private Short vinculoEmpregaticio;
    @Size(max = 50)
    @Column(name = "CBO")
    private String cbo;
    @Column(name = "DOTACAO")
    private Integer dotacao;
    @Column(name = "SETOR")
    private Integer setor;
    @Column(name = "DEPARTAMENTO")
    private Integer departamento;
    @Column(name = "CARGO")
    private Integer cargo;
    @Column(name = "CODIGO_FUNCAO")
    private Short codigoFuncao;
    @Column(name = "FUNCAO")
    private Integer funcao;
    @Size(max = 30)
    @Column(name = "REFERENCIA")
    private String referencia;
    @Size(max = 30)
    @Column(name = "PROFISSAO")
    private String profissao;
    @Column(name = "REGIME")
    private Integer regime;
    @Column(name = "FORMA_ADMISSAO")
    private Integer formaAdmissao;
    @Size(max = 30)
    @Column(name = "OBS_ADMISSAO")
    private String obsAdmissao;
    @Size(max = 30)
    @Column(name = "PROVIMENTO")
    private String provimento;
    @Column(name = "DATA_ADMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAdmissao;
    @Column(name = "DATA_TEMPO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTempo;
    @Column(name = "DATA_FERIAS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFerias;
    @Column(name = "EXPERIENCIA")
    private Short experiencia;
    @Column(name = "VECIMENTO_CONTRATO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vecimentoContrato;
    @Column(name = "TIPO_SALARIO")
    private Short tipoSalario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "HORAS")
    private Double horas;
    @Column(name = "HORAS_SEMANAIS")
    private Integer horasSemanais;
    @Size(max = 1)
    @Column(name = "SITUACAO")
    private String situacao;
    @Column(name = "SALARIO_BASE")
    private BigDecimal salarioBase;
    @Column(name = "SALARIO_TOTAL")
    private BigDecimal salarioTotal;
    @Size(max = 1)
    @Column(name = "CALCULO_MEDIAS")
    private String calculoMedias;
    @Column(name = "DATA_AFASTAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAfastamento;
    @Column(name = "DATA_RETORNO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRetorno;
    @Column(name = "DATA_DEMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDemissao;
    @Column(name = "CAUSA_DEMISSAO")
    private Integer causaDemissao;
    @Size(max = 1)
    @Column(name = "CALC_FGTS")
    private String calcFgts;
    @Column(name = "DATA_OPCAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataOpcao;
    @Size(max = 1)
    @Column(name = "SEFIP")
    private String sefip;
    @Column(name = "CATEGORIA_FGTS")
    private Short categoriaFgts;
    @Column(name = "OCORRENCIA_FGTS")
    private Short ocorrenciaFgts;
    @Column(name = "CONTA_FGTS")
    private Integer contaFgts;
    @Column(name = "SALDO_FGTS")
    private BigDecimal saldoFgts;
    @Size(max = 1)
    @Column(name = "CALC_IRRF")
    private String calcIrrf;
    @Size(max = 1)
    @Column(name = "CALC_INSS")
    private String calcInss;
    @Size(max = 1)
    @Column(name = "CALC_INSS_EMPRESA")
    private String calcInssEmpresa;
    @Column(name = "DEP_SALARIO_FAMILIA")
    private Short depSalarioFamilia;
    @Column(name = "DEP_IMPOSTO_RENDA")
    private Short depImpostoRenda;
    @Column(name = "BANCO")
    private Short banco;
    @Size(max = 16)
    @Column(name = "CONTA_CORRENTE")
    private String contaCorrente;
    @Size(max = 1)
    @Column(name = "CONTRIB_SINDICAL")
    private String contribSindical;
    @Column(name = "SINDICATO")
    private Short sindicato;
    @Size(max = 1)
    @Column(name = "ADIANTAMENTO")
    private String adiantamento;
    @Column(name = "VALOR_APOSENTADORIA")
    private BigDecimal valorAposentadoria;
    @Size(max = 1)
    @Column(name = "SITUACAO_ESPECIAL")
    private String situacaoEspecial;
    @Size(max = 1)
    @Column(name = "CRACHA")
    private String cracha;
    @Column(name = "CARTAO_PONTO")
    private Short cartaoPonto;
    @Size(max = 8)
    @Column(name = "COD_BARRAS")
    private String codBarras;
    @Column(name = "DATA_LIMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLimite;
    @Column(name = "CHAPA_2")
    private Integer chapa2;
    @Size(max = 1)
    @Column(name = "CHAPA_2_DESCONTO")
    private String chapa2Desconto;
    @Column(name = "DATA_AVISO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAviso;
    @Size(max = 2)
    @Column(name = "CODIGO_AFASTAMENTO")
    private String codigoAfastamento;
    @Size(max = 1)
    @Column(name = "AVISO_PREVIO_RESCISAO")
    private String avisoPrevioRescisao;
    @Column(name = "VALOR_AVISO_PREVIO_RESCISAO")
    private BigDecimal valorAvisoPrevioRescisao;
    @Column(name = "DATA_HOMOLOGACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHomologacao;
    @Column(name = "CAGED_RESCISAO")
    private Integer cagedRescisao;
    @Size(max = 2)
    @Column(name = "CODIGO_SAQUE")
    private String codigoSaque;
    @Size(max = 1)
    @Column(name = "GRFC_FGTS")
    private String grfcFgts;
    @Column(name = "PENSAO_ALIM_RESCISAO")
    private BigDecimal pensaoAlimRescisao;
    @Size(max = 5)
    @Column(name = "HORARIO_1")
    private String horario1;
    @Size(max = 5)
    @Column(name = "HORARIO_2")
    private String horario2;
    @Size(max = 5)
    @Column(name = "HORARIO_3")
    private String horario3;
    @Size(max = 5)
    @Column(name = "HORARIO_4")
    private String horario4;
    @Size(max = 1)
    @Column(name = "FGTS_RECOLHIDO")
    private String fgtsRecolhido;
    @Size(max = 50)
    @Column(name = "FGTS_RECOLHIDO_OBS")
    private String fgtsRecolhidoObs;
    @Lob
    @Column(name = "FOTO")
    private byte[] foto;
    @Size(max = 2)
    @Column(name = "COD_MOVIMENTO_AFA")
    private String codMovimentoAfa;
    @Size(max = 2)
    @Column(name = "COD_MOVIMENTO_RET")
    private String codMovimentoRet;
    @Size(max = 7)
    @Column(name = "RESCISAO_FOLHA")
    private String rescisaoFolha;
    @Size(max = 30)
    @Column(name = "APELIDO")
    private String apelido;
    @Column(name = "DATA_DEMISSAO_BASE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDemissaoBase;
    @Column(name = "DATA_DEMISSAO_TRAB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDemissaoTrab;
    @Column(name = "SALARIO_RESCISAO")
    private BigDecimal salarioRescisao;
    @Size(max = 1)
    @Column(name = "MARCADO")
    private String marcado;
    @Column(name = "CODIGO_ADIANTAMENTO")
    private Integer codigoAdiantamento;
    @Column(name = "VALOR_ADIANTAMENTO")
    private BigDecimal valorAdiantamento;
    @Size(max = 80)
    @Column(name = "OBS_1")
    private String obs1;
    @Size(max = 80)
    @Column(name = "OBS_2")
    private String obs2;
    @Column(name = "SALARIO_BASE_PROPORCAO")
    private BigDecimal salarioBaseProporcao;
    @Size(max = 1)
    @Column(name = "DECIMO_TERCEIRO")
    private String decimoTerceiro;
    @Size(max = 1)
    @Column(name = "FERIAS")
    private String ferias;
    @Column(name = "CODIGO_TURMA")
    private Integer codigoTurma;
    @Size(max = 1)
    @Column(name = "SEGURO_VIDA")
    private String seguroVida;
    @Column(name = "FAIXA_SEGURO_VIDA")
    private Integer faixaSeguroVida;
    @Size(max = 1)
    @Column(name = "VALE_MERCADORIA")
    private String valeMercadoria;
    @Column(name = "CARTAOPONTO")
    private Integer cartaoponto;
    @Column(name = "TEMPO_INSS")
    private Integer tempoInss;
    @Size(max = 30)
    @Column(name = "OBS_RESCISAO")
    private String obsRescisao;
    @Size(max = 40)
    @Column(name = "REGISTRO_CART_TRAB")
    private String registroCartTrab;
    @Size(max = 14)
    @Column(name = "CONJUGE_CPF")
    private String conjugeCpf;
    @Size(max = 15)
    @Column(name = "CONJUGE_RG")
    private String conjugeRg;
    @Size(max = 1)
    @Column(name = "CONJUGE_IRRF")
    private String conjugeIrrf;
    @Column(name = "CONJUGE_DATA_NASCIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date conjugeDataNascimento;
    @Column(name = "TICKET_FOLHAS")
    private Integer ticketFolhas;
    @Column(name = "ANO_1_EMPREGO")
    private Integer ano1Emprego;
    @Size(max = 1)
    @Column(name = "FARMACIA")
    private String farmacia;
    @Size(max = 2)
    @Column(name = "ESTADO_NASCIMENTO")
    private String estadoNascimento;
    @Size(max = 6)
    @Column(name = "AGENCIA")
    private String agencia;
    @Size(max = 5)
    @Column(name = "RESCISAO_CALCULO")
    private String rescisaoCalculo;
    @Size(max = 1)
    @Column(name = "ISENCAO_65ANOS")
    private String isencao65anos;
    @Column(name = "PRORROGACAO_CONTRATO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prorrogacaoContrato;
    @Column(name = "DIAS_PRORROGACAO_CONTRATO")
    private Integer diasProrrogacaoContrato;
    @Size(max = 70)
    @Column(name = "HORARIO_DESCRICAO")
    private String horarioDescricao;
    @Size(max = 1)
    @Column(name = "CESTABASICA")
    private String cestabasica;
    @Column(name = "SUSPENSAO")
    private Integer suspensao;
    @Column(name = "FUNCAO_COMISSAO")
    private Integer funcaoComissao;
    @Column(name = "FUNCAO_CONCURSO")
    private Integer funcaoConcurso;
    @Column(name = "CONCURSO_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date concursoData;
    @Column(name = "CONCURSO_CLASSIF")
    private Integer concursoClassif;
    @Column(name = "CONCURSO_ANO")
    private Integer concursoAno;
    @Size(max = 10)
    @Column(name = "CONCURSO_NUMERO")
    private String concursoNumero;
    @Size(max = 30)
    @Column(name = "REFERENCIA_COMISSAO")
    private String referenciaComissao;
    @Size(max = 30)
    @Column(name = "REFERENCIA_CONCURSO")
    private String referenciaConcurso;
    @Column(name = "DATAINI_COMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainiComissao;
    @Column(name = "DATAFIN_COMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datafinComissao;
    @Column(name = "DATAINI_SUBSTITUICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datainiSubstituicao;
    @Column(name = "DATAFIN_SUBSTITUICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datafinSubstituicao;
    @Column(name = "DATA_PISPASEP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPispasep;
    @Column(name = "DATA_SEFIP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSefip;
    @Column(name = "PORTARIA_ANO")
    private Integer portariaAno;
    @Column(name = "PORTARIA_NUMERO")
    private Integer portariaNumero;
    @Column(name = "CHAPEIRA")
    private Integer chapeira;
    @Column(name = "PORTARIA_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date portariaData;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 60)
    @Column(name = "E_MAIL")
    private String eMail;
    @Column(name = "PORTARIA_ADMISSAO")
    private Integer portariaAdmissao;
    @Column(name = "PORTARIA_DATA_ADMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date portariaDataAdmissao;
    @Column(name = "READAPTADO_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date readaptadoData;
    @Size(max = 60)
    @Column(name = "READAPTADO_MOTIVO")
    private String readaptadoMotivo;
    @Column(name = "REINTEGRACAO_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reintegracaoData;
    @Size(max = 60)
    @Column(name = "REINTEGRACAO_MOTIVO")
    private String reintegracaoMotivo;
    @Column(name = "CODIGO_REFERENCIA")
    private Integer codigoReferencia;
    @Column(name = "CODIGO_REFERENCIA_CONCURSO")
    private Integer codigoReferenciaConcurso;
    @Column(name = "CODIGO_REFERENCIA_COMISSAO")
    private Integer codigoReferenciaComissao;
    @Size(max = 1)
    @Column(name = "PREVIDENCIA_REEMBOLSO")
    private String previdenciaReembolso;
    @Column(name = "PONTUACAO")
    private Integer pontuacao;
    @Column(name = "TICKET_VALOR")
    private BigDecimal ticketValor;
    @Size(max = 20)
    @Column(name = "USUARIO")
    private String usuario;
    @Size(max = 20)
    @Column(name = "SENHA")
    private String senha;
    @Column(name = "NUMERO_ORDEM")
    private Integer numeroOrdem;
    @Size(max = 3)
    @Column(name = "CAUSA_AFASTAMENTO")
    private String causaAfastamento;
    @Column(name = "TIPO_CONTRATO")
    private Integer tipoContrato;
    @Size(max = 1)
    @Column(name = "VALE_TRANSPORTE")
    private String valeTransporte;
    @Column(name = "VALE_TRANSPORTE_QUANT")
    private BigDecimal valeTransporteQuant;
    @Size(max = 1)
    @Column(name = "SEGURO_DESEMPREGO")
    private String seguroDesemprego;
    @Column(name = "PRIMEIRO_ACESSO")
    private Integer primeiroAcesso;
    @Size(max = 200)
    @Column(name = "CAMINHO_FOTO")
    private String caminhoFoto;

    public Funcionarios() {
    }

    public Funcionarios(FuncionariosPK funcionariosPK) {
        this.funcionariosPK = funcionariosPK;
    }

    public Funcionarios(int empresa, Integer chapa) {
        this.funcionariosPK = new FuncionariosPK(empresa, chapa);
    }

    public FuncionariosPK getFuncionariosPK() {
        return funcionariosPK;
    }

    public void setFuncionariosPK(FuncionariosPK funcionariosPK) {
        this.funcionariosPK = funcionariosPK;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getLocalNascimento() {
        return localNascimento;
    }

    public void setLocalNascimento(String localNascimento) {
        this.localNascimento = localNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getConjuge() {
        return conjuge;
    }

    public void setConjuge(String conjuge) {
        this.conjuge = conjuge;
    }

    public Short getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Short idiomas) {
        this.idiomas = idiomas;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public String getCarteiraProfissional() {
        return carteiraProfissional;
    }

    public void setCarteiraProfissional(String carteiraProfissional) {
        this.carteiraProfissional = carteiraProfissional;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getUfCartProf() {
        return ufCartProf;
    }

    public void setUfCartProf(String ufCartProf) {
        this.ufCartProf = ufCartProf;
    }

    public Date getDataCartProf() {
        return dataCartProf;
    }

    public void setDataCartProf(Date dataCartProf) {
        this.dataCartProf = dataCartProf;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getRgData() {
        return rgData;
    }

    public void setRgData(Date rgData) {
        this.rgData = rgData;
    }

    public String getRgUf() {
        return rgUf;
    }

    public void setRgUf(String rgUf) {
        this.rgUf = rgUf;
    }

    public String getRgEmissao() {
        return rgEmissao;
    }

    public void setRgEmissao(String rgEmissao) {
        this.rgEmissao = rgEmissao;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public Integer getTituloEleitorZona() {
        return tituloEleitorZona;
    }

    public void setTituloEleitorZona(Integer tituloEleitorZona) {
        this.tituloEleitorZona = tituloEleitorZona;
    }

    public Integer getTituloEleitorSessao() {
        return tituloEleitorSessao;
    }

    public void setTituloEleitorSessao(Integer tituloEleitorSessao) {
        this.tituloEleitorSessao = tituloEleitorSessao;
    }

    public String getReservista() {
        return reservista;
    }

    public void setReservista(String reservista) {
        this.reservista = reservista;
    }

    public String getCarteiraMotorista() {
        return carteiraMotorista;
    }

    public void setCarteiraMotorista(String carteiraMotorista) {
        this.carteiraMotorista = carteiraMotorista;
    }

    public String getCarteiraMotoristaCategoria() {
        return carteiraMotoristaCategoria;
    }

    public void setCarteiraMotoristaCategoria(String carteiraMotoristaCategoria) {
        this.carteiraMotoristaCategoria = carteiraMotoristaCategoria;
    }

    public Date getCarteiraMotoristaValidade() {
        return carteiraMotoristaValidade;
    }

    public void setCarteiraMotoristaValidade(Date carteiraMotoristaValidade) {
        this.carteiraMotoristaValidade = carteiraMotoristaValidade;
    }

    public String getInformarRais() {
        return informarRais;
    }

    public void setInformarRais(String informarRais) {
        this.informarRais = informarRais;
    }

    public Short getTipoAdmissao() {
        return tipoAdmissao;
    }

    public void setTipoAdmissao(Short tipoAdmissao) {
        this.tipoAdmissao = tipoAdmissao;
    }

    public Short getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(Short tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public Short getRaca() {
        return raca;
    }

    public void setRaca(Short raca) {
        this.raca = raca;
    }

    public String getDeficienteFisico() {
        return deficienteFisico;
    }

    public void setDeficienteFisico(String deficienteFisico) {
        this.deficienteFisico = deficienteFisico;
    }

    public Short getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(Short nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Integer getAnoChegada() {
        return anoChegada;
    }

    public void setAnoChegada(Integer anoChegada) {
        this.anoChegada = anoChegada;
    }

    public Short getGrauInstrucao() {
        return grauInstrucao;
    }

    public void setGrauInstrucao(Short grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    public Short getVinculoEmpregaticio() {
        return vinculoEmpregaticio;
    }

    public void setVinculoEmpregaticio(Short vinculoEmpregaticio) {
        this.vinculoEmpregaticio = vinculoEmpregaticio;
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = cbo;
    }

    public Integer getDotacao() {
        return dotacao;
    }

    public void setDotacao(Integer dotacao) {
        this.dotacao = dotacao;
    }

    public Integer getSetor() {
        return setor;
    }

    public void setSetor(Integer setor) {
        this.setor = setor;
    }

    public Integer getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Integer departamento) {
        this.departamento = departamento;
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public Short getCodigoFuncao() {
        return codigoFuncao;
    }

    public void setCodigoFuncao(Short codigoFuncao) {
        this.codigoFuncao = codigoFuncao;
    }

    public Integer getFuncao() {
        return funcao;
    }

    public void setFuncao(Integer funcao) {
        this.funcao = funcao;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Integer getRegime() {
        return regime;
    }

    public void setRegime(Integer regime) {
        this.regime = regime;
    }

    public Integer getFormaAdmissao() {
        return formaAdmissao;
    }

    public void setFormaAdmissao(Integer formaAdmissao) {
        this.formaAdmissao = formaAdmissao;
    }

    public String getObsAdmissao() {
        return obsAdmissao;
    }

    public void setObsAdmissao(String obsAdmissao) {
        this.obsAdmissao = obsAdmissao;
    }

    public String getProvimento() {
        return provimento;
    }

    public void setProvimento(String provimento) {
        this.provimento = provimento;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Date getDataTempo() {
        return dataTempo;
    }

    public void setDataTempo(Date dataTempo) {
        this.dataTempo = dataTempo;
    }

    public Date getDataFerias() {
        return dataFerias;
    }

    public void setDataFerias(Date dataFerias) {
        this.dataFerias = dataFerias;
    }

    public Short getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Short experiencia) {
        this.experiencia = experiencia;
    }

    public Date getVecimentoContrato() {
        return vecimentoContrato;
    }

    public void setVecimentoContrato(Date vecimentoContrato) {
        this.vecimentoContrato = vecimentoContrato;
    }

    public Short getTipoSalario() {
        return tipoSalario;
    }

    public void setTipoSalario(Short tipoSalario) {
        this.tipoSalario = tipoSalario;
    }

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public Integer getHorasSemanais() {
        return horasSemanais;
    }

    public void setHorasSemanais(Integer horasSemanais) {
        this.horasSemanais = horasSemanais;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    public BigDecimal getSalarioTotal() {
        return salarioTotal;
    }

    public void setSalarioTotal(BigDecimal salarioTotal) {
        this.salarioTotal = salarioTotal;
    }

    public String getCalculoMedias() {
        return calculoMedias;
    }

    public void setCalculoMedias(String calculoMedias) {
        this.calculoMedias = calculoMedias;
    }

    public Date getDataAfastamento() {
        return dataAfastamento;
    }

    public void setDataAfastamento(Date dataAfastamento) {
        this.dataAfastamento = dataAfastamento;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Date getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(Date dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Integer getCausaDemissao() {
        return causaDemissao;
    }

    public void setCausaDemissao(Integer causaDemissao) {
        this.causaDemissao = causaDemissao;
    }

    public String getCalcFgts() {
        return calcFgts;
    }

    public void setCalcFgts(String calcFgts) {
        this.calcFgts = calcFgts;
    }

    public Date getDataOpcao() {
        return dataOpcao;
    }

    public void setDataOpcao(Date dataOpcao) {
        this.dataOpcao = dataOpcao;
    }

    public String getSefip() {
        return sefip;
    }

    public void setSefip(String sefip) {
        this.sefip = sefip;
    }

    public Short getCategoriaFgts() {
        return categoriaFgts;
    }

    public void setCategoriaFgts(Short categoriaFgts) {
        this.categoriaFgts = categoriaFgts;
    }

    public Short getOcorrenciaFgts() {
        return ocorrenciaFgts;
    }

    public void setOcorrenciaFgts(Short ocorrenciaFgts) {
        this.ocorrenciaFgts = ocorrenciaFgts;
    }

    public Integer getContaFgts() {
        return contaFgts;
    }

    public void setContaFgts(Integer contaFgts) {
        this.contaFgts = contaFgts;
    }

    public BigDecimal getSaldoFgts() {
        return saldoFgts;
    }

    public void setSaldoFgts(BigDecimal saldoFgts) {
        this.saldoFgts = saldoFgts;
    }

    public String getCalcIrrf() {
        return calcIrrf;
    }

    public void setCalcIrrf(String calcIrrf) {
        this.calcIrrf = calcIrrf;
    }

    public String getCalcInss() {
        return calcInss;
    }

    public void setCalcInss(String calcInss) {
        this.calcInss = calcInss;
    }

    public String getCalcInssEmpresa() {
        return calcInssEmpresa;
    }

    public void setCalcInssEmpresa(String calcInssEmpresa) {
        this.calcInssEmpresa = calcInssEmpresa;
    }

    public Short getDepSalarioFamilia() {
        return depSalarioFamilia;
    }

    public void setDepSalarioFamilia(Short depSalarioFamilia) {
        this.depSalarioFamilia = depSalarioFamilia;
    }

    public Short getDepImpostoRenda() {
        return depImpostoRenda;
    }

    public void setDepImpostoRenda(Short depImpostoRenda) {
        this.depImpostoRenda = depImpostoRenda;
    }

    public Short getBanco() {
        return banco;
    }

    public void setBanco(Short banco) {
        this.banco = banco;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public String getContribSindical() {
        return contribSindical;
    }

    public void setContribSindical(String contribSindical) {
        this.contribSindical = contribSindical;
    }

    public Short getSindicato() {
        return sindicato;
    }

    public void setSindicato(Short sindicato) {
        this.sindicato = sindicato;
    }

    public String getAdiantamento() {
        return adiantamento;
    }

    public void setAdiantamento(String adiantamento) {
        this.adiantamento = adiantamento;
    }

    public BigDecimal getValorAposentadoria() {
        return valorAposentadoria;
    }

    public void setValorAposentadoria(BigDecimal valorAposentadoria) {
        this.valorAposentadoria = valorAposentadoria;
    }

    public String getSituacaoEspecial() {
        return situacaoEspecial;
    }

    public void setSituacaoEspecial(String situacaoEspecial) {
        this.situacaoEspecial = situacaoEspecial;
    }

    public String getCracha() {
        return cracha;
    }

    public void setCracha(String cracha) {
        this.cracha = cracha;
    }

    public Short getCartaoPonto() {
        return cartaoPonto;
    }

    public void setCartaoPonto(Short cartaoPonto) {
        this.cartaoPonto = cartaoPonto;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Integer getChapa2() {
        return chapa2;
    }

    public void setChapa2(Integer chapa2) {
        this.chapa2 = chapa2;
    }

    public String getChapa2Desconto() {
        return chapa2Desconto;
    }

    public void setChapa2Desconto(String chapa2Desconto) {
        this.chapa2Desconto = chapa2Desconto;
    }

    public Date getDataAviso() {
        return dataAviso;
    }

    public void setDataAviso(Date dataAviso) {
        this.dataAviso = dataAviso;
    }

    public String getCodigoAfastamento() {
        return codigoAfastamento;
    }

    public void setCodigoAfastamento(String codigoAfastamento) {
        this.codigoAfastamento = codigoAfastamento;
    }

    public String getAvisoPrevioRescisao() {
        return avisoPrevioRescisao;
    }

    public void setAvisoPrevioRescisao(String avisoPrevioRescisao) {
        this.avisoPrevioRescisao = avisoPrevioRescisao;
    }

    public BigDecimal getValorAvisoPrevioRescisao() {
        return valorAvisoPrevioRescisao;
    }

    public void setValorAvisoPrevioRescisao(BigDecimal valorAvisoPrevioRescisao) {
        this.valorAvisoPrevioRescisao = valorAvisoPrevioRescisao;
    }

    public Date getDataHomologacao() {
        return dataHomologacao;
    }

    public void setDataHomologacao(Date dataHomologacao) {
        this.dataHomologacao = dataHomologacao;
    }

    public Integer getCagedRescisao() {
        return cagedRescisao;
    }

    public void setCagedRescisao(Integer cagedRescisao) {
        this.cagedRescisao = cagedRescisao;
    }

    public String getCodigoSaque() {
        return codigoSaque;
    }

    public void setCodigoSaque(String codigoSaque) {
        this.codigoSaque = codigoSaque;
    }

    public String getGrfcFgts() {
        return grfcFgts;
    }

    public void setGrfcFgts(String grfcFgts) {
        this.grfcFgts = grfcFgts;
    }

    public BigDecimal getPensaoAlimRescisao() {
        return pensaoAlimRescisao;
    }

    public void setPensaoAlimRescisao(BigDecimal pensaoAlimRescisao) {
        this.pensaoAlimRescisao = pensaoAlimRescisao;
    }

    public String getHorario1() {
        return horario1;
    }

    public void setHorario1(String horario1) {
        this.horario1 = horario1;
    }

    public String getHorario2() {
        return horario2;
    }

    public void setHorario2(String horario2) {
        this.horario2 = horario2;
    }

    public String getHorario3() {
        return horario3;
    }

    public void setHorario3(String horario3) {
        this.horario3 = horario3;
    }

    public String getHorario4() {
        return horario4;
    }

    public void setHorario4(String horario4) {
        this.horario4 = horario4;
    }

    public String getFgtsRecolhido() {
        return fgtsRecolhido;
    }

    public void setFgtsRecolhido(String fgtsRecolhido) {
        this.fgtsRecolhido = fgtsRecolhido;
    }

    public String getFgtsRecolhidoObs() {
        return fgtsRecolhidoObs;
    }

    public void setFgtsRecolhidoObs(String fgtsRecolhidoObs) {
        this.fgtsRecolhidoObs = fgtsRecolhidoObs;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getCodMovimentoAfa() {
        return codMovimentoAfa;
    }

    public void setCodMovimentoAfa(String codMovimentoAfa) {
        this.codMovimentoAfa = codMovimentoAfa;
    }

    public String getCodMovimentoRet() {
        return codMovimentoRet;
    }

    public void setCodMovimentoRet(String codMovimentoRet) {
        this.codMovimentoRet = codMovimentoRet;
    }

    public String getRescisaoFolha() {
        return rescisaoFolha;
    }

    public void setRescisaoFolha(String rescisaoFolha) {
        this.rescisaoFolha = rescisaoFolha;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Date getDataDemissaoBase() {
        return dataDemissaoBase;
    }

    public void setDataDemissaoBase(Date dataDemissaoBase) {
        this.dataDemissaoBase = dataDemissaoBase;
    }

    public Date getDataDemissaoTrab() {
        return dataDemissaoTrab;
    }

    public void setDataDemissaoTrab(Date dataDemissaoTrab) {
        this.dataDemissaoTrab = dataDemissaoTrab;
    }

    public BigDecimal getSalarioRescisao() {
        return salarioRescisao;
    }

    public void setSalarioRescisao(BigDecimal salarioRescisao) {
        this.salarioRescisao = salarioRescisao;
    }

    public String getMarcado() {
        return marcado;
    }

    public void setMarcado(String marcado) {
        this.marcado = marcado;
    }

    public Integer getCodigoAdiantamento() {
        return codigoAdiantamento;
    }

    public void setCodigoAdiantamento(Integer codigoAdiantamento) {
        this.codigoAdiantamento = codigoAdiantamento;
    }

    public BigDecimal getValorAdiantamento() {
        return valorAdiantamento;
    }

    public void setValorAdiantamento(BigDecimal valorAdiantamento) {
        this.valorAdiantamento = valorAdiantamento;
    }

    public String getObs1() {
        return obs1;
    }

    public void setObs1(String obs1) {
        this.obs1 = obs1;
    }

    public String getObs2() {
        return obs2;
    }

    public void setObs2(String obs2) {
        this.obs2 = obs2;
    }

    public BigDecimal getSalarioBaseProporcao() {
        return salarioBaseProporcao;
    }

    public void setSalarioBaseProporcao(BigDecimal salarioBaseProporcao) {
        this.salarioBaseProporcao = salarioBaseProporcao;
    }

    public String getDecimoTerceiro() {
        return decimoTerceiro;
    }

    public void setDecimoTerceiro(String decimoTerceiro) {
        this.decimoTerceiro = decimoTerceiro;
    }

    public String getFerias() {
        return ferias;
    }

    public void setFerias(String ferias) {
        this.ferias = ferias;
    }

    public Integer getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(Integer codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public String getSeguroVida() {
        return seguroVida;
    }

    public void setSeguroVida(String seguroVida) {
        this.seguroVida = seguroVida;
    }

    public Integer getFaixaSeguroVida() {
        return faixaSeguroVida;
    }

    public void setFaixaSeguroVida(Integer faixaSeguroVida) {
        this.faixaSeguroVida = faixaSeguroVida;
    }

    public String getValeMercadoria() {
        return valeMercadoria;
    }

    public void setValeMercadoria(String valeMercadoria) {
        this.valeMercadoria = valeMercadoria;
    }

    public Integer getCartaoponto() {
        return cartaoponto;
    }

    public void setCartaoponto(Integer cartaoponto) {
        this.cartaoponto = cartaoponto;
    }

    public Integer getTempoInss() {
        return tempoInss;
    }

    public void setTempoInss(Integer tempoInss) {
        this.tempoInss = tempoInss;
    }

    public String getObsRescisao() {
        return obsRescisao;
    }

    public void setObsRescisao(String obsRescisao) {
        this.obsRescisao = obsRescisao;
    }

    public String getRegistroCartTrab() {
        return registroCartTrab;
    }

    public void setRegistroCartTrab(String registroCartTrab) {
        this.registroCartTrab = registroCartTrab;
    }

    public String getConjugeCpf() {
        return conjugeCpf;
    }

    public void setConjugeCpf(String conjugeCpf) {
        this.conjugeCpf = conjugeCpf;
    }

    public String getConjugeRg() {
        return conjugeRg;
    }

    public void setConjugeRg(String conjugeRg) {
        this.conjugeRg = conjugeRg;
    }

    public String getConjugeIrrf() {
        return conjugeIrrf;
    }

    public void setConjugeIrrf(String conjugeIrrf) {
        this.conjugeIrrf = conjugeIrrf;
    }

    public Date getConjugeDataNascimento() {
        return conjugeDataNascimento;
    }

    public void setConjugeDataNascimento(Date conjugeDataNascimento) {
        this.conjugeDataNascimento = conjugeDataNascimento;
    }

    public Integer getTicketFolhas() {
        return ticketFolhas;
    }

    public void setTicketFolhas(Integer ticketFolhas) {
        this.ticketFolhas = ticketFolhas;
    }

    public Integer getAno1Emprego() {
        return ano1Emprego;
    }

    public void setAno1Emprego(Integer ano1Emprego) {
        this.ano1Emprego = ano1Emprego;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getEstadoNascimento() {
        return estadoNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getRescisaoCalculo() {
        return rescisaoCalculo;
    }

    public void setRescisaoCalculo(String rescisaoCalculo) {
        this.rescisaoCalculo = rescisaoCalculo;
    }

    public String getIsencao65anos() {
        return isencao65anos;
    }

    public void setIsencao65anos(String isencao65anos) {
        this.isencao65anos = isencao65anos;
    }

    public Date getProrrogacaoContrato() {
        return prorrogacaoContrato;
    }

    public void setProrrogacaoContrato(Date prorrogacaoContrato) {
        this.prorrogacaoContrato = prorrogacaoContrato;
    }

    public Integer getDiasProrrogacaoContrato() {
        return diasProrrogacaoContrato;
    }

    public void setDiasProrrogacaoContrato(Integer diasProrrogacaoContrato) {
        this.diasProrrogacaoContrato = diasProrrogacaoContrato;
    }

    public String getHorarioDescricao() {
        return horarioDescricao;
    }

    public void setHorarioDescricao(String horarioDescricao) {
        this.horarioDescricao = horarioDescricao;
    }

    public String getCestabasica() {
        return cestabasica;
    }

    public void setCestabasica(String cestabasica) {
        this.cestabasica = cestabasica;
    }

    public Integer getSuspensao() {
        return suspensao;
    }

    public void setSuspensao(Integer suspensao) {
        this.suspensao = suspensao;
    }

    public Integer getFuncaoComissao() {
        return funcaoComissao;
    }

    public void setFuncaoComissao(Integer funcaoComissao) {
        this.funcaoComissao = funcaoComissao;
    }

    public Integer getFuncaoConcurso() {
        return funcaoConcurso;
    }

    public void setFuncaoConcurso(Integer funcaoConcurso) {
        this.funcaoConcurso = funcaoConcurso;
    }

    public Date getConcursoData() {
        return concursoData;
    }

    public void setConcursoData(Date concursoData) {
        this.concursoData = concursoData;
    }

    public Integer getConcursoClassif() {
        return concursoClassif;
    }

    public void setConcursoClassif(Integer concursoClassif) {
        this.concursoClassif = concursoClassif;
    }

    public Integer getConcursoAno() {
        return concursoAno;
    }

    public void setConcursoAno(Integer concursoAno) {
        this.concursoAno = concursoAno;
    }

    public String getConcursoNumero() {
        return concursoNumero;
    }

    public void setConcursoNumero(String concursoNumero) {
        this.concursoNumero = concursoNumero;
    }

    public String getReferenciaComissao() {
        return referenciaComissao;
    }

    public void setReferenciaComissao(String referenciaComissao) {
        this.referenciaComissao = referenciaComissao;
    }

    public String getReferenciaConcurso() {
        return referenciaConcurso;
    }

    public void setReferenciaConcurso(String referenciaConcurso) {
        this.referenciaConcurso = referenciaConcurso;
    }

    public Date getDatainiComissao() {
        return datainiComissao;
    }

    public void setDatainiComissao(Date datainiComissao) {
        this.datainiComissao = datainiComissao;
    }

    public Date getDatafinComissao() {
        return datafinComissao;
    }

    public void setDatafinComissao(Date datafinComissao) {
        this.datafinComissao = datafinComissao;
    }

    public Date getDatainiSubstituicao() {
        return datainiSubstituicao;
    }

    public void setDatainiSubstituicao(Date datainiSubstituicao) {
        this.datainiSubstituicao = datainiSubstituicao;
    }

    public Date getDatafinSubstituicao() {
        return datafinSubstituicao;
    }

    public void setDatafinSubstituicao(Date datafinSubstituicao) {
        this.datafinSubstituicao = datafinSubstituicao;
    }

    public Date getDataPispasep() {
        return dataPispasep;
    }

    public void setDataPispasep(Date dataPispasep) {
        this.dataPispasep = dataPispasep;
    }

    public Date getDataSefip() {
        return dataSefip;
    }

    public void setDataSefip(Date dataSefip) {
        this.dataSefip = dataSefip;
    }

    public Integer getPortariaAno() {
        return portariaAno;
    }

    public void setPortariaAno(Integer portariaAno) {
        this.portariaAno = portariaAno;
    }

    public Integer getPortariaNumero() {
        return portariaNumero;
    }

    public void setPortariaNumero(Integer portariaNumero) {
        this.portariaNumero = portariaNumero;
    }

    public Integer getChapeira() {
        return chapeira;
    }

    public void setChapeira(Integer chapeira) {
        this.chapeira = chapeira;
    }

    public Date getPortariaData() {
        return portariaData;
    }

    public void setPortariaData(Date portariaData) {
        this.portariaData = portariaData;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public Integer getPortariaAdmissao() {
        return portariaAdmissao;
    }

    public void setPortariaAdmissao(Integer portariaAdmissao) {
        this.portariaAdmissao = portariaAdmissao;
    }

    public Date getPortariaDataAdmissao() {
        return portariaDataAdmissao;
    }

    public void setPortariaDataAdmissao(Date portariaDataAdmissao) {
        this.portariaDataAdmissao = portariaDataAdmissao;
    }

    public Date getReadaptadoData() {
        return readaptadoData;
    }

    public void setReadaptadoData(Date readaptadoData) {
        this.readaptadoData = readaptadoData;
    }

    public String getReadaptadoMotivo() {
        return readaptadoMotivo;
    }

    public void setReadaptadoMotivo(String readaptadoMotivo) {
        this.readaptadoMotivo = readaptadoMotivo;
    }

    public Date getReintegracaoData() {
        return reintegracaoData;
    }

    public void setReintegracaoData(Date reintegracaoData) {
        this.reintegracaoData = reintegracaoData;
    }

    public String getReintegracaoMotivo() {
        return reintegracaoMotivo;
    }

    public void setReintegracaoMotivo(String reintegracaoMotivo) {
        this.reintegracaoMotivo = reintegracaoMotivo;
    }

    public Integer getCodigoReferencia() {
        return codigoReferencia;
    }

    public void setCodigoReferencia(Integer codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public Integer getCodigoReferenciaConcurso() {
        return codigoReferenciaConcurso;
    }

    public void setCodigoReferenciaConcurso(Integer codigoReferenciaConcurso) {
        this.codigoReferenciaConcurso = codigoReferenciaConcurso;
    }

    public Integer getCodigoReferenciaComissao() {
        return codigoReferenciaComissao;
    }

    public void setCodigoReferenciaComissao(Integer codigoReferenciaComissao) {
        this.codigoReferenciaComissao = codigoReferenciaComissao;
    }

    public String getPrevidenciaReembolso() {
        return previdenciaReembolso;
    }

    public void setPrevidenciaReembolso(String previdenciaReembolso) {
        this.previdenciaReembolso = previdenciaReembolso;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public BigDecimal getTicketValor() {
        return ticketValor;
    }

    public void setTicketValor(BigDecimal ticketValor) {
        this.ticketValor = ticketValor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getNumeroOrdem() {
        return numeroOrdem;
    }

    public void setNumeroOrdem(Integer numeroOrdem) {
        this.numeroOrdem = numeroOrdem;
    }

    public String getCausaAfastamento() {
        return causaAfastamento;
    }

    public void setCausaAfastamento(String causaAfastamento) {
        this.causaAfastamento = causaAfastamento;
    }

    public Integer getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(Integer tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getValeTransporte() {
        return valeTransporte;
    }

    public void setValeTransporte(String valeTransporte) {
        this.valeTransporte = valeTransporte;
    }

    public BigDecimal getValeTransporteQuant() {
        return valeTransporteQuant;
    }

    public void setValeTransporteQuant(BigDecimal valeTransporteQuant) {
        this.valeTransporteQuant = valeTransporteQuant;
    }

    public String getSeguroDesemprego() {
        return seguroDesemprego;
    }

    public void setSeguroDesemprego(String seguroDesemprego) {
        this.seguroDesemprego = seguroDesemprego;
    }

    public Integer getPrimeiroAcesso() {
        return primeiroAcesso;
    }

    public void setPrimeiroAcesso(Integer primeiroAcesso) {
        this.primeiroAcesso = primeiroAcesso;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funcionariosPK != null ? funcionariosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionarios)) {
            return false;
        }
        Funcionarios other = (Funcionarios) object;
        if ((this.funcionariosPK == null && other.funcionariosPK != null) || (this.funcionariosPK != null && !this.funcionariosPK.equals(other.funcionariosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.eddydata.holerite.entidade.Funcionarios[ funcionariosPK=" + funcionariosPK + " ]";
    }
    
}
