
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfOrientacaoItem complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrientacaoItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrientacaoItem" type="{http://tempuri.org/}OrientacaoItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrientacaoItem", propOrder = {
    "orientacaoItem"
})
public class ArrayOfOrientacaoItem {

    @XmlElement(name = "OrientacaoItem", nillable = true)
    protected List<OrientacaoItem> orientacaoItem;

    /**
     * Gets the value of the orientacaoItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orientacaoItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrientacaoItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrientacaoItem }
     * 
     * 
     */
    public List<OrientacaoItem> getOrientacaoItem() {
        if (orientacaoItem == null) {
            orientacaoItem = new ArrayList<OrientacaoItem>();
        }
        return this.orientacaoItem;
    }

}
