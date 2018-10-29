
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Auxiliar complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Auxiliar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="Texto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Atividade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Auxiliar", propOrder = {
    "id",
    "texto",
    "atividade"
})
public class Auxiliar {

    @XmlElement(name = "Id")
    @XmlSchemaType(name = "unsignedByte")
    protected short id;
    @XmlElement(name = "Texto")
    protected String texto;
    @XmlElement(name = "Atividade")
    protected String atividade;

    /**
     * Obtém o valor da propriedade id.
     * 
     */
    public short getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     */
    public void setId(short value) {
        this.id = value;
    }

    /**
     * Obtém o valor da propriedade texto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Define o valor da propriedade texto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTexto(String value) {
        this.texto = value;
    }

    /**
     * Obtém o valor da propriedade atividade.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAtividade() {
        return atividade;
    }

    /**
     * Define o valor da propriedade atividade.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAtividade(String value) {
        this.atividade = value;
    }

}
