
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de OrientacaoAceite complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="OrientacaoAceite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idSolicitacao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idOrientacao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idOrgao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Ciente" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DataInclusao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CPFAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Orientacao" type="{http://tempuri.org/}Orientacao" minOccurs="0"/>
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
@XmlType(name = "OrientacaoAceite", propOrder = {
    "idSolicitacao",
    "idOrientacao",
    "idOrgao",
    "ciente",
    "dataInclusao",
    "cpfAut",
    "orientacao"
})
public class OrientacaoAceite {

    protected int idSolicitacao;
    protected int idOrientacao;
    protected int idOrgao;
    @XmlElement(name = "Ciente")
    protected boolean ciente;
    @XmlElement(name = "DataInclusao", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInclusao;
    @XmlElement(name = "CPFAut")
    protected String cpfAut;
    @XmlElement(name = "Orientacao")
    protected Orientacao orientacao;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

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
     * Obtém o valor da propriedade idOrientacao.
     * 
     */
    public int getIdOrientacao() {
        return idOrientacao;
    }

    /**
     * Define o valor da propriedade idOrientacao.
     * 
     */
    public void setIdOrientacao(int value) {
        this.idOrientacao = value;
    }

    /**
     * Obtém o valor da propriedade idOrgao.
     * 
     */
    public int getIdOrgao() {
        return idOrgao;
    }

    /**
     * Define o valor da propriedade idOrgao.
     * 
     */
    public void setIdOrgao(int value) {
        this.idOrgao = value;
    }

    /**
     * Obtém o valor da propriedade ciente.
     * 
     */
    public boolean isCiente() {
        return ciente;
    }

    /**
     * Define o valor da propriedade ciente.
     * 
     */
    public void setCiente(boolean value) {
        this.ciente = value;
    }

    /**
     * Obtém o valor da propriedade dataInclusao.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInclusao() {
        return dataInclusao;
    }

    /**
     * Define o valor da propriedade dataInclusao.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInclusao(XMLGregorianCalendar value) {
        this.dataInclusao = value;
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
     * Obtém o valor da propriedade orientacao.
     * 
     * @return
     *     possible object is
     *     {@link Orientacao }
     *     
     */
    public Orientacao getOrientacao() {
        return orientacao;
    }

    /**
     * Define o valor da propriedade orientacao.
     * 
     * @param value
     *     allowed object is
     *     {@link Orientacao }
     *     
     */
    public void setOrientacao(Orientacao value) {
        this.orientacao = value;
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
