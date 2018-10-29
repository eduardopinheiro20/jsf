
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java de PerguntaResposta complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="PerguntaResposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idSolicitacao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idPergunta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idOrgao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Resposta" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DataInclusao" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CPFAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pergunta" type="{http://tempuri.org/}Pergunta" minOccurs="0"/>
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
@XmlType(name = "PerguntaResposta", propOrder = {
    "idSolicitacao",
    "idPergunta",
    "idOrgao",
    "resposta",
    "dataInclusao",
    "cpfAut",
    "pergunta"
})
public class PerguntaResposta {

    protected int idSolicitacao;
    protected int idPergunta;
    protected int idOrgao;
    @XmlElement(name = "Resposta")
    protected boolean resposta;
    @XmlElement(name = "DataInclusao", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInclusao;
    @XmlElement(name = "CPFAut")
    protected String cpfAut;
    @XmlElement(name = "Pergunta")
    protected Pergunta pergunta;
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
     * Obtém o valor da propriedade idPergunta.
     * 
     */
    public int getIdPergunta() {
        return idPergunta;
    }

    /**
     * Define o valor da propriedade idPergunta.
     * 
     */
    public void setIdPergunta(int value) {
        this.idPergunta = value;
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
     * Obtém o valor da propriedade resposta.
     * 
     */
    public boolean isResposta() {
        return resposta;
    }

    /**
     * Define o valor da propriedade resposta.
     * 
     */
    public void setResposta(boolean value) {
        this.resposta = value;
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
     * Obtém o valor da propriedade pergunta.
     * 
     * @return
     *     possible object is
     *     {@link Pergunta }
     *     
     */
    public Pergunta getPergunta() {
        return pergunta;
    }

    /**
     * Define o valor da propriedade pergunta.
     * 
     * @param value
     *     allowed object is
     *     {@link Pergunta }
     *     
     */
    public void setPergunta(Pergunta value) {
        this.pergunta = value;
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
