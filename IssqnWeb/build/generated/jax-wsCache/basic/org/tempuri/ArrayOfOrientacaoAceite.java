
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfOrientacaoAceite complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfOrientacaoAceite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrientacaoAceite" type="{http://tempuri.org/}OrientacaoAceite" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfOrientacaoAceite", propOrder = {
    "orientacaoAceite"
})
public class ArrayOfOrientacaoAceite {

    @XmlElement(name = "OrientacaoAceite", nillable = true)
    protected List<OrientacaoAceite> orientacaoAceite;

    /**
     * Gets the value of the orientacaoAceite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orientacaoAceite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrientacaoAceite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrientacaoAceite }
     * 
     * 
     */
    public List<OrientacaoAceite> getOrientacaoAceite() {
        if (orientacaoAceite == null) {
            orientacaoAceite = new ArrayList<OrientacaoAceite>();
        }
        return this.orientacaoAceite;
    }

}
