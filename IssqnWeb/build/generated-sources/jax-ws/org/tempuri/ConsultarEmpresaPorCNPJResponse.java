
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
 *         &lt;element name="Empresa" type="{http://tempuri.org/}Empresa" minOccurs="0"/>
 *         &lt;element name="ResultCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ResultDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "empresa",
    "resultCode",
    "resultDescription"
})
@XmlRootElement(name = "ConsultarEmpresaPorCNPJResponse")
public class ConsultarEmpresaPorCNPJResponse {

    @XmlElement(name = "Empresa")
    protected Empresa empresa;
    @XmlElement(name = "ResultCode")
    protected int resultCode;
    @XmlElement(name = "ResultDescription")
    protected String resultDescription;

    /**
     * Obtém o valor da propriedade empresa.
     * 
     * @return
     *     possible object is
     *     {@link Empresa }
     *     
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Define o valor da propriedade empresa.
     * 
     * @param value
     *     allowed object is
     *     {@link Empresa }
     *     
     */
    public void setEmpresa(Empresa value) {
        this.empresa = value;
    }

    /**
     * Obtém o valor da propriedade resultCode.
     * 
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Define o valor da propriedade resultCode.
     * 
     */
    public void setResultCode(int value) {
        this.resultCode = value;
    }

    /**
     * Obtém o valor da propriedade resultDescription.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultDescription() {
        return resultDescription;
    }

    /**
     * Define o valor da propriedade resultDescription.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultDescription(String value) {
        this.resultDescription = value;
    }

}
