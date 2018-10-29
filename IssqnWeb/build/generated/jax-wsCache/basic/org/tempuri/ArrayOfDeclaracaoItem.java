
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfDeclaracaoItem complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDeclaracaoItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeclaracaoItem" type="{http://tempuri.org/}DeclaracaoItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDeclaracaoItem", propOrder = {
    "declaracaoItem"
})
public class ArrayOfDeclaracaoItem {

    @XmlElement(name = "DeclaracaoItem", nillable = true)
    protected List<DeclaracaoItem> declaracaoItem;

    /**
     * Gets the value of the declaracaoItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the declaracaoItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeclaracaoItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeclaracaoItem }
     * 
     * 
     */
    public List<DeclaracaoItem> getDeclaracaoItem() {
        if (declaracaoItem == null) {
            declaracaoItem = new ArrayList<DeclaracaoItem>();
        }
        return this.declaracaoItem;
    }

}
