
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfResponsavelEmpresa complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfResponsavelEmpresa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResponsavelEmpresa" type="{http://tempuri.org/}ResponsavelEmpresa" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfResponsavelEmpresa", propOrder = {
    "responsavelEmpresa"
})
public class ArrayOfResponsavelEmpresa {

    @XmlElement(name = "ResponsavelEmpresa", nillable = true)
    protected List<ResponsavelEmpresa> responsavelEmpresa;

    /**
     * Gets the value of the responsavelEmpresa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responsavelEmpresa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponsavelEmpresa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResponsavelEmpresa }
     * 
     * 
     */
    public List<ResponsavelEmpresa> getResponsavelEmpresa() {
        if (responsavelEmpresa == null) {
            responsavelEmpresa = new ArrayList<ResponsavelEmpresa>();
        }
        return this.responsavelEmpresa;
    }

}
