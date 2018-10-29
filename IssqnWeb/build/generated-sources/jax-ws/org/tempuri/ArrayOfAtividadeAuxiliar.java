
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfAtividadeAuxiliar complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAtividadeAuxiliar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AtividadeAuxiliar" type="{http://tempuri.org/}AtividadeAuxiliar" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAtividadeAuxiliar", propOrder = {
    "atividadeAuxiliar"
})
public class ArrayOfAtividadeAuxiliar {

    @XmlElement(name = "AtividadeAuxiliar", nillable = true)
    protected List<AtividadeAuxiliar> atividadeAuxiliar;

    /**
     * Gets the value of the atividadeAuxiliar property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the atividadeAuxiliar property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAtividadeAuxiliar().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AtividadeAuxiliar }
     * 
     * 
     */
    public List<AtividadeAuxiliar> getAtividadeAuxiliar() {
        if (atividadeAuxiliar == null) {
            atividadeAuxiliar = new ArrayList<AtividadeAuxiliar>();
        }
        return this.atividadeAuxiliar;
    }

}
