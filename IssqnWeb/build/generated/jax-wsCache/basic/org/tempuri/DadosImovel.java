
package org.tempuri;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de DadosImovel complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="DadosImovel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoLogradouro" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="AreaImovel" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="AreaEstabelecimento" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SetorQuadraLote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObjetoSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DadosImovel", propOrder = {
    "tipoLogradouro",
    "areaImovel",
    "areaEstabelecimento",
    "setorQuadraLote",
    "objetoSocial"
})
public class DadosImovel {

    @XmlElement(name = "TipoLogradouro")
    protected short tipoLogradouro;
    @XmlElement(name = "AreaImovel", required = true)
    protected BigDecimal areaImovel;
    @XmlElement(name = "AreaEstabelecimento", required = true)
    protected BigDecimal areaEstabelecimento;
    @XmlElement(name = "SetorQuadraLote")
    protected String setorQuadraLote;
    @XmlElement(name = "ObjetoSocial")
    protected String objetoSocial;

    /**
     * Obtém o valor da propriedade tipoLogradouro.
     * 
     */
    public short getTipoLogradouro() {
        return tipoLogradouro;
    }

    /**
     * Define o valor da propriedade tipoLogradouro.
     * 
     */
    public void setTipoLogradouro(short value) {
        this.tipoLogradouro = value;
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
     * Obtém o valor da propriedade setorQuadraLote.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetorQuadraLote() {
        return setorQuadraLote;
    }

    /**
     * Define o valor da propriedade setorQuadraLote.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetorQuadraLote(String value) {
        this.setorQuadraLote = value;
    }

    /**
     * Obtém o valor da propriedade objetoSocial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjetoSocial() {
        return objetoSocial;
    }

    /**
     * Define o valor da propriedade objetoSocial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjetoSocial(String value) {
        this.objetoSocial = value;
    }

}
