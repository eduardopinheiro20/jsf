
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de InformarInicioLicenciamentoAltoRiscoParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="InformarInicioLicenciamentoAltoRiscoParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Protocolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InicioAtendimento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IdOrgao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProtocoloAtendimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InformarInicioLicenciamentoAltoRiscoParameters", propOrder = {
    "protocolo",
    "inicioAtendimento",
    "idOrgao",
    "protocoloAtendimento"
})
public class InformarInicioLicenciamentoAltoRiscoParameters {

    @XmlElement(name = "Protocolo")
    protected String protocolo;
    @XmlElement(name = "InicioAtendimento", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar inicioAtendimento;
    @XmlElement(name = "IdOrgao")
    protected int idOrgao;
    @XmlElement(name = "ProtocoloAtendimento")
    protected String protocoloAtendimento;

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
     * Obtém o valor da propriedade inicioAtendimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInicioAtendimento() {
        return inicioAtendimento;
    }

    /**
     * Define o valor da propriedade inicioAtendimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInicioAtendimento(XMLGregorianCalendar value) {
        this.inicioAtendimento = value;
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
     * Obtém o valor da propriedade protocoloAtendimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocoloAtendimento() {
        return protocoloAtendimento;
    }

    /**
     * Define o valor da propriedade protocoloAtendimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocoloAtendimento(String value) {
        this.protocoloAtendimento = value;
    }

}
