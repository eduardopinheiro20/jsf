
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de OrgaoEstadual complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="OrgaoEstadual">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdOrgaoEstadual" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrgaoIntegradoPoupatempo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgaoEstadual", propOrder = {
    "idOrgaoEstadual",
    "nome",
    "orgaoIntegradoPoupatempo"
})
public class OrgaoEstadual {

    @XmlElement(name = "IdOrgaoEstadual")
    protected int idOrgaoEstadual;
    @XmlElement(name = "Nome")
    protected String nome;
    @XmlElement(name = "OrgaoIntegradoPoupatempo")
    protected boolean orgaoIntegradoPoupatempo;

    /**
     * Obtém o valor da propriedade idOrgaoEstadual.
     * 
     */
    public int getIdOrgaoEstadual() {
        return idOrgaoEstadual;
    }

    /**
     * Define o valor da propriedade idOrgaoEstadual.
     * 
     */
    public void setIdOrgaoEstadual(int value) {
        this.idOrgaoEstadual = value;
    }

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
     * Obtém o valor da propriedade orgaoIntegradoPoupatempo.
     * 
     */
    public boolean isOrgaoIntegradoPoupatempo() {
        return orgaoIntegradoPoupatempo;
    }

    /**
     * Define o valor da propriedade orgaoIntegradoPoupatempo.
     * 
     */
    public void setOrgaoIntegradoPoupatempo(boolean value) {
        this.orgaoIntegradoPoupatempo = value;
    }

}
