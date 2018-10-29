
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CNAEDivisao complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="CNAEDivisao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idSecao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CNAESecao" type="{http://tempuri.org/}CNAESecao" minOccurs="0"/>
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
@XmlType(name = "CNAEDivisao", propOrder = {
    "idSecao",
    "cnaeSecao",
    "descricao",
    "notasExplicativas"
})
public class CNAEDivisao {

    protected String idSecao;
    @XmlElement(name = "CNAESecao")
    protected CNAESecao cnaeSecao;
    @XmlElement(name = "Descricao")
    protected String descricao;
    @XmlElement(name = "NotasExplicativas")
    protected String notasExplicativas;
    @XmlAttribute(name = "Id")
    protected String id;

    /**
     * Obtém o valor da propriedade idSecao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSecao() {
        return idSecao;
    }

    /**
     * Define o valor da propriedade idSecao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSecao(String value) {
        this.idSecao = value;
    }

    /**
     * Obtém o valor da propriedade cnaeSecao.
     * 
     * @return
     *     possible object is
     *     {@link CNAESecao }
     *     
     */
    public CNAESecao getCNAESecao() {
        return cnaeSecao;
    }

    /**
     * Define o valor da propriedade cnaeSecao.
     * 
     * @param value
     *     allowed object is
     *     {@link CNAESecao }
     *     
     */
    public void setCNAESecao(CNAESecao value) {
        this.cnaeSecao = value;
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
