
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfIrregularidade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfIrregularidade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Irregularidade" type="{http://tempuri.org/}Irregularidade" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfIrregularidade", propOrder = {
    "irregularidade"
})
public class ArrayOfIrregularidade {

    @XmlElement(name = "Irregularidade", nillable = true)
    protected List<Irregularidade> irregularidade;

    /**
     * Gets the value of the irregularidade property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the irregularidade property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIrregularidade().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Irregularidade }
     * 
     * 
     */
    public List<Irregularidade> getIrregularidade() {
        if (irregularidade == null) {
            irregularidade = new ArrayList<Irregularidade>();
        }
        return this.irregularidade;
    }

}
