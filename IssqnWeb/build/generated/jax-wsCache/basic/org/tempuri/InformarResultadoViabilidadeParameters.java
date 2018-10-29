
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de InformarResultadoViabilidadeParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="InformarResultadoViabilidadeParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Protocolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="motivosRestricoes" type="{http://tempuri.org/}ArrayOfInt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformarResultadoViabilidadeParameters", propOrder = {
    "protocolo",
    "resultado",
    "motivosRestricoes"
})
public class InformarResultadoViabilidadeParameters {

    @XmlElement(name = "Protocolo")
    protected String protocolo;
    @XmlElement(name = "Resultado")
    protected int resultado;
    protected ArrayOfInt motivosRestricoes;

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
     * Obtém o valor da propriedade motivosRestricoes.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInt }
     *     
     */
    public ArrayOfInt getMotivosRestricoes() {
        return motivosRestricoes;
    }

    /**
     * Define o valor da propriedade motivosRestricoes.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInt }
     *     
     */
    public void setMotivosRestricoes(ArrayOfInt value) {
        this.motivosRestricoes = value;
    }

}
