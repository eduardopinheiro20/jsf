
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
 *         &lt;element name="Parametros" type="{http://tempuri.org/}ArrayOfInformarResultadoLicenciamentoAltoRiscoParameters" minOccurs="0"/>
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
@XmlRootElement(name = "InformarResultadoLicenciamentoAltoRiscoRequest")
public class InformarResultadoLicenciamentoAltoRiscoRequest {

    @XmlElement(name = "Parametros")
    protected ArrayOfInformarResultadoLicenciamentoAltoRiscoParameters parametros;

    /**
     * Obtém o valor da propriedade parametros.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInformarResultadoLicenciamentoAltoRiscoParameters }
     *     
     */
    public ArrayOfInformarResultadoLicenciamentoAltoRiscoParameters getParametros() {
        return parametros;
    }

    /**
     * Define o valor da propriedade parametros.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInformarResultadoLicenciamentoAltoRiscoParameters }
     *     
     */
    public void setParametros(ArrayOfInformarResultadoLicenciamentoAltoRiscoParameters value) {
        this.parametros = value;
    }

}
