
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="protocolo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nire" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cnpj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "protocolo",
    "nire",
    "cnpj"
})
@XmlRootElement(name = "ConsultarViabilidadePreviaRequest")
public class ConsultarViabilidadePreviaRequest {

    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long protocolo;
    protected String nire;
    protected String cnpj;

    /**
     * Obtém o valor da propriedade protocolo.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProtocolo() {
        return protocolo;
    }

    /**
     * Define o valor da propriedade protocolo.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProtocolo(Long value) {
        this.protocolo = value;
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
     * Obtém o valor da propriedade cnpj.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Define o valor da propriedade cnpj.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnpj(String value) {
        this.cnpj = value;
    }

}
