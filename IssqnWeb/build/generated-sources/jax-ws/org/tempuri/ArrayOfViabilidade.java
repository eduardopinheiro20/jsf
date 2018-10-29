
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfViabilidade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfViabilidade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Viabilidade" type="{http://tempuri.org/}Viabilidade" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfViabilidade", propOrder = {
    "viabilidade"
})
public class ArrayOfViabilidade {

    @XmlElement(name = "Viabilidade", nillable = true)
    protected List<Viabilidade> viabilidade;

    /**
     * Gets the value of the viabilidade property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the viabilidade property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getViabilidade().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Viabilidade }
     * 
     * 
     */
    public List<Viabilidade> getViabilidade() {
        if (viabilidade == null) {
            viabilidade = new ArrayList<Viabilidade>();
        }
        return this.viabilidade;
    }

}
