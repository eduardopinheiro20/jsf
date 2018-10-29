
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Pergunta complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Pergunta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdOrgao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Texto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Descricao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Explicacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Declaracao" type="{http://tempuri.org/}Declaracao" minOccurs="0"/>
 *         &lt;element name="Codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Excluido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CPFAlteradoPor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "Pergunta", propOrder = {
    "idOrgao",
    "texto",
    "descricao",
    "explicacao",
    "declaracao",
    "codigo",
    "excluido",
    "cpfAlteradoPor"
})
public class Pergunta {

    @XmlElement(name = "IdOrgao")
    protected int idOrgao;
    @XmlElement(name = "Texto")
    protected String texto;
    @XmlElement(name = "Descricao")
    protected String descricao;
    @XmlElement(name = "Explicacao")
    protected String explicacao;
    @XmlElement(name = "Declaracao")
    protected Declaracao declaracao;
    @XmlElement(name = "Codigo")
    protected int codigo;
    @XmlElement(name = "Excluido")
    protected boolean excluido;
    @XmlElement(name = "CPFAlteradoPor")
    protected String cpfAlteradoPor;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

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
     * Obtém o valor da propriedade texto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Define o valor da propriedade texto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTexto(String value) {
        this.texto = value;
    }

    /**
     * Obtém o valor da propriedade descricao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define o valor da propriedade descricao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricao(String value) {
        this.descricao = value;
    }

    /**
     * Obtém o valor da propriedade explicacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExplicacao() {
        return explicacao;
    }

    /**
     * Define o valor da propriedade explicacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExplicacao(String value) {
        this.explicacao = value;
    }

    /**
     * Obtém o valor da propriedade declaracao.
     * 
     * @return
     *     possible object is
     *     {@link Declaracao }
     *     
     */
    public Declaracao getDeclaracao() {
        return declaracao;
    }

    /**
     * Define o valor da propriedade declaracao.
     * 
     * @param value
     *     allowed object is
     *     {@link Declaracao }
     *     
     */
    public void setDeclaracao(Declaracao value) {
        this.declaracao = value;
    }

    /**
     * Obtém o valor da propriedade codigo.
     * 
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Define o valor da propriedade codigo.
     * 
     */
    public void setCodigo(int value) {
        this.codigo = value;
    }

    /**
     * Obtém o valor da propriedade excluido.
     * 
     */
    public boolean isExcluido() {
        return excluido;
    }

    /**
     * Define o valor da propriedade excluido.
     * 
     */
    public void setExcluido(boolean value) {
        this.excluido = value;
    }

    /**
     * Obtém o valor da propriedade cpfAlteradoPor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPFAlteradoPor() {
        return cpfAlteradoPor;
    }

    /**
     * Define o valor da propriedade cpfAlteradoPor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPFAlteradoPor(String value) {
        this.cpfAlteradoPor = value;
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
