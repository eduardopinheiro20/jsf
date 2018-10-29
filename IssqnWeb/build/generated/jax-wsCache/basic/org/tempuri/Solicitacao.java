
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de Solicitacao complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Solicitacao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Empresa" type="{http://tempuri.org/}Empresa" minOccurs="0"/>
 *         &lt;element name="NomeSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CpfSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Contabilista" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="EmailSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumeroProtocolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusSolicitacao" type="{http://tempuri.org/}StatusSolicitacaoLicenca" minOccurs="0"/>
 *         &lt;element name="DataHoraInclusao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PendenteViabilidade" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CpfAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Licencas" type="{http://tempuri.org/}ArrayOfLicenca" minOccurs="0"/>
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
@XmlType(name = "Solicitacao", propOrder = {
    "empresa",
    "nomeSolicitante",
    "cpfSolicitante",
    "contabilista",
    "emailSolicitante",
    "numeroProtocolo",
    "statusSolicitacao",
    "dataHoraInclusao",
    "pendenteViabilidade",
    "cpfAut",
    "licencas"
})
public class Solicitacao {

    @XmlElement(name = "Empresa")
    protected Empresa empresa;
    @XmlElement(name = "NomeSolicitante")
    protected String nomeSolicitante;
    @XmlElement(name = "CpfSolicitante")
    protected String cpfSolicitante;
    @XmlElement(name = "Contabilista")
    protected boolean contabilista;
    @XmlElement(name = "EmailSolicitante")
    protected String emailSolicitante;
    @XmlElement(name = "NumeroProtocolo")
    protected String numeroProtocolo;
    @XmlElement(name = "StatusSolicitacao")
    protected StatusSolicitacaoLicenca statusSolicitacao;
    @XmlElement(name = "DataHoraInclusao", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataHoraInclusao;
    @XmlElement(name = "PendenteViabilidade")
    protected boolean pendenteViabilidade;
    @XmlElement(name = "CpfAut")
    protected String cpfAut;
    @XmlElement(name = "Licencas")
    protected ArrayOfLicenca licencas;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade empresa.
     * 
     * @return
     *     possible object is
     *     {@link Empresa }
     *     
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Define o valor da propriedade empresa.
     * 
     * @param value
     *     allowed object is
     *     {@link Empresa }
     *     
     */
    public void setEmpresa(Empresa value) {
        this.empresa = value;
    }

    /**
     * Obtém o valor da propriedade nomeSolicitante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    /**
     * Define o valor da propriedade nomeSolicitante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeSolicitante(String value) {
        this.nomeSolicitante = value;
    }

    /**
     * Obtém o valor da propriedade cpfSolicitante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpfSolicitante() {
        return cpfSolicitante;
    }

    /**
     * Define o valor da propriedade cpfSolicitante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpfSolicitante(String value) {
        this.cpfSolicitante = value;
    }

    /**
     * Obtém o valor da propriedade contabilista.
     * 
     */
    public boolean isContabilista() {
        return contabilista;
    }

    /**
     * Define o valor da propriedade contabilista.
     * 
     */
    public void setContabilista(boolean value) {
        this.contabilista = value;
    }

    /**
     * Obtém o valor da propriedade emailSolicitante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailSolicitante() {
        return emailSolicitante;
    }

    /**
     * Define o valor da propriedade emailSolicitante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailSolicitante(String value) {
        this.emailSolicitante = value;
    }

    /**
     * Obtém o valor da propriedade numeroProtocolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    /**
     * Define o valor da propriedade numeroProtocolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolo(String value) {
        this.numeroProtocolo = value;
    }

    /**
     * Obtém o valor da propriedade statusSolicitacao.
     * 
     * @return
     *     possible object is
     *     {@link StatusSolicitacaoLicenca }
     *     
     */
    public StatusSolicitacaoLicenca getStatusSolicitacao() {
        return statusSolicitacao;
    }

    /**
     * Define o valor da propriedade statusSolicitacao.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusSolicitacaoLicenca }
     *     
     */
    public void setStatusSolicitacao(StatusSolicitacaoLicenca value) {
        this.statusSolicitacao = value;
    }

    /**
     * Obtém o valor da propriedade dataHoraInclusao.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    /**
     * Define o valor da propriedade dataHoraInclusao.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataHoraInclusao(XMLGregorianCalendar value) {
        this.dataHoraInclusao = value;
    }

    /**
     * Obtém o valor da propriedade pendenteViabilidade.
     * 
     */
    public boolean isPendenteViabilidade() {
        return pendenteViabilidade;
    }

    /**
     * Define o valor da propriedade pendenteViabilidade.
     * 
     */
    public void setPendenteViabilidade(boolean value) {
        this.pendenteViabilidade = value;
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
