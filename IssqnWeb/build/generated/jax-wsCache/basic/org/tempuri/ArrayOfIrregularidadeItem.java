
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfIrregularidadeItem complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfIrregularidadeItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IrregularidadeItem" type="{http://tempuri.org/}IrregularidadeItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfIrregularidadeItem", propOrder = {
    "irregularidadeItem"
})
public class ArrayOfIrregularidadeItem {

    @XmlElement(name = "IrregularidadeItem", nillable = true)
    protected List<IrregularidadeItem> irregularidadeItem;

    /**
     * Gets the value of the irregularidadeItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the irregularidadeItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIrregularidadeItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IrregularidadeItem }
     * 
     * 
     */
    public List<IrregularidadeItem> getIrregularidadeItem() {
        if (irregularidadeItem == null) {
            irregularidadeItem = new ArrayList<IrregularidadeItem>();
        }
        return this.irregularidadeItem;
    }

}
