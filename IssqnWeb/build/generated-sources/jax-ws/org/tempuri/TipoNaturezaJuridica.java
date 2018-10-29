
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de TipoNaturezaJuridica complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="TipoNaturezaJuridica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NomeNatJuridica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "TipoNaturezaJuridica", propOrder = {
    "nomeNatJuridica"
})
public class TipoNaturezaJuridica {

    @XmlElement(name = "NomeNatJuridica")
    protected String nomeNatJuridica;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade nomeNatJuridica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeNatJuridica() {
        return nomeNatJuridica;
    }

    /**
     * Define o valor da propriedade nomeNatJuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeNatJuridica(String value) {
        this.nomeNatJuridica = value;
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
