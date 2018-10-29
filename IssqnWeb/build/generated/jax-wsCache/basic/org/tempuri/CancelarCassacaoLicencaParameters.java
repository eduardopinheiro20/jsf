
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de CancelarCassacaoLicencaParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="CancelarCassacaoLicencaParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProtocoloSil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotivoCancelamentoCassacao" type="{http://tempuri.org/}ArrayOfDescricaoMotivoCancelamentoCassacao" minOccurs="0"/>
 *         &lt;element name="ProtocoloOrgao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelarCassacaoLicencaParameters", propOrder = {
    "protocoloSil",
    "motivoCancelamentoCassacao",
    "protocoloOrgao"
})
public class CancelarCassacaoLicencaParameters {

    @XmlElement(name = "ProtocoloSil")
    protected String protocoloSil;
    @XmlElement(name = "MotivoCancelamentoCassacao")
    protected ArrayOfDescricaoMotivoCancelamentoCassacao motivoCancelamentoCassacao;
    @XmlElement(name = "ProtocoloOrgao")
    protected String protocoloOrgao;

    /**
     * Obtém o valor da propriedade protocoloSil.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocoloSil() {
        return protocoloSil;
    }

    /**
     * Define o valor da propriedade protocoloSil.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocoloSil(String value) {
        this.protocoloSil = value;
    }

    /**
     * Obtém o valor da propriedade motivoCancelamentoCassacao.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDescricaoMotivoCancelamentoCassacao }
     *     
     */
    public ArrayOfDescricaoMotivoCancelamentoCassacao getMotivoCancelamentoCassacao() {
        return motivoCancelamentoCassacao;
    }

    /**
     * Define o valor da propriedade motivoCancelamentoCassacao.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDescricaoMotivoCancelamentoCassacao }
     *     
     */
    public void setMotivoCancelamentoCassacao(ArrayOfDescricaoMotivoCancelamentoCassacao value) {
        this.motivoCancelamentoCassacao = value;
    }

    /**
     * Obtém o valor da propriedade protocoloOrgao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocoloOrgao() {
        return protocoloOrgao;
    }

    /**
     * Define o valor da propriedade protocoloOrgao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocoloOrgao(String value) {
        this.protocoloOrgao = value;
    }

}
