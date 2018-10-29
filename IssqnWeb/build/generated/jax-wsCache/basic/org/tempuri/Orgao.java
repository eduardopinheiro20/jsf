
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Orgao complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Orgao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Municipio" type="{http://tempuri.org/}Municipio" minOccurs="0"/>
 *         &lt;element name="OrgaoEstadual" type="{http://tempuri.org/}OrgaoEstadual" minOccurs="0"/>
 *         &lt;element name="ValidadeLicencaBaixoRisco" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CpfAlteradoPor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Excluido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "Orgao", propOrder = {
    "nome",
    "municipio",
    "orgaoEstadual",
    "validadeLicencaBaixoRisco",
    "cpfAlteradoPor",
    "excluido"
})
public class Orgao {

    @XmlElement(name = "Nome")
    protected String nome;
    @XmlElement(name = "Municipio")
    protected Municipio municipio;
    @XmlElement(name = "OrgaoEstadual")
    protected OrgaoEstadual orgaoEstadual;
    @XmlElement(name = "ValidadeLicencaBaixoRisco")
    protected int validadeLicencaBaixoRisco;
    @XmlElement(name = "CpfAlteradoPor")
    protected String cpfAlteradoPor;
    @XmlElement(name = "Excluido")
    protected boolean excluido;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o valor da propriedade nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Obtém o valor da propriedade municipio.
     * 
     * @return
     *     possible object is
     *     {@link Municipio }
     *     
     */
    public Municipio getMunicipio() {
        return municipio;
    }

    /**
     * Define o valor da propriedade municipio.
     * 
     * @param value
     *     allowed object is
     *     {@link Municipio }
     *     
     */
    public void setMunicipio(Municipio value) {
        this.municipio = value;
    }

    /**
     * Obtém o valor da propriedade orgaoEstadual.
     * 
     * @return
     *     possible object is
     *     {@link OrgaoEstadual }
     *     
     */
    public OrgaoEstadual getOrgaoEstadual() {
        return orgaoEstadual;
    }

    /**
     * Define o valor da propriedade orgaoEstadual.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgaoEstadual }
     *     
     */
    public void setOrgaoEstadual(OrgaoEstadual value) {
        this.orgaoEstadual = value;
    }

    /**
     * Obtém o valor da propriedade validadeLicencaBaixoRisco.
     * 
     */
    public int getValidadeLicencaBaixoRisco() {
        return validadeLicencaBaixoRisco;
    }

    /**
     * Define o valor da propriedade validadeLicencaBaixoRisco.
     * 
     */
    public void setValidadeLicencaBaixoRisco(int value) {
        this.validadeLicencaBaixoRisco = value;
    }

    /**
     * Obtém o valor da propriedade cpfAlteradoPor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpfAlteradoPor() {
        return cpfAlteradoPor;
    }

    /**
     * Define o valor da propriedade cpfAlteradoPor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpfAlteradoPor(String value) {
        this.cpfAlteradoPor = value;
    }

    /**
     * Obtém o valor da propriedade excluido.
     * 
     */
    public boolean isExcluido() {
        return excluido;
    }

    /**
     * Define o valor da propriedade excluido.
     * 
     */
    public void setExcluido(boolean value) {
        this.excluido = value;
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
