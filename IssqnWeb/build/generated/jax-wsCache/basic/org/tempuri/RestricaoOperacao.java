
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de RestricaoOperacao complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="RestricaoOperacao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdOrgao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Titulo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Texto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "RestricaoOperacao", propOrder = {
    "idOrgao",
    "titulo",
    "texto",
    "codigo",
    "excluido",
    "cpfAlteradoPor"
})
public class RestricaoOperacao {

    @XmlElement(name = "IdOrgao")
    protected int idOrgao;
    @XmlElement(name = "Titulo")
    protected String titulo;
    @XmlElement(name = "Texto")
    protected String texto;
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
     * Obtém o valor da propriedade titulo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o valor da propriedade titulo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
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
