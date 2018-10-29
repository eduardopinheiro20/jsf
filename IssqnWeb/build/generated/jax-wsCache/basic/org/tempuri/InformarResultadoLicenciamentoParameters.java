
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de InformarResultadoLicenciamentoParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="InformarResultadoLicenciamentoParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Protocolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProtocoloOrgao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumeroLicenca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataEmissao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DataValidade" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IrregularidadeLista" type="{http://tempuri.org/}ArrayOfIrregularidadeItem" minOccurs="0"/>
 *         &lt;element name="RestricaoOperacaoLista" type="{http://tempuri.org/}ArrayOfRestricaoOperacaoItem" minOccurs="0"/>
 *         &lt;element name="DeclaracaoLista" type="{http://tempuri.org/}ArrayOfDeclaracaoItem" minOccurs="0"/>
 *         &lt;element name="OrientacaoLista" type="{http://tempuri.org/}ArrayOfOrientacaoItem" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformarResultadoLicenciamentoParameters", propOrder = {
    "protocolo",
    "protocoloOrgao",
    "resultado",
    "numeroLicenca",
    "dataEmissao",
    "dataValidade",
    "irregularidadeLista",
    "restricaoOperacaoLista",
    "declaracaoLista",
    "orientacaoLista"
})
public class InformarResultadoLicenciamentoParameters {

    @XmlElement(name = "Protocolo")
    protected String protocolo;
    @XmlElement(name = "ProtocoloOrgao")
    protected String protocoloOrgao;
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
    @XmlElement(name = "IrregularidadeLista")
    protected ArrayOfIrregularidadeItem irregularidadeLista;
    @XmlElement(name = "RestricaoOperacaoLista")
    protected ArrayOfRestricaoOperacaoItem restricaoOperacaoLista;
    @XmlElement(name = "DeclaracaoLista")
    protected ArrayOfDeclaracaoItem declaracaoLista;
    @XmlElement(name = "OrientacaoLista")
    protected ArrayOfOrientacaoItem orientacaoLista;

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
     * Obtém o valor da propriedade protocoloOrgao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocoloOrgao() {
        return protocoloOrgao;
    }

    /**
     * Define o valor da propriedade protocoloOrgao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocoloOrgao(String value) {
        this.protocoloOrgao = value;
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
     * Obtém o valor da propriedade irregularidadeLista.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfIrregularidadeItem }
     *     
     */
    public ArrayOfIrregularidadeItem getIrregularidadeLista() {
        return irregularidadeLista;
    }

    /**
     * Define o valor da propriedade irregularidadeLista.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfIrregularidadeItem }
     *     
     */
    public void setIrregularidadeLista(ArrayOfIrregularidadeItem value) {
        this.irregularidadeLista = value;
    }

    /**
     * Obtém o valor da propriedade restricaoOperacaoLista.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRestricaoOperacaoItem }
     *     
     */
    public ArrayOfRestricaoOperacaoItem getRestricaoOperacaoLista() {
        return restricaoOperacaoLista;
    }

    /**
     * Define o valor da propriedade restricaoOperacaoLista.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRestricaoOperacaoItem }
     *     
     */
    public void setRestricaoOperacaoLista(ArrayOfRestricaoOperacaoItem value) {
        this.restricaoOperacaoLista = value;
    }

    /**
     * Obtém o valor da propriedade declaracaoLista.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDeclaracaoItem }
     *     
     */
    public ArrayOfDeclaracaoItem getDeclaracaoLista() {
        return declaracaoLista;
    }

    /**
     * Define o valor da propriedade declaracaoLista.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDeclaracaoItem }
     *     
     */
    public void setDeclaracaoLista(ArrayOfDeclaracaoItem value) {
        this.declaracaoLista = value;
    }

    /**
     * Obtém o valor da propriedade orientacaoLista.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOrientacaoItem }
     *     
     */
    public ArrayOfOrientacaoItem getOrientacaoLista() {
        return orientacaoLista;
    }

    /**
     * Define o valor da propriedade orientacaoLista.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOrientacaoItem }
     *     
     */
    public void setOrientacaoLista(ArrayOfOrientacaoItem value) {
        this.orientacaoLista = value;
    }

}
