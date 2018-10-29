
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfRestricaoOperacao complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRestricaoOperacao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RestricaoOperacao" type="{http://tempuri.org/}RestricaoOperacao" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRestricaoOperacao", propOrder = {
    "restricaoOperacao"
})
public class ArrayOfRestricaoOperacao {

    @XmlElement(name = "RestricaoOperacao", nillable = true)
    protected List<RestricaoOperacao> restricaoOperacao;

    /**
     * Gets the value of the restricaoOperacao property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the restricaoOperacao property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRestricaoOperacao().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RestricaoOperacao }
     * 
     * 
     */
    public List<RestricaoOperacao> getRestricaoOperacao() {
        if (restricaoOperacao == null) {
            restricaoOperacao = new ArrayList<RestricaoOperacao>();
        }
        return this.restricaoOperacao;
    }

}
