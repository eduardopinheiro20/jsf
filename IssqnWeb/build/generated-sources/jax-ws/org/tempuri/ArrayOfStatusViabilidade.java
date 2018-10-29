
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfStatusViabilidade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfStatusViabilidade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatusViabilidade" type="{http://tempuri.org/}StatusViabilidade" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfStatusViabilidade", propOrder = {
    "statusViabilidade"
})
public class ArrayOfStatusViabilidade {

    @XmlElement(name = "StatusViabilidade", nillable = true)
    protected List<StatusViabilidade> statusViabilidade;

    /**
     * Gets the value of the statusViabilidade property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the statusViabilidade property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatusViabilidade().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StatusViabilidade }
     * 
     * 
     */
    public List<StatusViabilidade> getStatusViabilidade() {
        if (statusViabilidade == null) {
            statusViabilidade = new ArrayList<StatusViabilidade>();
        }
        return this.statusViabilidade;
    }

}
