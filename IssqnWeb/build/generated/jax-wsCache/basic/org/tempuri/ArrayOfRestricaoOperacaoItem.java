
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfRestricaoOperacaoItem complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRestricaoOperacaoItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RestricaoOperacaoItem" type="{http://tempuri.org/}RestricaoOperacaoItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRestricaoOperacaoItem", propOrder = {
    "restricaoOperacaoItem"
})
public class ArrayOfRestricaoOperacaoItem {

    @XmlElement(name = "RestricaoOperacaoItem", nillable = true)
    protected List<RestricaoOperacaoItem> restricaoOperacaoItem;

    /**
     * Gets the value of the restricaoOperacaoItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the restricaoOperacaoItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRestricaoOperacaoItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RestricaoOperacaoItem }
     * 
     * 
     */
    public List<RestricaoOperacaoItem> getRestricaoOperacaoItem() {
        if (restricaoOperacaoItem == null) {
            restricaoOperacaoItem = new ArrayList<RestricaoOperacaoItem>();
        }
        return this.restricaoOperacaoItem;
    }

}
