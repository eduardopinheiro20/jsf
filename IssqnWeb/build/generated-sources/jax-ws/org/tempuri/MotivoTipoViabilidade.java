
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de MotivoTipoViabilidade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="MotivoTipoViabilidade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idMotivo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dsTipoMotivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dsTextoMotivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MotivoTipoViabilidade", propOrder = {
    "idMotivo",
    "dsTipoMotivo",
    "dsTextoMotivo"
})
public class MotivoTipoViabilidade {

    protected int idMotivo;
    protected String dsTipoMotivo;
    protected String dsTextoMotivo;

    /**
     * Obtém o valor da propriedade idMotivo.
     * 
     */
    public int getIdMotivo() {
        return idMotivo;
    }

    /**
     * Define o valor da propriedade idMotivo.
     * 
     */
    public void setIdMotivo(int value) {
        this.idMotivo = value;
    }

    /**
     * Obtém o valor da propriedade dsTipoMotivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsTipoMotivo() {
        return dsTipoMotivo;
    }

    /**
     * Define o valor da propriedade dsTipoMotivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsTipoMotivo(String value) {
        this.dsTipoMotivo = value;
    }

    /**
     * Obtém o valor da propriedade dsTextoMotivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsTextoMotivo() {
        return dsTextoMotivo;
    }

    /**
     * Define o valor da propriedade dsTextoMotivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsTextoMotivo(String value) {
        this.dsTextoMotivo = value;
    }

}
