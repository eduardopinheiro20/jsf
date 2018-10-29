
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de InformarResultadoLicenciamentoAltoRiscoParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="InformarResultadoLicenciamentoAltoRiscoParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Protocolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumeroLicenca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataEmissao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DataValidade" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IrregularidadesRestricoes" type="{http://tempuri.org/}ArrayOfInt" minOccurs="0"/>
 *         &lt;element name="IdOrgao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformarResultadoLicenciamentoAltoRiscoParameters", propOrder = {
    "protocolo",
    "resultado",
    "numeroLicenca",
    "dataEmissao",
    "dataValidade",
    "irregularidadesRestricoes",
    "idOrgao"
})
public class InformarResultadoLicenciamentoAltoRiscoParameters {

    @XmlElement(name = "Protocolo")
    protected String protocolo;
    @XmlElement(name = "Resultado")
    protected int resultado;
    @XmlElement(name = "NumeroLicenca")
    protected String numeroLicenca;
    @XmlElement(name = "DataEmissao", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissao;
    @XmlElement(name = "DataValidade", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataValidade;
    @XmlElement(name = "IrregularidadesRestricoes")
    protected ArrayOfInt irregularidadesRestricoes;
    @XmlElement(name = "IdOrgao")
    protected int idOrgao;

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
     * Obtém o valor da propriedade resultado.
     * 
     */
    public int getResultado() {
        return resultado;
    }

    /**
     * Define o valor da propriedade resultado.
     * 
     */
    public void setResultado(int value) {
        this.resultado = value;
    }

    /**
     * Obtém o valor da propriedade numeroLicenca.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroLicenca() {
        return numeroLicenca;
    }

    /**
     * Define o valor da propriedade numeroLicenca.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroLicenca(String value) {
        this.numeroLicenca = value;
    }

    /**
     * Obtém o valor da propriedade dataEmissao.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define o valor da propriedade dataEmissao.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEmissao(XMLGregorianCalendar value) {
        this.dataEmissao = value;
    }

    /**
     * Obtém o valor da propriedade dataValidade.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataValidade() {
        return dataValidade;
    }

    /**
     * Define o valor da propriedade dataValidade.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataValidade(XMLGregorianCalendar value) {
        this.dataValidade = value;
    }

    /**
     * Obtém o valor da propriedade irregularidadesRestricoes.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInt }
     *     
     */
    public ArrayOfInt getIrregularidadesRestricoes() {
        return irregularidadesRestricoes;
    }

    /**
     * Define o valor da propriedade irregularidadesRestricoes.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInt }
     *     
     */
    public void setIrregularidadesRestricoes(ArrayOfInt value) {
        this.irregularidadesRestricoes = value;
    }

    /**
     * Obtém o valor da propriedade idOrgao.
     * 
     */
    public int getIdOrgao() {
        return idOrgao;
    }

    /**
     * Define o valor da propriedade idOrgao.
     * 
     */
    public void setIdOrgao(int value) {
        this.idOrgao = value;
    }

}
