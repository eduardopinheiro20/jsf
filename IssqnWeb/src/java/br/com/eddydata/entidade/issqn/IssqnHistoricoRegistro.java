/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.referencia.Cor;
import br.com.eddydata.entidade.referencia.EstadoCivil;
import br.com.eddydata.entidade.referencia.GrauInstrucao;
import br.com.eddydata.entidade.referencia.OrgaoExpedidor;
import br.com.eddydata.entidade.referencia.Sexo;
import br.com.eddydata.entidade.referencia.TipoPessoa;
import br.com.eddydata.entidade.referencia.TipoSanguineo;
import br.com.eddydata.entidade.referencia.UfRg;
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
 * @author David
 */
@Entity
@Table(name = "issqn_historico_registro")
public class IssqnHistoricoRegistro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "data_cadastro")
    @Temporal(value = TemporalType.DATE)
    private Date dataCadastro;

    @JoinColumn(name = "id_iss", referencedColumnName = "id_iss")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issqn iss;

    @Column(name = "nome", length = 200)
    private String nome;

    @Column(name = "nome_fantasia", length = 300)
    private String nomeFantasia;

    @Column(name = "tp_pessoa")
    private TipoPessoa tpPessoa;

    @Column(name = "cpf_cnpj", length = 20)
    private String cpfCnpj;

    @Column(name = "cartao_cidadao")
    private Integer cartaoCidadao;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "tel1", length = 50)
    private String tel1;

    @Column(name = "tel2", length = 50)
    private String tel2;

    @Column(name = "data_nascimento")
    @Temporal(value = TemporalType.DATE)
    private Date dataNascimento;

    @Column(name = "rg", length = 20)
    private String rg;

    @Column(name = "dt_emissao_rg")
    @Temporal(value = TemporalType.DATE)
    private Date dtEmissaoRg;

    @Column(name = "e_mail", length = 150)
    private String email;

    @Column(name = "orgao_exp_rg")
    private OrgaoExpedidor orgaoExpRg;

    @Column(name = "cert_reservista", length = 50)
    private String certReservista;

    @Column(name = "grau_instrucao")
    private GrauInstrucao grauInstrucao;

    @Column(name = "est_civil")
    private EstadoCivil estCivil;

    @Column(name = "cor")
    private Cor cor;

    @Column(name = "uf_rg")
    private UfRg ufRg;

    @Column(name = "sexo")
    private Sexo sexo;

    @Column(name = "tp_sanguineo")
    private TipoSanguineo tpSanguineo;

    @Column(name = "insc_estadual", length = 50)
    private String inscEstadual;

    @Column(name = "insc_municipal", length = 50)
    private String inscMunicipal;

    public IssqnHistoricoRegistro() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Issqn getIss() {
        return iss;
    }

    public void setIss(Issqn iss) {
        this.iss = iss;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public TipoPessoa getTpPessoa() {
        return tpPessoa;
    }

    public void setTpPessoa(TipoPessoa tpPessoa) {
        this.tpPessoa = tpPessoa;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Integer getCartaoCidadao() {
        return cartaoCidadao;
    }

    public void setCartaoCidadao(Integer cartaoCidadao) {
        this.cartaoCidadao = cartaoCidadao;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public GrauInstrucao getGrauInstrucao() {
        return grauInstrucao;
    }

    public void setGrauInstrucao(GrauInstrucao grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    public EstadoCivil getEstCivil() {
        return estCivil;
    }

    public void setEstCivil(EstadoCivil estCivil) {
        this.estCivil = estCivil;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public UfRg getUfRg() {
        return ufRg;
    }

    public void setUfRg(UfRg ufRg) {
        this.ufRg = ufRg;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public TipoSanguineo getTpSanguineo() {
        return tpSanguineo;
    }

    public void setTpSanguineo(TipoSanguineo tpSanguineo) {
        this.tpSanguineo = tpSanguineo;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getInscMunicipal() {
        return inscMunicipal;
    }

    public void setInscMunicipal(String inscMunicipal) {
        this.inscMunicipal = inscMunicipal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof IssqnHistorico)) {
            return false;
        }
        IssqnHistoricoRegistro other = (IssqnHistoricoRegistro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
