
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfRestricaoViabilidade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRestricaoViabilidade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RestricaoViabilidade" type="{http://tempuri.org/}RestricaoViabilidade" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRestricaoViabilidade", propOrder = {
    "restricaoViabilidade"
})
public class ArrayOfRestricaoViabilidade {

    @XmlElement(name = "RestricaoViabilidade", nillable = true)
    protected List<RestricaoViabilidade> restricaoViabilidade;

    /**
     * Gets the value of the restricaoViabilidade property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the restricaoViabilidade property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRestricaoViabilidade().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RestricaoViabilidade }
     * 
     * 
     */
    public List<RestricaoViabilidade> getRestricaoViabilidade() {
        if (restricaoViabilidade == null) {
            restricaoViabilidade = new ArrayList<RestricaoViabilidade>();
        }
        return this.restricaoViabilidade;
    }

}
