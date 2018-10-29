/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.geral;

import br.com.eddydata.entidade.admin.Imovel;
import br.com.eddydata.entidade.geo.Cidade;
import br.com.eddydata.entidade.referencia.Cor;
import br.com.eddydata.entidade.referencia.EstadoCivil;
import br.com.eddydata.entidade.referencia.GrauInstrucao;
import br.com.eddydata.entidade.referencia.Natureza;
import br.com.eddydata.entidade.referencia.OrgaoExpedidor;
import br.com.eddydata.entidade.referencia.Sexo;
import br.com.eddydata.entidade.referencia.Situacao;
import br.com.eddydata.entidade.referencia.Sociedade;
import br.com.eddydata.entidade.referencia.TipoPessoa;
import br.com.eddydata.entidade.referencia.TipoSanguineo;
import br.com.eddydata.entidade.referencia.UfRg;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
import javax.validation.constraints.Size;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Integer id;

    @Column(name = "tp_pessoa")
    private TipoPessoa tpPessoa;

    @Column(name = "situacao")
    private Situacao situacao;

    @Size(max = 300)
    @Column(name = "nome")
    private String nome;

    @Column(name = "sexo")
    private Sexo sexo;

    @Column(name = "est_civil")
    private EstadoCivil estCivil;

    @Column(name = "dt_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dtNascimento;

    @Column(name = "cor")
    private Cor cor;

    @Column(name = "tp_sanguineo")
    private TipoSanguineo tpSanguineo;

    @Column(name = "is_estudante")
    private Boolean isEstudante;

    @Column(name = "grau_instrucao")
    private GrauInstrucao grauInstrucao;

    @Column(name = "dt_migracao")
    @Temporal(TemporalType.DATE)
    private Date dtMigracao;

    @Column(name = "cartao_cidadao")
    private Integer cartaoCidadao;

    @Size(max = 20)
    @Column(name = "rg")
    private String rg;

    @Column(name = "dt_emissao_rg")
    @Temporal(TemporalType.DATE)
    private Date dtEmissaoRg;

    @Column(name = "uf_rg")
    private UfRg ufRg;

    @Column(name = "orgao_exp_rg")
    private OrgaoExpedidor orgaoExpRg;

    @Size(max = 50)
    @Column(name = "cert_reservista")
    private String certReservista;

    @Size(max = 20)
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @Column(name = "nis")
    private Integer nis;

    @Size(max = 50)
    @Column(name = "cart_prof")
    private String cartProf;

    @Column(name = "dt_emissao_cart_prof")
    @Temporal(TemporalType.DATE)
    private Date dtEmissaoCartProf;

    @Size(max = 50)
    @Column(name = "pis_pasep")
    private String pisPasep;

    @Size(max = 100)
    @Column(name = "empresa_trab")
    private String empresaTrab;

    @Column(name = "is_registrado")
    private Boolean isRegistrado;

    @Column(name = "dt_admissao")
    @Temporal(TemporalType.DATE)
    private Date dtAdmissao;

    @Column(name = "renda")
    private Double renda;

    @Size(max = 300)
    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Size(max = 50)
    @Column(name = "insc_municipal")
    private String inscMunicipal;

    @Column(name = "dt_inscricao")
    @Temporal(TemporalType.DATE)
    private Date dtInscricao;

    @Column(name = "natureza")
    private Natureza natureza;

    @Column(name = "sociedade")
    private Sociedade sociedade;

    @Size(max = 100)
    @Column(name = "url")
    private String url;

    @Column(name = "dt_abertura")
    @Temporal(TemporalType.DATE)
    private Date dtAbertura;

    @Size(max = 50)
    @Column(name = "insc_estadual")
    private String inscEstadual;

    @Size(max = 50)
    @Column(name = "tel1")
    private String tel1;

    @Size(max = 50)
    @Column(name = "tel2")
    private String tel2;

    @Size(max = 50)
    @Column(name = "tel3")
    private String tel3;

    @Size(max = 50)
    @Column(name = "tel4")
    private String tel4;

    @Size(max = 50)
    @Column(name = "tel5")
    private String tel5;

    @Size(max = 50)
    @Column(name = "tel6")
    private String tel6;

    @Size(max = 70)
    @Column(name = "e_mail")
    private String eMail;

    @Size(max = 50)
    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Column(name = "id_atividade")
    private Integer idAtividade;

    @Size(max = 200)
    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private Integer numero;

    @Size(max = 200)
    @Column(name = "bairro")
    private String bairro;

    @Size(max = 15)
    @Column(name = "cep")
    private String cep;

    @Column(name = "sus")
    private Integer sus;

    @Column(name = "is_reg_prof")
    private Integer isRegProf;

    @Column(name = "renda_complementar")
    private Double rendaComplementar;

    @Size(max = 40)
    @Column(name = "cartorio_certidao")
    private String cartorioCertidao;

    @Size(max = 15)
    @Column(name = "livro_certidao")
    private String livroCertidao;

    @Size(max = 15)
    @Column(name = "folha_certidao")
    private String folhaCertidao;

    @Column(name = "dt_emissao_certidao")
    @Temporal(TemporalType.DATE)
    private Date dtEmissaoCertidao;

    @Size(max = 80)
    @Column(name = "pai")
    private String pai;

    @Size(max = 80)
    @Column(name = "mae")
    private String mae;

    @Size(max = 80)
    @Column(name = "apelido")
    private String apelido;

    @Size(max = 1000)
    @Column(name = "memorando")
    private String memorando;

    @Column(name = "certidao")
    private Integer certidao;

    @Size(max = 15)
    @Column(name = "termo_certidao")
    private String termoCertidao;

    @Column(name = "numero_temp")
    private Integer numeroTemp;

    @Column(name = "id_cargo")
    private Integer idCargo;

    @Column(name = "cadastro_atualizado")
    private Integer cadastroAtualizado;

    @Column(name = "cod_habitante")
    private Integer codHabitante;

    @Column(name = "bolsa_familia")
    private Boolean bolsaFamilia;

    @Size(max = 20)
    @Column(name = "celular")
    private String celular;

    @Column(name = "data_emissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;

    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Size(max = 255)
    @Column(name = "distrito_certidao_nasc")
    private String distritoCertidaoNasc;

    @Size(max = 70)
    @Column(name = "email")
    private String email;

    @Column(name = "estado_civil")
    private Integer estadoCivil;

    @Column(name = "fls_certidao_nasc")
    private Integer flsCertidaoNasc;

    @Size(max = 30)
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @Size(max = 255)
    @Column(name = "livro_certidao_nasc")
    private String livroCertidaoNasc;

    @Size(max = 50)
    @Column(name = "matricula_certidao_nasc")
    private String matriculaCertidaoNasc;

    @Column(name = "orgao_expedidor_rg")
    private Integer orgaoExpedidorRg;

    @Size(max = 255)
    @Column(name = "subdistrito_certidao_nasc")
    private String subdistritoCertidaoNasc;

    @Size(max = 20)
    @Column(name = "telefone")
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cidade_nasc")
    private Cidade cidadeNascimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_origem_migracao")
    private Cidade origemMigracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cidade_eleitor")
    private Cidade cidadeEleitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_certidao_nasc")
    private Cidade cidadeCertidaoNasc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deficienciapessoa")
    private DeficienciaPessoa deficienciapessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_imovel_trab")
    private Imovel imovelTrab;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_imovel")
    private Imovel imovel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissao")
    private Profissao profissao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_religiao")
    private Religiao religiao;
    
    @Column(name = "cidade", length = 50)
    private String cidade;
    
    @Column(name = "logradouro", length = 100)
    private String logradouro;
    
    @Column(name = "complemento", length = 20)
    private String complemento;
    
    @Column(name = "uf", length = 50)
    private String uf;

    public Pessoa() {
    }

    public Pessoa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoPessoa getTpPessoa() {
        return tpPessoa;
    }

    public void setTpPessoa(TipoPessoa tpPessoa) {
        this.tpPessoa = tpPessoa;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public EstadoCivil getEstCivil() {
        return estCivil;
    }

    public void setEstCivil(EstadoCivil estCivil) {
        this.estCivil = estCivil;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public TipoSanguineo getTpSanguineo() {
        return tpSanguineo;
    }

    public void setTpSanguineo(TipoSanguineo tpSanguineo) {
        this.tpSanguineo = tpSanguineo;
    }

    public Boolean getIsEstudante() {
        return isEstudante;
    }

    public void setIsEstudante(Boolean isEstudante) {
        this.isEstudante = isEstudante;
    }

    public GrauInstrucao getGrauInstrucao() {
        return grauInstrucao;
    }

    public void setGrauInstrucao(GrauInstrucao grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    public Date getDtMigracao() {
        return dtMigracao;
    }

    public void setDtMigracao(Date dtMigracao) {
        this.dtMigracao = dtMigracao;
    }

    public Integer getCartaoCidadao() {
        return cartaoCidadao;
    }

    public void setCartaoCidadao(Integer cartaoCidadao) {
        this.cartaoCidadao = cartaoCidadao;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getDtEmissaoRg() {
        return dtEmissaoRg;
    }

    public void setDtEmissaoRg(Date dtEmissaoRg) {
        this.dtEmissaoRg = dtEmissaoRg;
    }

    public UfRg getUfRg() {
        return ufRg;
    }

    public void setUfRg(UfRg ufRg) {
        this.ufRg = ufRg;
    }

    public OrgaoExpedidor getOrgaoExpRg() {
        return orgaoExpRg;
    }

    public void setOrgaoExpRg(OrgaoExpedidor orgaoExpRg) {
        this.orgaoExpRg = orgaoExpRg;
    }

    public String getCertReservista() {
        return certReservista;
    }

    public void setCertReservista(String certReservista) {
        this.certReservista = certReservista;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Integer getNis() {
        return nis;
    }

    public void setNis(Integer nis) {
        this.nis = nis;
    }

    public String getCartProf() {
        return cartProf;
    }

    public void setCartProf(String cartProf) {
        this.cartProf = cartProf;
    }

    public Date getDtEmissaoCartProf() {
        return dtEmissaoCartProf;
    }

    public void setDtEmissaoCartProf(Date dtEmissaoCartProf) {
        this.dtEmissaoCartProf = dtEmissaoCartProf;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getEmpresaTrab() {
        return empresaTrab;
    }

    public void setEmpresaTrab(String empresaTrab) {
        this.empresaTrab = empresaTrab;
    }

    public Boolean getIsRegistrado() {
        return isRegistrado;
    }

    public void setIsRegistrado(Boolean isRegistrado) {
        this.isRegistrado = isRegistrado;
    }

    public Date getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(Date dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public Double getRenda() {
        return renda;
    }

    public void setRenda(Double renda) {
        this.renda = renda;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getInscMunicipal() {
        return inscMunicipal;
    }

    public void setInscMunicipal(String inscMunicipal) {
        this.inscMunicipal = inscMunicipal;
    }

    public Date getDtInscricao() {
        return dtInscricao;
    }

    public void setDtInscricao(Date dtInscricao) {
        this.dtInscricao = dtInscricao;
    }

    public Natureza getNatureza() {
        return natureza;
    }

    public void setNatureza(Natureza natureza) {
        this.natureza = natureza;
    }

    public Sociedade getSociedade() {
        return sociedade;
    }

    public void setSociedade(Sociedade sociedade) {
        this.sociedade = sociedade;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDtAbertura() {
        return dtAbertura;
    }

    public void setDtAbertura(Date dtAbertura) {
        this.dtAbertura = dtAbertura;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getTel4() {
        return tel4;
    }

    public void setTel4(String tel4) {
        this.tel4 = tel4;
    }

    public String getTel5() {
        return tel5;
    }

    public void setTel5(String tel5) {
        this.tel5 = tel5;
    }

    public String getTel6() {
        return tel6;
    }

    public void setTel6(String tel6) {
        this.tel6 = tel6;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Integer getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Integer idAtividade) {
        this.idAtividade = idAtividade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getSus() {
        return sus;
    }

    public void setSus(Integer sus) {
        this.sus = sus;
    }

    public Integer getIsRegProf() {
        return isRegProf;
    }

    public void setIsRegProf(Integer isRegProf) {
        this.isRegProf = isRegProf;
    }

    public Double getRendaComplementar() {
        return rendaComplementar;
    }

    public void setRendaComplementar(Double rendaComplementar) {
        this.rendaComplementar = rendaComplementar;
    }

    public String getCartorioCertidao() {
        return cartorioCertidao;
    }

    public void setCartorioCertidao(String cartorioCertidao) {
        this.cartorioCertidao = cartorioCertidao;
    }

    public String getLivroCertidao() {
        return livroCertidao;
    }

    public void setLivroCertidao(String livroCertidao) {
        this.livroCertidao = livroCertidao;
    }

    public String getFolhaCertidao() {
        return folhaCertidao;
    }

    public void setFolhaCertidao(String folhaCertidao) {
        this.folhaCertidao = folhaCertidao;
    }

    public Date getDtEmissaoCertidao() {
        return dtEmissaoCertidao;
    }

    public void setDtEmissaoCertidao(Date dtEmissaoCertidao) {
        this.dtEmissaoCertidao = dtEmissaoCertidao;
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

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getMemorando() {
        return memorando;
    }

    public void setMemorando(String memorando) {
        this.memorando = memorando;
    }

    public Integer getCertidao() {
        return certidao;
    }

    public void setCertidao(Integer certidao) {
        this.certidao = certidao;
    }

    public String getTermoCertidao() {
        return termoCertidao;
    }

    public void setTermoCertidao(String termoCertidao) {
        this.termoCertidao = termoCertidao;
    }

    public Integer getNumeroTemp() {
        return numeroTemp;
    }

    public void setNumeroTemp(Integer numeroTemp) {
        this.numeroTemp = numeroTemp;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getCadastroAtualizado() {
        return cadastroAtualizado;
    }

    public void setCadastroAtualizado(Integer cadastroAtualizado) {
        this.cadastroAtualizado = cadastroAtualizado;
    }

    public Integer getCodHabitante() {
        return codHabitante;
    }

    public void setCodHabitante(Integer codHabitante) {
        this.codHabitante = codHabitante;
    }

    public Boolean getBolsaFamilia() {
        return bolsaFamilia;
    }

    public void setBolsaFamilia(Boolean bolsaFamilia) {
        this.bolsaFamilia = bolsaFamilia;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDistritoCertidaoNasc() {
        return distritoCertidaoNasc;
    }

    public void setDistritoCertidaoNasc(String distritoCertidaoNasc) {
        this.distritoCertidaoNasc = distritoCertidaoNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(Integer estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Integer getFlsCertidaoNasc() {
        return flsCertidaoNasc;
    }

    public void setFlsCertidaoNasc(Integer flsCertidaoNasc) {
        this.flsCertidaoNasc = flsCertidaoNasc;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getLivroCertidaoNasc() {
        return livroCertidaoNasc;
    }

    public void setLivroCertidaoNasc(String livroCertidaoNasc) {
        this.livroCertidaoNasc = livroCertidaoNasc;
    }

    public String getMatriculaCertidaoNasc() {
        return matriculaCertidaoNasc;
    }

    public void setMatriculaCertidaoNasc(String matriculaCertidaoNasc) {
        this.matriculaCertidaoNasc = matriculaCertidaoNasc;
    }

    public Integer getOrgaoExpedidorRg() {
        return orgaoExpedidorRg;
    }

    public void setOrgaoExpedidorRg(Integer orgaoExpedidorRg) {
        this.orgaoExpedidorRg = orgaoExpedidorRg;
    }

    public String getSubdistritoCertidaoNasc() {
        return subdistritoCertidaoNasc;
    }

    public void setSubdistritoCertidaoNasc(String subdistritoCertidaoNasc) {
        this.subdistritoCertidaoNasc = subdistritoCertidaoNasc;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cidade getCidadeNascimento() {
        return cidadeNascimento;
    }

    public void setCidadeNascimento(Cidade cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public Cidade getOrigemMigracao() {
        return origemMigracao;
    }

    public void setOrigemMigracao(Cidade origemMigracao) {
        this.origemMigracao = origemMigracao;
    }

    public Cidade getCidadeEleitor() {
        return cidadeEleitor;
    }

    public void setCidadeEleitor(Cidade cidadeEleitor) {
        this.cidadeEleitor = cidadeEleitor;
    }

    public Cidade getCidadeCertidaoNasc() {
        return cidadeCertidaoNasc;
    }

    public void setCidadeCertidaoNasc(Cidade cidadeCertidaoNasc) {
        this.cidadeCertidaoNasc = cidadeCertidaoNasc;
    }

    public DeficienciaPessoa getDeficienciapessoa() {
        return deficienciapessoa;
    }

    public void setDeficienciapessoa(DeficienciaPessoa deficienciapessoa) {
        this.deficienciapessoa = deficienciapessoa;
    }

    public Imovel getImovelTrab() {
        return imovelTrab;
    }

    public void setImovelTrab(Imovel imovelTrab) {
        this.imovelTrab = imovelTrab;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public Religiao getReligiao() {
        return religiao;
    }

    public void setReligiao(Religiao religiao) {
        this.religiao = religiao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + '}';
    }

}
