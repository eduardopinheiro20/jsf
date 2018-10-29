
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfLiberarLicencaCassadaParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfLiberarLicencaCassadaParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LiberarLicencaCassadaParameters" type="{http://tempuri.org/}LiberarLicencaCassadaParameters" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfLiberarLicencaCassadaParameters", propOrder = {
    "liberarLicencaCassadaParameters"
})
public class ArrayOfLiberarLicencaCassadaParameters {

    @XmlElement(name = "LiberarLicencaCassadaParameters", nillable = true)
    protected List<LiberarLicencaCassadaParameters> liberarLicencaCassadaParameters;

    /**
     * Gets the value of the liberarLicencaCassadaParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the liberarLicencaCassadaParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLiberarLicencaCassadaParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LiberarLicencaCassadaParameters }
     * 
     * 
     */
    public List<LiberarLicencaCassadaParameters> getLiberarLicencaCassadaParameters() {
        if (liberarLicencaCassadaParameters == null) {
            liberarLicencaCassadaParameters = new ArrayList<LiberarLicencaCassadaParameters>();
        }
        return this.liberarLicencaCassadaParameters;
    }

}
