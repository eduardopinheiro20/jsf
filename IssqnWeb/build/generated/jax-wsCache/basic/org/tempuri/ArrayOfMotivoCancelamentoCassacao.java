
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ArrayOfMotivoCancelamentoCassacao complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMotivoCancelamentoCassacao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MotivoCancelamentoCassacao" type="{http://tempuri.org/}MotivoCancelamentoCassacao" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMotivoCancelamentoCassacao", propOrder = {
    "motivoCancelamentoCassacao"
})
public class ArrayOfMotivoCancelamentoCassacao {

    @XmlElement(name = "MotivoCancelamentoCassacao", nillable = true)
    protected List<MotivoCancelamentoCassacao> motivoCancelamentoCassacao;

    /**
     * Gets the value of the motivoCancelamentoCassacao property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the motivoCancelamentoCassacao property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMotivoCancelamentoCassacao().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MotivoCancelamentoCassacao }
     * 
     * 
     */
    public List<MotivoCancelamentoCassacao> getMotivoCancelamentoCassacao() {
        if (motivoCancelamentoCassacao == null) {
            motivoCancelamentoCassacao = new ArrayList<MotivoCancelamentoCassacao>();
        }
        return this.motivoCancelamentoCassacao;
    }

}
