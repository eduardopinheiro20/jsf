
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de AtividadeCNAE complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="AtividadeCNAE">
 *   &lt;complexContent>
 *     &lt;extension base="{http://tempuri.org/}Atividade">
 *       &lt;sequence>
 *         &lt;element name="CNAEClasse" type="{http://tempuri.org/}CNAEClasse" minOccurs="0"/>
 *         &lt;element name="IdClasse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Compreende" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TambemCompreende" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NaoCompreende" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NotasExplicativas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtividadeCNAE", propOrder = {
    "cnaeClasse",
    "idClasse",
    "compreende",
    "tambemCompreende",
    "naoCompreende",
    "notasExplicativas"
})
public class AtividadeCNAE
    extends Atividade
{

    @XmlElement(name = "CNAEClasse")
    protected CNAEClasse cnaeClasse;
    @XmlElement(name = "IdClasse")
    protected String idClasse;
    @XmlElement(name = "Compreende")
    protected String compreende;
    @XmlElement(name = "TambemCompreende")
    protected String tambemCompreende;
    @XmlElement(name = "NaoCompreende")
    protected String naoCompreende;
    @XmlElement(name = "NotasExplicativas")
    protected String notasExplicativas;

    /**
     * Obtém o valor da propriedade cnaeClasse.
     * 
     * @return
     *     possible object is
     *     {@link CNAEClasse }
     *     
     */
    public CNAEClasse getCNAEClasse() {
        return cnaeClasse;
    }

    /**
     * Define o valor da propriedade cnaeClasse.
     * 
     * @param value
     *     allowed object is
     *     {@link CNAEClasse }
     *     
     */
    public void setCNAEClasse(CNAEClasse value) {
        this.cnaeClasse = value;
    }

    /**
     * Obtém o valor da propriedade idClasse.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdClasse() {
        return idClasse;
    }

    /**
     * Define o valor da propriedade idClasse.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdClasse(String value) {
        this.idClasse = value;
    }

    /**
     * Obtém o valor da propriedade compreende.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompreende() {
        return compreende;
    }

    /**
     * Define o valor da propriedade compreende.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompreende(String value) {
        this.compreende = value;
    }

    /**
     * Obtém o valor da propriedade tambemCompreende.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTambemCompreende() {
        return tambemCompreende;
    }

    /**
     * Define o valor da propriedade tambemCompreende.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTambemCompreende(String value) {
        this.tambemCompreende = value;
    }

    /**
     * Obtém o valor da propriedade naoCompreende.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaoCompreende() {
        return naoCompreende;
    }

    /**
     * Define o valor da propriedade naoCompreende.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaoCompreende(String value) {
        this.naoCompreende = value;
    }

    /**
     * Obtém o valor da propriedade notasExplicativas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotasExplicativas() {
        return notasExplicativas;
    }

    /**
     * Define o valor da propriedade notasExplicativas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotasExplicativas(String value) {
        this.notasExplicativas = value;
    }

}
