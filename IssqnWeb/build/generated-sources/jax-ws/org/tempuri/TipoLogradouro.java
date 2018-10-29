
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de TipoLogradouro complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="TipoLogradouro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NomeLogradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "TipoLogradouro", propOrder = {
    "nomeLogradouro"
})
public class TipoLogradouro {

    @XmlElement(name = "NomeLogradouro")
    protected String nomeLogradouro;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade nomeLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeLogradouro() {
        return nomeLogradouro;
    }

    /**
     * Define o valor da propriedade nomeLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeLogradouro(String value) {
        this.nomeLogradouro = value;
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
