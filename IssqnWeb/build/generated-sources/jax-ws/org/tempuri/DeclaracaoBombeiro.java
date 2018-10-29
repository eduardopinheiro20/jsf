
package org.tempuri;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de DeclaracaoBombeiro complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="DeclaracaoBombeiro">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Protocolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AreaEstabelecimento" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ProprietarioNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProprietarioEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProprietarioTelefone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResponsavelNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResponsavelEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResponsavelTelefone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AreaImovel" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Pavimentos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Contiguo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="OutrosUsos" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AVCB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataAVCB" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="NumeroFormulario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "DeclaracaoBombeiro", propOrder = {
    "protocolo",
    "areaEstabelecimento",
    "proprietarioNome",
    "proprietarioEmail",
    "proprietarioTelefone",
    "responsavelNome",
    "responsavelEmail",
    "responsavelTelefone",
    "areaImovel",
    "pavimentos",
    "contiguo",
    "outrosUsos",
    "avcb",
    "dataAVCB",
    "numeroFormulario"
})
public class DeclaracaoBombeiro {

    @XmlElement(name = "Protocolo")
    protected String protocolo;
    @XmlElement(name = "AreaEstabelecimento", required = true)
    protected BigDecimal areaEstabelecimento;
    @XmlElement(name = "ProprietarioNome")
    protected String proprietarioNome;
    @XmlElement(name = "ProprietarioEmail")
    protected String proprietarioEmail;
    @XmlElement(name = "ProprietarioTelefone")
    protected String proprietarioTelefone;
    @XmlElement(name = "ResponsavelNome")
    protected String responsavelNome;
    @XmlElement(name = "ResponsavelEmail")
    protected String responsavelEmail;
    @XmlElement(name = "ResponsavelTelefone")
    protected String responsavelTelefone;
    @XmlElement(name = "AreaImovel", required = true)
    protected BigDecimal areaImovel;
    @XmlElement(name = "Pavimentos")
    protected int pavimentos;
    @XmlElement(name = "Contiguo")
    protected boolean contiguo;
    @XmlElement(name = "OutrosUsos")
    protected boolean outrosUsos;
    @XmlElement(name = "AVCB")
    protected String avcb;
    @XmlElement(name = "DataAVCB", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAVCB;
    @XmlElement(name = "NumeroFormulario")
    protected String numeroFormulario;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade protocolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolo() {
        return protocolo;
    }

    /**
     * Define o valor da propriedade protocolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolo(String value) {
        this.protocolo = value;
    }

    /**
     * Obtém o valor da propriedade areaEstabelecimento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAreaEstabelecimento() {
        return areaEstabelecimento;
    }

    /**
     * Define o valor da propriedade areaEstabelecimento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAreaEstabelecimento(BigDecimal value) {
        this.areaEstabelecimento = value;
    }

    /**
     * Obtém o valor da propriedade proprietarioNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProprietarioNome() {
        return proprietarioNome;
    }

    /**
     * Define o valor da propriedade proprietarioNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProprietarioNome(String value) {
        this.proprietarioNome = value;
    }

    /**
     * Obtém o valor da propriedade proprietarioEmail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProprietarioEmail() {
        return proprietarioEmail;
    }

    /**
     * Define o valor da propriedade proprietarioEmail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProprietarioEmail(String value) {
        this.proprietarioEmail = value;
    }

    /**
     * Obtém o valor da propriedade proprietarioTelefone.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProprietarioTelefone() {
        return proprietarioTelefone;
    }

    /**
     * Define o valor da propriedade proprietarioTelefone.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProprietarioTelefone(String value) {
        this.proprietarioTelefone = value;
    }

    /**
     * Obtém o valor da propriedade responsavelNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsavelNome() {
        return responsavelNome;
    }

    /**
     * Define o valor da propriedade responsavelNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsavelNome(String value) {
        this.responsavelNome = value;
    }

    /**
     * Obtém o valor da propriedade responsavelEmail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsavelEmail() {
        return responsavelEmail;
    }

    /**
     * Define o valor da propriedade responsavelEmail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsavelEmail(String value) {
        this.responsavelEmail = value;
    }

    /**
     * Obtém o valor da propriedade responsavelTelefone.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsavelTelefone() {
        return responsavelTelefone;
    }

    /**
     * Define o valor da propriedade responsavelTelefone.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsavelTelefone(String value) {
        this.responsavelTelefone = value;
    }

    /**
     * Obtém o valor da propriedade areaImovel.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAreaImovel() {
        return areaImovel;
    }

    /**
     * Define o valor da propriedade areaImovel.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAreaImovel(BigDecimal value) {
        this.areaImovel = value;
    }

    /**
     * Obtém o valor da propriedade pavimentos.
     * 
     */
    public int getPavimentos() {
        return pavimentos;
    }

    /**
     * Define o valor da propriedade pavimentos.
     * 
     */
    public void setPavimentos(int value) {
        this.pavimentos = value;
    }

    /**
     * Obtém o valor da propriedade contiguo.
     * 
     */
    public boolean isContiguo() {
        return contiguo;
    }

    /**
     * Define o valor da propriedade contiguo.
     * 
     */
    public void setContiguo(boolean value) {
        this.contiguo = value;
    }

    /**
     * Obtém o valor da propriedade outrosUsos.
     * 
     */
    public boolean isOutrosUsos() {
        return outrosUsos;
    }

    /**
     * Define o valor da propriedade outrosUsos.
     * 
     */
    public void setOutrosUsos(boolean value) {
        this.outrosUsos = value;
    }

    /**
     * Obtém o valor da propriedade avcb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAVCB() {
        return avcb;
    }

    /**
     * Define o valor da propriedade avcb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAVCB(String value) {
        this.avcb = value;
    }

    /**
     * Obtém o valor da propriedade dataAVCB.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAVCB() {
        return dataAVCB;
    }

    /**
     * Define o valor da propriedade dataAVCB.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAVCB(XMLGregorianCalendar value) {
        this.dataAVCB = value;
    }

    /**
     * Obtém o valor da propriedade numeroFormulario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroFormulario() {
        return numeroFormulario;
    }

    /**
     * Define o valor da propriedade numeroFormulario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroFormulario(String value) {
        this.numeroFormulario = value;
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
