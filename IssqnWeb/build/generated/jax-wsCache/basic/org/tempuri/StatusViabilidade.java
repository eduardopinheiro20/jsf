
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de StatusViabilidade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="StatusViabilidade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nr_protocolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoAto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sttViabilidade" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="dsViabilidade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusViabilidade", propOrder = {
    "nrProtocolo",
    "tipoAto",
    "sttViabilidade",
    "dsViabilidade"
})
public class StatusViabilidade {

    @XmlElement(name = "nr_protocolo")
    protected String nrProtocolo;
    @XmlElement(name = "TipoAto")
    protected int tipoAto;
    @XmlSchemaType(name = "unsignedByte")
    protected short sttViabilidade;
    protected String dsViabilidade;

    /**
     * Obtém o valor da propriedade nrProtocolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrProtocolo() {
        return nrProtocolo;
    }

    /**
     * Define o valor da propriedade nrProtocolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrProtocolo(String value) {
        this.nrProtocolo = value;
    }

    /**
     * Obtém o valor da propriedade tipoAto.
     * 
     */
    public int getTipoAto() {
        return tipoAto;
    }

    /**
     * Define o valor da propriedade tipoAto.
     * 
     */
    public void setTipoAto(int value) {
        this.tipoAto = value;
    }

    /**
     * Obtém o valor da propriedade sttViabilidade.
     * 
     */
    public short getSttViabilidade() {
        return sttViabilidade;
    }

    /**
     * Define o valor da propriedade sttViabilidade.
     * 
     */
    public void setSttViabilidade(short value) {
        this.sttViabilidade = value;
    }

    /**
     * Obtém o valor da propriedade dsViabilidade.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsViabilidade() {
        return dsViabilidade;
    }

    /**
     * Define o valor da propriedade dsViabilidade.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsViabilidade(String value) {
        this.dsViabilidade = value;
    }

}
