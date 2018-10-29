
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfItemTransacao complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfItemTransacao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemTransacao" type="{http://tempuri.org/}ItemTransacao" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfItemTransacao", propOrder = {
    "itemTransacao"
})
public class ArrayOfItemTransacao {

    @XmlElement(name = "ItemTransacao", nillable = true)
    protected List<ItemTransacao> itemTransacao;

    /**
     * Gets the value of the itemTransacao property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemTransacao property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemTransacao().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemTransacao }
     * 
     * 
     */
    public List<ItemTransacao> getItemTransacao() {
        if (itemTransacao == null) {
            itemTransacao = new ArrayList<ItemTransacao>();
        }
        return this.itemTransacao;
    }

}
