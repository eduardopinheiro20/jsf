
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfMotivoIndeferimentoMunicipal complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMotivoIndeferimentoMunicipal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MotivoIndeferimentoMunicipal" type="{http://tempuri.org/}MotivoIndeferimentoMunicipal" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMotivoIndeferimentoMunicipal", propOrder = {
    "motivoIndeferimentoMunicipal"
})
public class ArrayOfMotivoIndeferimentoMunicipal {

    @XmlElement(name = "MotivoIndeferimentoMunicipal", nillable = true)
    protected List<MotivoIndeferimentoMunicipal> motivoIndeferimentoMunicipal;

    /**
     * Gets the value of the motivoIndeferimentoMunicipal property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the motivoIndeferimentoMunicipal property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMotivoIndeferimentoMunicipal().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MotivoIndeferimentoMunicipal }
     * 
     * 
     */
    public List<MotivoIndeferimentoMunicipal> getMotivoIndeferimentoMunicipal() {
        if (motivoIndeferimentoMunicipal == null) {
            motivoIndeferimentoMunicipal = new ArrayList<MotivoIndeferimentoMunicipal>();
        }
        return this.motivoIndeferimentoMunicipal;
    }

}
