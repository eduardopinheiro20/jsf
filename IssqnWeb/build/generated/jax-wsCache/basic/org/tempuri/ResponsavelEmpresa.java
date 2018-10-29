
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de ResponsavelEmpresa complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ResponsavelEmpresa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdEmpresa" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NomeResponsavel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CPFResponsavel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CPFAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Inclusao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IdSolicitacao" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "ResponsavelEmpresa", propOrder = {
    "idEmpresa",
    "nomeResponsavel",
    "cpfResponsavel",
    "cpfAut",
    "inclusao",
    "idSolicitacao"
})
public class ResponsavelEmpresa {

    @XmlElement(name = "IdEmpresa")
    protected int idEmpresa;
    @XmlElement(name = "NomeResponsavel")
    protected String nomeResponsavel;
    @XmlElement(name = "CPFResponsavel")
    protected String cpfResponsavel;
    @XmlElement(name = "CPFAut")
    protected String cpfAut;
    @XmlElement(name = "Inclusao", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar inclusao;
    @XmlElement(name = "IdSolicitacao")
    protected int idSolicitacao;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade idEmpresa.
     * 
     */
    public int getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * Define o valor da propriedade idEmpresa.
     * 
     */
    public void setIdEmpresa(int value) {
        this.idEmpresa = value;
    }

    /**
     * Obtém o valor da propriedade nomeResponsavel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    /**
     * Define o valor da propriedade nomeResponsavel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeResponsavel(String value) {
        this.nomeResponsavel = value;
    }

    /**
     * Obtém o valor da propriedade cpfResponsavel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPFResponsavel() {
        return cpfResponsavel;
    }

    /**
     * Define o valor da propriedade cpfResponsavel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPFResponsavel(String value) {
        this.cpfResponsavel = value;
    }

    /**
     * Obtém o valor da propriedade cpfAut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPFAut() {
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
    public void setCPFAut(String value) {
        this.cpfAut = value;
    }

    /**
     * Obtém o valor da propriedade inclusao.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInclusao() {
        return inclusao;
    }

    /**
     * Define o valor da propriedade inclusao.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInclusao(XMLGregorianCalendar value) {
        this.inclusao = value;
    }

    /**
     * Obtém o valor da propriedade idSolicitacao.
     * 
     */
    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    /**
     * Define o valor da propriedade idSolicitacao.
     * 
     */
    public void setIdSolicitacao(int value) {
        this.idSolicitacao = value;
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
