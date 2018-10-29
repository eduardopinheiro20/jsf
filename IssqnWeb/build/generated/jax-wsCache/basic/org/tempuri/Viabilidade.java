
package org.tempuri;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de Viabilidade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Viabilidade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="protcolo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cdAto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cnpj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nire" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dtSolicitacaoViabilidade" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dtResultadoViabilidae" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ds_tipoAto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cdStatus" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dsStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cep" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dsTipoImovel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="setorQuadraLote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dsLogradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dsComplemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dsReferencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dsBairro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dsMunicipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="areaEstabelecimento" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="areaImovel" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="dsObjetoSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dadosCnae" type="{http://tempuri.org/}ArrayOfCnae" minOccurs="0"/>
 *         &lt;element name="dadosAtividadeAuxiliar" type="{http://tempuri.org/}ArrayOfAuxiliar" minOccurs="0"/>
 *         &lt;element name="dadosResposta" type="{http://tempuri.org/}ArrayOfRespostaInfo" minOccurs="0"/>
 *         &lt;element name="dadosMotivo" type="{http://tempuri.org/}ArrayOfMotivoTipoViabilidade" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Viabilidade", propOrder = {
    "protcolo",
    "cdAto",
    "cnpj",
    "nire",
    "dtSolicitacaoViabilidade",
    "dtResultadoViabilidae",
    "dsTipoAto",
    "cdStatus",
    "dsStatus",
    "cep",
    "dsTipoImovel",
    "setorQuadraLote",
    "dsLogradouro",
    "numero",
    "dsComplemento",
    "dsReferencia",
    "dsBairro",
    "dsMunicipio",
    "areaEstabelecimento",
    "areaImovel",
    "dsObjetoSocial",
    "dadosCnae",
    "dadosAtividadeAuxiliar",
    "dadosResposta",
    "dadosMotivo"
})
public class Viabilidade {

    protected long protcolo;
    protected int cdAto;
    protected String cnpj;
    protected String nire;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtSolicitacaoViabilidade;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtResultadoViabilidae;
    @XmlElement(name = "ds_tipoAto")
    protected String dsTipoAto;
    protected int cdStatus;
    protected String dsStatus;
    protected String cep;
    protected String dsTipoImovel;
    protected String setorQuadraLote;
    protected String dsLogradouro;
    protected String numero;
    protected String dsComplemento;
    protected String dsReferencia;
    protected String dsBairro;
    protected String dsMunicipio;
    @XmlElement(required = true)
    protected BigDecimal areaEstabelecimento;
    @XmlElement(required = true)
    protected BigDecimal areaImovel;
    protected String dsObjetoSocial;
    protected ArrayOfCnae dadosCnae;
    protected ArrayOfAuxiliar dadosAtividadeAuxiliar;
    protected ArrayOfRespostaInfo dadosResposta;
    protected ArrayOfMotivoTipoViabilidade dadosMotivo;

    /**
     * Obtém o valor da propriedade protcolo.
     * 
     */
    public long getProtcolo() {
        return protcolo;
    }

    /**
     * Define o valor da propriedade protcolo.
     * 
     */
    public void setProtcolo(long value) {
        this.protcolo = value;
    }

    /**
     * Obtém o valor da propriedade cdAto.
     * 
     */
    public int getCdAto() {
        return cdAto;
    }

    /**
     * Define o valor da propriedade cdAto.
     * 
     */
    public void setCdAto(int value) {
        this.cdAto = value;
    }

    /**
     * Obtém o valor da propriedade cnpj.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Define o valor da propriedade cnpj.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnpj(String value) {
        this.cnpj = value;
    }

    /**
     * Obtém o valor da propriedade nire.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNire() {
        return nire;
    }

    /**
     * Define o valor da propriedade nire.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNire(String value) {
        this.nire = value;
    }

    /**
     * Obtém o valor da propriedade dtSolicitacaoViabilidade.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtSolicitacaoViabilidade() {
        return dtSolicitacaoViabilidade;
    }

    /**
     * Define o valor da propriedade dtSolicitacaoViabilidade.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtSolicitacaoViabilidade(XMLGregorianCalendar value) {
        this.dtSolicitacaoViabilidade = value;
    }

    /**
     * Obtém o valor da propriedade dtResultadoViabilidae.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtResultadoViabilidae() {
        return dtResultadoViabilidae;
    }

    /**
     * Define o valor da propriedade dtResultadoViabilidae.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDtResultadoViabilidae(XMLGregorianCalendar value) {
        this.dtResultadoViabilidae = value;
    }

    /**
     * Obtém o valor da propriedade dsTipoAto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsTipoAto() {
        return dsTipoAto;
    }

    /**
     * Define o valor da propriedade dsTipoAto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsTipoAto(String value) {
        this.dsTipoAto = value;
    }

    /**
     * Obtém o valor da propriedade cdStatus.
     * 
     */
    public int getCdStatus() {
        return cdStatus;
    }

    /**
     * Define o valor da propriedade cdStatus.
     * 
     */
    public void setCdStatus(int value) {
        this.cdStatus = value;
    }

    /**
     * Obtém o valor da propriedade dsStatus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsStatus() {
        return dsStatus;
    }

    /**
     * Define o valor da propriedade dsStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsStatus(String value) {
        this.dsStatus = value;
    }

    /**
     * Obtém o valor da propriedade cep.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCep() {
        return cep;
    }

    /**
     * Define o valor da propriedade cep.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCep(String value) {
        this.cep = value;
    }

    /**
     * Obtém o valor da propriedade dsTipoImovel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsTipoImovel() {
        return dsTipoImovel;
    }

    /**
     * Define o valor da propriedade dsTipoImovel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsTipoImovel(String value) {
        this.dsTipoImovel = value;
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
     * Obtém o valor da propriedade dsLogradouro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsLogradouro() {
        return dsLogradouro;
    }

    /**
     * Define o valor da propriedade dsLogradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsLogradouro(String value) {
        this.dsLogradouro = value;
    }

    /**
     * Obtém o valor da propriedade numero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Define o valor da propriedade numero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Obtém o valor da propriedade dsComplemento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsComplemento() {
        return dsComplemento;
    }

    /**
     * Define o valor da propriedade dsComplemento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsComplemento(String value) {
        this.dsComplemento = value;
    }

    /**
     * Obtém o valor da propriedade dsReferencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsReferencia() {
        return dsReferencia;
    }

    /**
     * Define o valor da propriedade dsReferencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsReferencia(String value) {
        this.dsReferencia = value;
    }

    /**
     * Obtém o valor da propriedade dsBairro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsBairro() {
        return dsBairro;
    }

    /**
     * Define o valor da propriedade dsBairro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsBairro(String value) {
        this.dsBairro = value;
    }

    /**
     * Obtém o valor da propriedade dsMunicipio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsMunicipio() {
        return dsMunicipio;
    }

    /**
     * Define o valor da propriedade dsMunicipio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsMunicipio(String value) {
        this.dsMunicipio = value;
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
     * Obtém o valor da propriedade dsObjetoSocial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDsObjetoSocial() {
        return dsObjetoSocial;
    }

    /**
     * Define o valor da propriedade dsObjetoSocial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDsObjetoSocial(String value) {
        this.dsObjetoSocial = value;
    }

    /**
     * Obtém o valor da propriedade dadosCnae.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCnae }
     *     
     */
    public ArrayOfCnae getDadosCnae() {
        return dadosCnae;
    }

    /**
     * Define o valor da propriedade dadosCnae.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCnae }
     *     
     */
    public void setDadosCnae(ArrayOfCnae value) {
        this.dadosCnae = value;
    }

    /**
     * Obtém o valor da propriedade dadosAtividadeAuxiliar.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAuxiliar }
     *     
     */
    public ArrayOfAuxiliar getDadosAtividadeAuxiliar() {
        return dadosAtividadeAuxiliar;
    }

    /**
     * Define o valor da propriedade dadosAtividadeAuxiliar.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAuxiliar }
     *     
     */
    public void setDadosAtividadeAuxiliar(ArrayOfAuxiliar value) {
        this.dadosAtividadeAuxiliar = value;
    }

    /**
     * Obtém o valor da propriedade dadosResposta.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRespostaInfo }
     *     
     */
    public ArrayOfRespostaInfo getDadosResposta() {
        return dadosResposta;
    }

    /**
     * Define o valor da propriedade dadosResposta.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRespostaInfo }
     *     
     */
    public void setDadosResposta(ArrayOfRespostaInfo value) {
        this.dadosResposta = value;
    }

    /**
     * Obtém o valor da propriedade dadosMotivo.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMotivoTipoViabilidade }
     *     
     */
    public ArrayOfMotivoTipoViabilidade getDadosMotivo() {
        return dadosMotivo;
    }

    /**
     * Define o valor da propriedade dadosMotivo.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMotivoTipoViabilidade }
     *     
     */
    public void setDadosMotivo(ArrayOfMotivoTipoViabilidade value) {
        this.dadosMotivo = value;
    }

}
