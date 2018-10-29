
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de NaturezaJuridica complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="NaturezaJuridica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NomeNaturezaJuridica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Licenciavel" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CpfAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoNaturezaJuridica" type="{http://tempuri.org/}TipoNaturezaJuridica" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NaturezaJuridica", propOrder = {
    "nomeNaturezaJuridica",
    "codigo",
    "licenciavel",
    "cpfAut",
    "tipoNaturezaJuridica"
})
public class NaturezaJuridica {

    @XmlElement(name = "NomeNaturezaJuridica")
    protected String nomeNaturezaJuridica;
    @XmlElement(name = "Codigo")
    protected String codigo;
    @XmlElement(name = "Licenciavel")
    protected boolean licenciavel;
    @XmlElement(name = "CpfAut")
    protected String cpfAut;
    @XmlElement(name = "TipoNaturezaJuridica")
    protected TipoNaturezaJuridica tipoNaturezaJuridica;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade nomeNaturezaJuridica.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeNaturezaJuridica() {
        return nomeNaturezaJuridica;
    }

    /**
     * Define o valor da propriedade nomeNaturezaJuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeNaturezaJuridica(String value) {
        this.nomeNaturezaJuridica = value;
    }

    /**
     * Obtém o valor da propriedade codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define o valor da propriedade codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtém o valor da propriedade licenciavel.
     * 
     */
    public boolean isLicenciavel() {
        return licenciavel;
    }

    /**
     * Define o valor da propriedade licenciavel.
     * 
     */
    public void setLicenciavel(boolean value) {
        this.licenciavel = value;
    }

    /**
     * Obtém o valor da propriedade cpfAut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpfAut() {
        return cpfAut;
    }

    /**
     * Define o valor da propriedade cpfAut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpfAut(String value) {
        this.cpfAut = value;
    }

    /**
     * Obtém o valor da propriedade tipoNaturezaJuridica.
     * 
     * @return
     *     possible object is
     *     {@link TipoNaturezaJuridica }
     *     
     */
    public TipoNaturezaJuridica getTipoNaturezaJuridica() {
        return tipoNaturezaJuridica;
    }

    /**
     * Define o valor da propriedade tipoNaturezaJuridica.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoNaturezaJuridica }
     *     
     */
    public void setTipoNaturezaJuridica(TipoNaturezaJuridica value) {
        this.tipoNaturezaJuridica = value;
    }

    /**
     * Obtém o valor da propriedade id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}
