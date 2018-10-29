
package org.tempuri;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de Empresa complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Empresa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CpfResponsavel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DDD1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telefone1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DDD2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telefone2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CnpjContador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CpfContador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cpnj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nire" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NomeEmpresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NaturezaJuridica" type="{http://tempuri.org/}NaturezaJuridica" minOccurs="0"/>
 *         &lt;element name="SetorQuadraLote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoLogradouro" type="{http://tempuri.org/}TipoLogradouro" minOccurs="0"/>
 *         &lt;element name="Logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Complemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bairro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Municipio" type="{http://tempuri.org/}Municipio" minOccurs="0"/>
 *         &lt;element name="Cep" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Referencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HoraInclusao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Porte" type="{http://tempuri.org/}Porte" minOccurs="0"/>
 *         &lt;element name="Mei" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CpfAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdSolicitacao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AreaEstabelecimento" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="AreaImovel" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="AtividadesCNAE" type="{http://tempuri.org/}ArrayOfAtividadeCNAE" minOccurs="0"/>
 *         &lt;element name="AtividadesAuxiliares" type="{http://tempuri.org/}ArrayOfAtividadeAuxiliar" minOccurs="0"/>
 *         &lt;element name="Socios" type="{http://tempuri.org/}ArrayOfResponsavelEmpresa" minOccurs="0"/>
 *         &lt;element name="Licencas" type="{http://tempuri.org/}ArrayOfLicenca" minOccurs="0"/>
 *         &lt;element name="DeclaracaoBombeiro" type="{http://tempuri.org/}DeclaracaoBombeiro" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Empresa", propOrder = {
    "cpfResponsavel",
    "email",
    "ddd1",
    "telefone1",
    "ddd2",
    "telefone2",
    "cnpjContador",
    "cpfContador",
    "cpnj",
    "nire",
    "nomeEmpresa",
    "naturezaJuridica",
    "setorQuadraLote",
    "tipoLogradouro",
    "logradouro",
    "numero",
    "complemento",
    "bairro",
    "municipio",
    "cep",
    "referencia",
    "horaInclusao",
    "porte",
    "mei",
    "cpfAut",
    "idSolicitacao",
    "areaEstabelecimento",
    "areaImovel",
    "atividadesCNAE",
    "atividadesAuxiliares",
    "socios",
    "licencas",
    "declaracaoBombeiro"
})
public class Empresa {

    @XmlElement(name = "CpfResponsavel")
    protected String cpfResponsavel;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "DDD1")
    protected String ddd1;
    @XmlElement(name = "Telefone1")
    protected String telefone1;
    @XmlElement(name = "DDD2")
    protected String ddd2;
    @XmlElement(name = "Telefone2")
    protected String telefone2;
    @XmlElement(name = "CnpjContador")
    protected String cnpjContador;
    @XmlElement(name = "CpfContador")
    protected String cpfContador;
    @XmlElement(name = "Cpnj")
    protected String cpnj;
    @XmlElement(name = "Nire")
    protected String nire;
    @XmlElement(name = "NomeEmpresa")
    protected String nomeEmpresa;
    @XmlElement(name = "NaturezaJuridica")
    protected NaturezaJuridica naturezaJuridica;
    @XmlElement(name = "SetorQuadraLote")
    protected String setorQuadraLote;
    @XmlElement(name = "TipoLogradouro")
    protected TipoLogradouro tipoLogradouro;
    @XmlElement(name = "Logradouro")
    protected String logradouro;
    @XmlElement(name = "Numero")
    protected String numero;
    @XmlElement(name = "Complemento")
    protected String complemento;
    @XmlElement(name = "Bairro")
    protected String bairro;
    @XmlElement(name = "Municipio")
    protected Municipio municipio;
    @XmlElement(name = "Cep")
    protected String cep;
    @XmlElement(name = "Referencia")
    protected String referencia;
    @XmlElement(name = "HoraInclusao", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar horaInclusao;
    @XmlElement(name = "Porte")
    protected Porte porte;
    @XmlElement(name = "Mei")
    protected boolean mei;
    @XmlElement(name = "CpfAut")
    protected String cpfAut;
    @XmlElement(name = "IdSolicitacao")
    protected int idSolicitacao;
    @XmlElement(name = "AreaEstabelecimento", required = true)
    protected BigDecimal areaEstabelecimento;
    @XmlElement(name = "AreaImovel", required = true)
    protected BigDecimal areaImovel;
    @XmlElement(name = "AtividadesCNAE")
    protected ArrayOfAtividadeCNAE atividadesCNAE;
    @XmlElement(name = "AtividadesAuxiliares")
    protected ArrayOfAtividadeAuxiliar atividadesAuxiliares;
    @XmlElement(name = "Socios")
    protected ArrayOfResponsavelEmpresa socios;
    @XmlElement(name = "Licencas")
    protected ArrayOfLicenca licencas;
    @XmlElement(name = "DeclaracaoBombeiro")
    protected DeclaracaoBombeiro declaracaoBombeiro;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade cpfResponsavel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    /**
     * Define o valor da propriedade cpfResponsavel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpfResponsavel(String value) {
        this.cpfResponsavel = value;
    }

    /**
     * Obtém o valor da propriedade email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o valor da propriedade email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Obtém o valor da propriedade ddd1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDDD1() {
        return ddd1;
    }

    /**
     * Define o valor da propriedade ddd1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDDD1(String value) {
        this.ddd1 = value;
    }

    /**
     * Obtém o valor da propriedade telefone1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefone1() {
        return telefone1;
    }

    /**
     * Define o valor da propriedade telefone1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefone1(String value) {
        this.telefone1 = value;
    }

    /**
     * Obtém o valor da propriedade ddd2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDDD2() {
        return ddd2;
    }

    /**
     * Define o valor da propriedade ddd2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDDD2(String value) {
        this.ddd2 = value;
    }

    /**
     * Obtém o valor da propriedade telefone2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefone2() {
        return telefone2;
    }

    /**
     * Define o valor da propriedade telefone2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefone2(String value) {
        this.telefone2 = value;
    }

    /**
     * Obtém o valor da propriedade cnpjContador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnpjContador() {
        return cnpjContador;
    }

    /**
     * Define o valor da propriedade cnpjContador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnpjContador(String value) {
        this.cnpjContador = value;
    }

    /**
     * Obtém o valor da propriedade cpfContador.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpfContador() {
        return cpfContador;
    }

    /**
     * Define o valor da propriedade cpfContador.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpfContador(String value) {
        this.cpfContador = value;
    }

    /**
     * Obtém o valor da propriedade cpnj.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpnj() {
        return cpnj;
    }

    /**
     * Define o valor da propriedade cpnj.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpnj(String value) {
        this.cpnj = value;
    }

    /**
     * Obtém o valor da propriedade nire.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNire() {
        return nire;
    }

    /**
     * Define o valor da propriedade nire.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNire(String value) {
        this.nire = value;
    }

    /**
     * Obtém o valor da propriedade nomeEmpresa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    /**
     * Define o valor da propriedade nomeEmpresa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeEmpresa(String value) {
        this.nomeEmpresa = value;
    }

    /**
     * Obtém o valor da propriedade naturezaJuridica.
     * 
     * @return
     *     possible object is
     *     {@link NaturezaJuridica }
     *     
     */
    public NaturezaJuridica getNaturezaJuridica() {
        return naturezaJuridica;
    }

    /**
     * Define o valor da propriedade naturezaJuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link NaturezaJuridica }
     *     
     */
    public void setNaturezaJuridica(NaturezaJuridica value) {
        this.naturezaJuridica = value;
    }

    /**
     * Obtém o valor da propriedade setorQuadraLote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetorQuadraLote() {
        return setorQuadraLote;
    }

    /**
     * Define o valor da propriedade setorQuadraLote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetorQuadraLote(String value) {
        this.setorQuadraLote = value;
    }

    /**
     * Obtém o valor da propriedade tipoLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link TipoLogradouro }
     *     
     */
    public TipoLogradouro getTipoLogradouro() {
        return tipoLogradouro;
    }

    /**
     * Define o valor da propriedade tipoLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoLogradouro }
     *     
     */
    public void setTipoLogradouro(TipoLogradouro value) {
        this.tipoLogradouro = value;
    }

    /**
     * Obtém o valor da propriedade logradouro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Define o valor da propriedade logradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogradouro(String value) {
        this.logradouro = value;
    }

    /**
     * Obtém o valor da propriedade numero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Define o valor da propriedade numero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Obtém o valor da propriedade complemento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Define o valor da propriedade complemento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplemento(String value) {
        this.complemento = value;
    }

    /**
     * Obtém o valor da propriedade bairro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Define o valor da propriedade bairro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBairro(String value) {
        this.bairro = value;
    }

    /**
     * Obtém o valor da propriedade municipio.
     * 
     * @return
     *     possible object is
     *     {@link Municipio }
     *     
     */
    public Municipio getMunicipio() {
        return municipio;
    }

    /**
     * Define o valor da propriedade municipio.
     * 
     * @param value
     *     allowed object is
     *     {@link Municipio }
     *     
     */
    public void setMunicipio(Municipio value) {
        this.municipio = value;
    }

    /**
     * Obtém o valor da propriedade cep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCep() {
        return cep;
    }

    /**
     * Define o valor da propriedade cep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCep(String value) {
        this.cep = value;
    }

    /**
     * Obtém o valor da propriedade referencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Define o valor da propriedade referencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia(String value) {
        this.referencia = value;
    }

    /**
     * Obtém o valor da propriedade horaInclusao.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHoraInclusao() {
        return horaInclusao;
    }

    /**
     * Define o valor da propriedade horaInclusao.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHoraInclusao(XMLGregorianCalendar value) {
        this.horaInclusao = value;
    }

    /**
     * Obtém o valor da propriedade porte.
     * 
     * @return
     *     possible object is
     *     {@link Porte }
     *     
     */
    public Porte getPorte() {
        return porte;
    }

    /**
     * Define o valor da propriedade porte.
     * 
     * @param value
     *     allowed object is
     *     {@link Porte }
     *     
     */
    public void setPorte(Porte value) {
        this.porte = value;
    }

    /**
     * Obtém o valor da propriedade mei.
     * 
     */
    public boolean isMei() {
        return mei;
    }

    /**
     * Define o valor da propriedade mei.
     * 
     */
    public void setMei(boolean value) {
        this.mei = value;
    }

    /**
     * Obtém o valor da propriedade cpfAut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpfAut() {
        return cpfAut;
    }

    /**
     * Define o valor da propriedade cpfAut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpfAut(String value) {
        this.cpfAut = value;
    }

    /**
     * Obtém o valor da propriedade idSolicitacao.
     * 
     */
    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    /**
     * Define o valor da propriedade idSolicitacao.
     * 
     */
    public void setIdSolicitacao(int value) {
        this.idSolicitacao = value;
    }

    /**
     * Obtém o valor da propriedade areaEstabelecimento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAreaEstabelecimento() {
        return areaEstabelecimento;
    }

    /**
     * Define o valor da propriedade areaEstabelecimento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAreaEstabelecimento(BigDecimal value) {
        this.areaEstabelecimento = value;
    }

    /**
     * Obtém o valor da propriedade areaImovel.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAreaImovel() {
        return areaImovel;
    }

    /**
     * Define o valor da propriedade areaImovel.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAreaImovel(BigDecimal value) {
        this.areaImovel = value;
    }

    /**
     * Obtém o valor da propriedade atividadesCNAE.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAtividadeCNAE }
     *     
     */
    public ArrayOfAtividadeCNAE getAtividadesCNAE() {
        return atividadesCNAE;
    }

    /**
     * Define o valor da propriedade atividadesCNAE.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAtividadeCNAE }
     *     
     */
    public void setAtividadesCNAE(ArrayOfAtividadeCNAE value) {
        this.atividadesCNAE = value;
    }

    /**
     * Obtém o valor da propriedade atividadesAuxiliares.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAtividadeAuxiliar }
     *     
     */
    public ArrayOfAtividadeAuxiliar getAtividadesAuxiliares() {
        return atividadesAuxiliares;
    }

    /**
     * Define o valor da propriedade atividadesAuxiliares.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAtividadeAuxiliar }
     *     
     */
    public void setAtividadesAuxiliares(ArrayOfAtividadeAuxiliar value) {
        this.atividadesAuxiliares = value;
    }

    /**
     * Obtém o valor da propriedade socios.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfResponsavelEmpresa }
     *     
     */
    public ArrayOfResponsavelEmpresa getSocios() {
        return socios;
    }

    /**
     * Define o valor da propriedade socios.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfResponsavelEmpresa }
     *     
     */
    public void setSocios(ArrayOfResponsavelEmpresa value) {
        this.socios = value;
    }

    /**
     * Obtém o valor da propriedade licencas.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLicenca }
     *     
     */
    public ArrayOfLicenca getLicencas() {
        return licencas;
    }

    /**
     * Define o valor da propriedade licencas.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLicenca }
     *     
     */
    public void setLicencas(ArrayOfLicenca value) {
        this.licencas = value;
    }

    /**
     * Obtém o valor da propriedade declaracaoBombeiro.
     * 
     * @return
     *     possible object is
     *     {@link DeclaracaoBombeiro }
     *     
     */
    public DeclaracaoBombeiro getDeclaracaoBombeiro() {
        return declaracaoBombeiro;
    }

    /**
     * Define o valor da propriedade declaracaoBombeiro.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclaracaoBombeiro }
     *     
     */
    public void setDeclaracaoBombeiro(DeclaracaoBombeiro value) {
        this.declaracaoBombeiro = value;
    }

    /**
     * Obtém o valor da propriedade id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}
