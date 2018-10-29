
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de RespostaInfo complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="RespostaInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDSecretaria" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Resposta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDPergunta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Pergunta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Orientacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idRisco" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="Risco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDCnae" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespostaInfo", propOrder = {
    "idSecretaria",
    "resposta",
    "idPergunta",
    "pergunta",
    "orientacao",
    "idRisco",
    "risco",
    "idCnae"
})
public class RespostaInfo {

    @XmlElement(name = "IDSecretaria")
    protected int idSecretaria;
    @XmlElement(name = "Resposta")
    protected String resposta;
    @XmlElement(name = "IDPergunta")
    protected int idPergunta;
    @XmlElement(name = "Pergunta")
    protected String pergunta;
    @XmlElement(name = "Orientacao")
    protected String orientacao;
    @XmlSchemaType(name = "unsignedByte")
    protected short idRisco;
    @XmlElement(name = "Risco")
    protected String risco;
    @XmlElement(name = "IDCnae")
    protected String idCnae;

    /**
     * Obtém o valor da propriedade idSecretaria.
     * 
     */
    public int getIDSecretaria() {
        return idSecretaria;
    }

    /**
     * Define o valor da propriedade idSecretaria.
     * 
     */
    public void setIDSecretaria(int value) {
        this.idSecretaria = value;
    }

    /**
     * Obtém o valor da propriedade resposta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResposta() {
        return resposta;
    }

    /**
     * Define o valor da propriedade resposta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResposta(String value) {
        this.resposta = value;
    }

    /**
     * Obtém o valor da propriedade idPergunta.
     * 
     */
    public int getIDPergunta() {
        return idPergunta;
    }

    /**
     * Define o valor da propriedade idPergunta.
     * 
     */
    public void setIDPergunta(int value) {
        this.idPergunta = value;
    }

    /**
     * Obtém o valor da propriedade pergunta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPergunta() {
        return pergunta;
    }

    /**
     * Define o valor da propriedade pergunta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPergunta(String value) {
        this.pergunta = value;
    }

    /**
     * Obtém o valor da propriedade orientacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrientacao() {
        return orientacao;
    }

    /**
     * Define o valor da propriedade orientacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrientacao(String value) {
        this.orientacao = value;
    }

    /**
     * Obtém o valor da propriedade idRisco.
     * 
     */
    public short getIdRisco() {
        return idRisco;
    }

    /**
     * Define o valor da propriedade idRisco.
     * 
     */
    public void setIdRisco(short value) {
        this.idRisco = value;
    }

    /**
     * Obtém o valor da propriedade risco.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRisco() {
        return risco;
    }

    /**
     * Define o valor da propriedade risco.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRisco(String value) {
        this.risco = value;
    }

    /**
     * Obtém o valor da propriedade idCnae.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCnae() {
        return idCnae;
    }

    /**
     * Define o valor da propriedade idCnae.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCnae(String value) {
        this.idCnae = value;
    }

}
