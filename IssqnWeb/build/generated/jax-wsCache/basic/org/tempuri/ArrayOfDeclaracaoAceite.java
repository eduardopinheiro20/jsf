
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfDeclaracaoAceite complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDeclaracaoAceite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeclaracaoAceite" type="{http://tempuri.org/}DeclaracaoAceite" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDeclaracaoAceite", propOrder = {
    "declaracaoAceite"
})
public class ArrayOfDeclaracaoAceite {

    @XmlElement(name = "DeclaracaoAceite", nillable = true)
    protected List<DeclaracaoAceite> declaracaoAceite;

    /**
     * Gets the value of the declaracaoAceite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the declaracaoAceite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeclaracaoAceite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeclaracaoAceite }
     * 
     * 
     */
    public List<DeclaracaoAceite> getDeclaracaoAceite() {
        if (declaracaoAceite == null) {
            declaracaoAceite = new ArrayList<DeclaracaoAceite>();
        }
        return this.declaracaoAceite;
    }

}
