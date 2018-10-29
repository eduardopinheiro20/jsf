
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfAuxiliar complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAuxiliar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Auxiliar" type="{http://tempuri.org/}Auxiliar" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAuxiliar", propOrder = {
    "auxiliar"
})
public class ArrayOfAuxiliar {

    @XmlElement(name = "Auxiliar", nillable = true)
    protected List<Auxiliar> auxiliar;

    /**
     * Gets the value of the auxiliar property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the auxiliar property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuxiliar().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Auxiliar }
     * 
     * 
     */
    public List<Auxiliar> getAuxiliar() {
        if (auxiliar == null) {
            auxiliar = new ArrayList<Auxiliar>();
        }
        return this.auxiliar;
    }

}
