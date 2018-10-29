
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataInicial" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DataFinal" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="StatusLicenca" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="StatusSolicitacao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Cnae" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idMunicipio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Risco" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dataInicial",
    "dataFinal",
    "statusLicenca",
    "statusSolicitacao",
    "cnae",
    "idMunicipio",
    "risco"
})
@XmlRootElement(name = "ConsultarNumeroProtocolosRequest")
public class ConsultarNumeroProtocolosRequest {

    @XmlElement(name = "DataInicial", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInicial;
    @XmlElement(name = "DataFinal", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFinal;
    @XmlElement(name = "StatusLicenca", required = true, type = Integer.class, nillable = true)
    protected Integer statusLicenca;
    @XmlElement(name = "StatusSolicitacao", required = true, type = Integer.class, nillable = true)
    protected Integer statusSolicitacao;
    @XmlElement(name = "Cnae", required = true, type = Integer.class, nillable = true)
    protected Integer cnae;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idMunicipio;
    @XmlElement(name = "Risco", required = true, type = Boolean.class, nillable = true)
    protected Boolean risco;

    /**
     * Obtém o valor da propriedade dataInicial.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInicial() {
        return dataInicial;
    }

    /**
     * Define o valor da propriedade dataInicial.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInicial(XMLGregorianCalendar value) {
        this.dataInicial = value;
    }

    /**
     * Obtém o valor da propriedade dataFinal.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFinal() {
        return dataFinal;
    }

    /**
     * Define o valor da propriedade dataFinal.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFinal(XMLGregorianCalendar value) {
        this.dataFinal = value;
    }

    /**
     * Obtém o valor da propriedade statusLicenca.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStatusLicenca() {
        return statusLicenca;
    }

    /**
     * Define o valor da propriedade statusLicenca.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStatusLicenca(Integer value) {
        this.statusLicenca = value;
    }

    /**
     * Obtém o valor da propriedade statusSolicitacao.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStatusSolicitacao() {
        return statusSolicitacao;
    }

    /**
     * Define o valor da propriedade statusSolicitacao.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStatusSolicitacao(Integer value) {
        this.statusSolicitacao = value;
    }

    /**
     * Obtém o valor da propriedade cnae.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCnae() {
        return cnae;
    }

    /**
     * Define o valor da propriedade cnae.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCnae(Integer value) {
        this.cnae = value;
    }

    /**
     * Obtém o valor da propriedade idMunicipio.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    /**
     * Define o valor da propriedade idMunicipio.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdMunicipio(Integer value) {
        this.idMunicipio = value;
    }

    /**
     * Obtém o valor da propriedade risco.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRisco() {
        return risco;
    }

    /**
     * Define o valor da propriedade risco.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRisco(Boolean value) {
        this.risco = value;
    }

}
