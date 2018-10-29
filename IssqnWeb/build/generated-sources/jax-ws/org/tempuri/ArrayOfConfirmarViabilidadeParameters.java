
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfConfirmarViabilidadeParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConfirmarViabilidadeParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConfirmarViabilidadeParameters" type="{http://tempuri.org/}ConfirmarViabilidadeParameters" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConfirmarViabilidadeParameters", propOrder = {
    "confirmarViabilidadeParameters"
})
public class ArrayOfConfirmarViabilidadeParameters {

    @XmlElement(name = "ConfirmarViabilidadeParameters", nillable = true)
    protected List<ConfirmarViabilidadeParameters> confirmarViabilidadeParameters;

    /**
     * Gets the value of the confirmarViabilidadeParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confirmarViabilidadeParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfirmarViabilidadeParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConfirmarViabilidadeParameters }
     * 
     * 
     */
    public List<ConfirmarViabilidadeParameters> getConfirmarViabilidadeParameters() {
        if (confirmarViabilidadeParameters == null) {
            confirmarViabilidadeParameters = new ArrayList<ConfirmarViabilidadeParameters>();
        }
        return this.confirmarViabilidadeParameters;
    }

}
