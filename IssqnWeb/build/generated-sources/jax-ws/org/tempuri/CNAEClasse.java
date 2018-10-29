
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CNAEClasse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="CNAEClasse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CNAEGrupo" type="{http://tempuri.org/}CNAEGrupo" minOccurs="0"/>
 *         &lt;element name="IdGrupo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Descricao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Compreende" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TambemCompreende" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NaoCompreende" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NotasExplicativas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CNAEClasse", propOrder = {
    "cnaeGrupo",
    "idGrupo",
    "descricao",
    "compreende",
    "tambemCompreende",
    "naoCompreende",
    "notasExplicativas"
})
public class CNAEClasse {

    @XmlElement(name = "CNAEGrupo")
    protected CNAEGrupo cnaeGrupo;
    @XmlElement(name = "IdGrupo")
    protected String idGrupo;
    @XmlElement(name = "Descricao")
    protected String descricao;
    @XmlElement(name = "Compreende")
    protected String compreende;
    @XmlElement(name = "TambemCompreende")
    protected String tambemCompreende;
    @XmlElement(name = "NaoCompreende")
    protected String naoCompreende;
    @XmlElement(name = "NotasExplicativas")
    protected String notasExplicativas;
    @XmlAttribute(name = "Id")
    protected String id;

    /**
     * Obtém o valor da propriedade cnaeGrupo.
     * 
     * @return
     *     possible object is
     *     {@link CNAEGrupo }
     *     
     */
    public CNAEGrupo getCNAEGrupo() {
        return cnaeGrupo;
    }

    /**
     * Define o valor da propriedade cnaeGrupo.
     * 
     * @param value
     *     allowed object is
     *     {@link CNAEGrupo }
     *     
     */
    public void setCNAEGrupo(CNAEGrupo value) {
        this.cnaeGrupo = value;
    }

    /**
     * Obtém o valor da propriedade idGrupo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdGrupo() {
        return idGrupo;
    }

    /**
     * Define o valor da propriedade idGrupo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdGrupo(String value) {
        this.idGrupo = value;
    }

    /**
     * Obtém o valor da propriedade descricao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define o valor da propriedade descricao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricao(String value) {
        this.descricao = value;
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

    /**
     * Obtém o valor da propriedade id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
