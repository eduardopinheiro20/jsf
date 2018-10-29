
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
 *         &lt;element name="Parametros" type="{http://tempuri.org/}InformarResultadoLicenciamentoParameters" minOccurs="0"/>
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
    "parametros"
})
@XmlRootElement(name = "InformarResultadoLicenciamentoRequest")
public class InformarResultadoLicenciamentoRequest {

    @XmlElement(name = "Parametros")
    protected InformarResultadoLicenciamentoParameters parametros;

    /**
     * Obtém o valor da propriedade parametros.
     * 
     * @return
     *     possible object is
     *     {@link InformarResultadoLicenciamentoParameters }
     *     
     */
    public InformarResultadoLicenciamentoParameters getParametros() {
        return parametros;
    }

    /**
     * Define o valor da propriedade parametros.
     * 
     * @param value
     *     allowed object is
     *     {@link InformarResultadoLicenciamentoParameters }
     *     
     */
    public void setParametros(InformarResultadoLicenciamentoParameters value) {
        this.parametros = value;
    }

}