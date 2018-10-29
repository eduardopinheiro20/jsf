
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfAtividadeCNAE complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAtividadeCNAE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AtividadeCNAE" type="{http://tempuri.org/}AtividadeCNAE" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAtividadeCNAE", propOrder = {
    "atividadeCNAE"
})
public class ArrayOfAtividadeCNAE {

    @XmlElement(name = "AtividadeCNAE", nillable = true)
    protected List<AtividadeCNAE> atividadeCNAE;

    /**
     * Gets the value of the atividadeCNAE property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the atividadeCNAE property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAtividadeCNAE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AtividadeCNAE }
     * 
     * 
     */
    public List<AtividadeCNAE> getAtividadeCNAE() {
        if (atividadeCNAE == null) {
            atividadeCNAE = new ArrayList<AtividadeCNAE>();
        }
        return this.atividadeCNAE;
    }

}
