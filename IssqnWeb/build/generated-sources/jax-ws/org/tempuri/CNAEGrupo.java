
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CNAEGrupo complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="CNAEGrupo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdDivisao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CNAEDivisao" type="{http://tempuri.org/}CNAEDivisao" minOccurs="0"/>
 *         &lt;element name="Descricao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NotasExplicativas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CNAEGrupo", propOrder = {
    "idDivisao",
    "cnaeDivisao",
    "descricao",
    "notasExplicativas"
})
public class CNAEGrupo {

    @XmlElement(name = "IdDivisao")
    protected String idDivisao;
    @XmlElement(name = "CNAEDivisao")
    protected CNAEDivisao cnaeDivisao;
    @XmlElement(name = "Descricao")
    protected String descricao;
    @XmlElement(name = "NotasExplicativas")
    protected String notasExplicativas;
    @XmlAttribute(name = "Id")
    protected String id;

    /**
     * Obtém o valor da propriedade idDivisao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDivisao() {
        return idDivisao;
    }

    /**
     * Define o valor da propriedade idDivisao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDivisao(String value) {
        this.idDivisao = value;
    }

    /**
     * Obtém o valor da propriedade cnaeDivisao.
     * 
     * @return
     *     possible object is
     *     {@link CNAEDivisao }
     *     
     */
    public CNAEDivisao getCNAEDivisao() {
        return cnaeDivisao;
    }

    /**
     * Define o valor da propriedade cnaeDivisao.
     * 
     * @param value
     *     allowed object is
     *     {@link CNAEDivisao }
     *     
     */
    public void setCNAEDivisao(CNAEDivisao value) {
        this.cnaeDivisao = value;
    }

    /**
     * Obtém o valor da propriedade descricao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define o valor da propriedade descricao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricao(String value) {
        this.descricao = value;
    }

    /**
     * Obtém o valor da propriedade notasExplicativas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotasExplicativas() {
        return notasExplicativas;
    }

    /**
     * Define o valor da propriedade notasExplicativas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotasExplicativas(String value) {
        this.notasExplicativas = value;
    }

    /**
     * Obtém o valor da propriedade id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
