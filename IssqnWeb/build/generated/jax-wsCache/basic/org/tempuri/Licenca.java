
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de Licenca complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Licenca">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdSolicitacao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IdT033" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IdT110" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Orgao" type="{http://tempuri.org/}Orgao" minOccurs="0"/>
 *         &lt;element name="Risco" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SituacaoLicenca" type="{http://tempuri.org/}StatusInformarLicenca" minOccurs="0"/>
 *         &lt;element name="Protocolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataValidade" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DataEmissao" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DataProtocolo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Viabilidade" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Restricoes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Orientacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RestricoesViabilidade" type="{http://tempuri.org/}ArrayOfRestricaoViabilidade" minOccurs="0"/>
 *         &lt;element name="MotivosIndeferimentoMunicipal" type="{http://tempuri.org/}ArrayOfMotivoIndeferimentoMunicipal" minOccurs="0"/>
 *         &lt;element name="RestricoesOperacionais" type="{http://tempuri.org/}ArrayOfRestricaoOperacao" minOccurs="0"/>
 *         &lt;element name="Irregularidades" type="{http://tempuri.org/}ArrayOfIrregularidade" minOccurs="0"/>
 *         &lt;element name="MotivosCassacao" type="{http://tempuri.org/}ArrayOfMotivoCassacao" minOccurs="0"/>
 *         &lt;element name="MotivosCancelamentoCassacao" type="{http://tempuri.org/}ArrayOfMotivoCancelamentoCassacao" minOccurs="0"/>
 *         &lt;element name="Perguntas" type="{http://tempuri.org/}ArrayOfPerguntaResposta" minOccurs="0"/>
 *         &lt;element name="Declaracoes" type="{http://tempuri.org/}ArrayOfDeclaracaoAceite" minOccurs="0"/>
 *         &lt;element name="Orientacoes" type="{http://tempuri.org/}ArrayOfOrientacaoAceite" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
@XmlType(name = "Licenca", propOrder = {
    "idSolicitacao",
    "idT033",
    "idT110",
    "orgao",
    "risco",
    "situacaoLicenca",
    "protocolo",
    "numero",
    "dataValidade",
    "dataEmissao",
    "dataProtocolo",
    "viabilidade",
    "restricoes",
    "orientacao",
    "restricoesViabilidade",
    "motivosIndeferimentoMunicipal",
    "restricoesOperacionais",
    "irregularidades",
    "motivosCassacao",
    "motivosCancelamentoCassacao",
    "perguntas",
    "declaracoes",
    "orientacoes",
    "endDate"
})
public class Licenca {

    @XmlElement(name = "IdSolicitacao")
    protected int idSolicitacao;
    @XmlElement(name = "IdT033")
    protected int idT033;
    @XmlElement(name = "IdT110")
    protected int idT110;
    @XmlElement(name = "Orgao")
    protected Orgao orgao;
    @XmlElement(name = "Risco")
    protected boolean risco;
    @XmlElement(name = "SituacaoLicenca")
    protected StatusInformarLicenca situacaoLicenca;
    @XmlElement(name = "Protocolo")
    protected String protocolo;
    @XmlElement(name = "Numero")
    protected String numero;
    @XmlElementRef(name = "DataValidade", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataValidade;
    @XmlElementRef(name = "DataEmissao", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataEmissao;
    @XmlElementRef(name = "DataProtocolo", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataProtocolo;
    @XmlElement(name = "Viabilidade")
    protected boolean viabilidade;
    @XmlElement(name = "Restricoes")
    protected int restricoes;
    @XmlElement(name = "Orientacao")
    protected String orientacao;
    @XmlElement(name = "RestricoesViabilidade")
    protected ArrayOfRestricaoViabilidade restricoesViabilidade;
    @XmlElement(name = "MotivosIndeferimentoMunicipal")
    protected ArrayOfMotivoIndeferimentoMunicipal motivosIndeferimentoMunicipal;
    @XmlElement(name = "RestricoesOperacionais")
    protected ArrayOfRestricaoOperacao restricoesOperacionais;
    @XmlElement(name = "Irregularidades")
    protected ArrayOfIrregularidade irregularidades;
    @XmlElement(name = "MotivosCassacao")
    protected ArrayOfMotivoCassacao motivosCassacao;
    @XmlElement(name = "MotivosCancelamentoCassacao")
    protected ArrayOfMotivoCancelamentoCassacao motivosCancelamentoCassacao;
    @XmlElement(name = "Perguntas")
    protected ArrayOfPerguntaResposta perguntas;
    @XmlElement(name = "Declaracoes")
    protected ArrayOfDeclaracaoAceite declaracoes;
    @XmlElement(name = "Orientacoes")
    protected ArrayOfOrientacaoAceite orientacoes;
    @XmlElement(name = "EndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

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
     * Obtém o valor da propriedade idT033.
     * 
     */
    public int getIdT033() {
        return idT033;
    }

    /**
     * Define o valor da propriedade idT033.
     * 
     */
    public void setIdT033(int value) {
        this.idT033 = value;
    }

    /**
     * Obtém o valor da propriedade idT110.
     * 
     */
    public int getIdT110() {
        return idT110;
    }

    /**
     * Define o valor da propriedade idT110.
     * 
     */
    public void setIdT110(int value) {
        this.idT110 = value;
    }

    /**
     * Obtém o valor da propriedade orgao.
     * 
     * @return
     *     possible object is
     *     {@link Orgao }
     *     
     */
    public Orgao getOrgao() {
        return orgao;
    }

    /**
     * Define o valor da propriedade orgao.
     * 
     * @param value
     *     allowed object is
     *     {@link Orgao }
     *     
     */
    public void setOrgao(Orgao value) {
        this.orgao = value;
    }

    /**
     * Obtém o valor da propriedade risco.
     * 
     */
    public boolean isRisco() {
        return risco;
    }

    /**
     * Define o valor da propriedade risco.
     * 
     */
    public void setRisco(boolean value) {
        this.risco = value;
    }

    /**
     * Obtém o valor da propriedade situacaoLicenca.
     * 
     * @return
     *     possible object is
     *     {@link StatusInformarLicenca }
     *     
     */
    public StatusInformarLicenca getSituacaoLicenca() {
        return situacaoLicenca;
    }

    /**
     * Define o valor da propriedade situacaoLicenca.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusInformarLicenca }
     *     
     */
    public void setSituacaoLicenca(StatusInformarLicenca value) {
        this.situacaoLicenca = value;
    }

    /**
     * Obtém o valor da propriedade protocolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolo() {
        return protocolo;
    }

    /**
     * Define o valor da propriedade protocolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolo(String value) {
        this.protocolo = value;
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
     * Obtém o valor da propriedade dataValidade.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataValidade() {
        return dataValidade;
    }

    /**
     * Define o valor da propriedade dataValidade.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataValidade(JAXBElement<XMLGregorianCalendar> value) {
        this.dataValidade = value;
    }

    /**
     * Obtém o valor da propriedade dataEmissao.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define o valor da propriedade dataEmissao.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataEmissao(JAXBElement<XMLGregorianCalendar> value) {
        this.dataEmissao = value;
    }

    /**
     * Obtém o valor da propriedade dataProtocolo.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataProtocolo() {
        return dataProtocolo;
    }

    /**
     * Define o valor da propriedade dataProtocolo.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataProtocolo(JAXBElement<XMLGregorianCalendar> value) {
        this.dataProtocolo = value;
    }

    /**
     * Obtém o valor da propriedade viabilidade.
     * 
     */
    public boolean isViabilidade() {
        return viabilidade;
    }

    /**
     * Define o valor da propriedade viabilidade.
     * 
     */
    public void setViabilidade(boolean value) {
        this.viabilidade = value;
    }

    /**
     * Obtém o valor da propriedade restricoes.
     * 
     */
    public int getRestricoes() {
        return restricoes;
    }

    /**
     * Define o valor da propriedade restricoes.
     * 
     */
    public void setRestricoes(int value) {
        this.restricoes = value;
    }

    /**
     * Obtém o valor da propriedade orientacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrientacao() {
        return orientacao;
    }

    /**
     * Define o valor da propriedade orientacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrientacao(String value) {
        this.orientacao = value;
    }

    /**
     * Obtém o valor da propriedade restricoesViabilidade.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRestricaoViabilidade }
     *     
     */
    public ArrayOfRestricaoViabilidade getRestricoesViabilidade() {
        return restricoesViabilidade;
    }

    /**
     * Define o valor da propriedade restricoesViabilidade.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRestricaoViabilidade }
     *     
     */
    public void setRestricoesViabilidade(ArrayOfRestricaoViabilidade value) {
        this.restricoesViabilidade = value;
    }

    /**
     * Obtém o valor da propriedade motivosIndeferimentoMunicipal.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMotivoIndeferimentoMunicipal }
     *     
     */
    public ArrayOfMotivoIndeferimentoMunicipal getMotivosIndeferimentoMunicipal() {
        return motivosIndeferimentoMunicipal;
    }

    /**
     * Define o valor da propriedade motivosIndeferimentoMunicipal.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMotivoIndeferimentoMunicipal }
     *     
     */
    public void setMotivosIndeferimentoMunicipal(ArrayOfMotivoIndeferimentoMunicipal value) {
        this.motivosIndeferimentoMunicipal = value;
    }

    /**
     * Obtém o valor da propriedade restricoesOperacionais.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRestricaoOperacao }
     *     
     */
    public ArrayOfRestricaoOperacao getRestricoesOperacionais() {
        return restricoesOperacionais;
    }

    /**
     * Define o valor da propriedade restricoesOperacionais.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRestricaoOperacao }
     *     
     */
    public void setRestricoesOperacionais(ArrayOfRestricaoOperacao value) {
        this.restricoesOperacionais = value;
    }

    /**
     * Obtém o valor da propriedade irregularidades.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfIrregularidade }
     *     
     */
    public ArrayOfIrregularidade getIrregularidades() {
        return irregularidades;
    }

    /**
     * Define o valor da propriedade irregularidades.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfIrregularidade }
     *     
     */
    public void setIrregularidades(ArrayOfIrregularidade value) {
        this.irregularidades = value;
    }

    /**
     * Obtém o valor da propriedade motivosCassacao.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMotivoCassacao }
     *     
     */
    public ArrayOfMotivoCassacao getMotivosCassacao() {
        return motivosCassacao;
    }

    /**
     * Define o valor da propriedade motivosCassacao.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMotivoCassacao }
     *     
     */
    public void setMotivosCassacao(ArrayOfMotivoCassacao value) {
        this.motivosCassacao = value;
    }

    /**
     * Obtém o valor da propriedade motivosCancelamentoCassacao.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMotivoCancelamentoCassacao }
     *     
     */
    public ArrayOfMotivoCancelamentoCassacao getMotivosCancelamentoCassacao() {
        return motivosCancelamentoCassacao;
    }

    /**
     * Define o valor da propriedade motivosCancelamentoCassacao.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMotivoCancelamentoCassacao }
     *     
     */
    public void setMotivosCancelamentoCassacao(ArrayOfMotivoCancelamentoCassacao value) {
        this.motivosCancelamentoCassacao = value;
    }

    /**
     * Obtém o valor da propriedade perguntas.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPerguntaResposta }
     *     
     */
    public ArrayOfPerguntaResposta getPerguntas() {
        return perguntas;
    }

    /**
     * Define o valor da propriedade perguntas.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPerguntaResposta }
     *     
     */
    public void setPerguntas(ArrayOfPerguntaResposta value) {
        this.perguntas = value;
    }

    /**
     * Obtém o valor da propriedade declaracoes.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDeclaracaoAceite }
     *     
     */
    public ArrayOfDeclaracaoAceite getDeclaracoes() {
        return declaracoes;
    }

    /**
     * Define o valor da propriedade declaracoes.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDeclaracaoAceite }
     *     
     */
    public void setDeclaracoes(ArrayOfDeclaracaoAceite value) {
        this.declaracoes = value;
    }

    /**
     * Obtém o valor da propriedade orientacoes.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrientacaoAceite }
     *     
     */
    public ArrayOfOrientacaoAceite getOrientacoes() {
        return orientacoes;
    }

    /**
     * Define o valor da propriedade orientacoes.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrientacaoAceite }
     *     
     */
    public void setOrientacoes(ArrayOfOrientacaoAceite value) {
        this.orientacoes = value;
    }

    /**
     * Obtém o valor da propriedade endDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Define o valor da propriedade endDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
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
