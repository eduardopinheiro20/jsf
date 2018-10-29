
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfInformarResultadoViabilidadeParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfInformarResultadoViabilidadeParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InformarResultadoViabilidadeParameters" type="{http://tempuri.org/}InformarResultadoViabilidadeParameters" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInformarResultadoViabilidadeParameters", propOrder = {
    "informarResultadoViabilidadeParameters"
})
public class ArrayOfInformarResultadoViabilidadeParameters {

    @XmlElement(name = "InformarResultadoViabilidadeParameters", nillable = true)
    protected List<InformarResultadoViabilidadeParameters> informarResultadoViabilidadeParameters;

    /**
     * Gets the value of the informarResultadoViabilidadeParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the informarResultadoViabilidadeParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInformarResultadoViabilidadeParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InformarResultadoViabilidadeParameters }
     * 
     * 
     */
    public List<InformarResultadoViabilidadeParameters> getInformarResultadoViabilidadeParameters() {
        if (informarResultadoViabilidadeParameters == null) {
            informarResultadoViabilidadeParameters = new ArrayList<InformarResultadoViabilidadeParameters>();
        }
        return this.informarResultadoViabilidadeParameters;
    }

}
