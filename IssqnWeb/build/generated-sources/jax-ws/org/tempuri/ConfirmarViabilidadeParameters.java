
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de ConfirmarViabilidadeParameters complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="ConfirmarViabilidadeParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="protcolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cnpj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nire" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cep" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bairro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="complemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idMunicipio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cpfSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dadosImovel" type="{http://tempuri.org/}DadosImovel" minOccurs="0"/>
 *         &lt;element name="dadosCnae" type="{http://tempuri.org/}ArrayOfCnae" minOccurs="0"/>
 *         &lt;element name="dadosAtividadeAuxiliar" type="{http://tempuri.org/}ArrayOfAuxiliar" minOccurs="0"/>
 *         &lt;element name="dadosResposta" type="{http://tempuri.org/}ArrayOfRespostaInfo" minOccurs="0"/>
 *         &lt;element name="idAplicacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmarViabilidadeParameters", propOrder = {
    "protcolo",
    "cnpj",
    "nire",
    "tipoAto",
    "status",
    "cep",
    "logradouro",
    "bairro",
    "numero",
    "complemento",
    "referencia",
    "idMunicipio",
    "cpfSolicitante",
    "dadosImovel",
    "dadosCnae",
    "dadosAtividadeAuxiliar",
    "dadosResposta",
    "idAplicacao"
})
public class ConfirmarViabilidadeParameters {

    protected String protcolo;
    protected String cnpj;
    protected String nire;
    protected int tipoAto;
    protected int status;
    protected String cep;
    protected String logradouro;
    protected String bairro;
    protected String numero;
    protected String complemento;
    protected String referencia;
    protected int idMunicipio;
    protected String cpfSolicitante;
    protected DadosImovel dadosImovel;
    protected ArrayOfCnae dadosCnae;
    protected ArrayOfAuxiliar dadosAtividadeAuxiliar;
    protected ArrayOfRespostaInfo dadosResposta;
    protected String idAplicacao;

    /**
     * Obtém o valor da propriedade protcolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtcolo() {
        return protcolo;
    }

    /**
     * Define o valor da propriedade protcolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtcolo(String value) {
        this.protcolo = value;
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
     * Obtém o valor da propriedade tipoAto.
     * 
     */
    public int getTipoAto() {
        return tipoAto;
    }

    /**
     * Define o valor da propriedade tipoAto.
     * 
     */
    public void setTipoAto(int value) {
        this.tipoAto = value;
    }

    /**
     * Obtém o valor da propriedade status.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Define o valor da propriedade status.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
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
     * Obtém o valor da propriedade logradouro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Define o valor da propriedade logradouro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogradouro(String value) {
        this.logradouro = value;
    }

    /**
     * Obtém o valor da propriedade bairro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Define o valor da propriedade bairro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBairro(String value) {
        this.bairro = value;
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
     * Obtém o valor da propriedade complemento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Define o valor da propriedade complemento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplemento(String value) {
        this.complemento = value;
    }

    /**
     * Obtém o valor da propriedade referencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Define o valor da propriedade referencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia(String value) {
        this.referencia = value;
    }

    /**
     * Obtém o valor da propriedade idMunicipio.
     * 
     */
    public int getIdMunicipio() {
        return idMunicipio;
    }

    /**
     * Define o valor da propriedade idMunicipio.
     * 
     */
    public void setIdMunicipio(int value) {
        this.idMunicipio = value;
    }

    /**
     * Obtém o valor da propriedade cpfSolicitante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpfSolicitante() {
        return cpfSolicitante;
    }

    /**
     * Define o valor da propriedade cpfSolicitante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCpfSolicitante(String value) {
        this.cpfSolicitante = value;
    }

    /**
     * Obtém o valor da propriedade dadosImovel.
     * 
     * @return
     *     possible object is
     *     {@link DadosImovel }
     *     
     */
    public DadosImovel getDadosImovel() {
        return dadosImovel;
    }

    /**
     * Define o valor da propriedade dadosImovel.
     * 
     * @param value
     *     allowed object is
     *     {@link DadosImovel }
     *     
     */
    public void setDadosImovel(DadosImovel value) {
        this.dadosImovel = value;
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
     * Obtém o valor da propriedade idAplicacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAplicacao() {
        return idAplicacao;
    }

    /**
     * Define o valor da propriedade idAplicacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAplicacao(String value) {
        this.idAplicacao = value;
    }

}
