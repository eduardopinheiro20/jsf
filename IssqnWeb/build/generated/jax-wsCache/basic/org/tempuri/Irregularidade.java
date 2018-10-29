
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Irregularidade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Irregularidade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdOrgao" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Descricao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Detalhamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Excluida" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CpfAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "Irregularidade", propOrder = {
    "idOrgao",
    "descricao",
    "detalhamento",
    "codigo",
    "excluida",
    "cpfAut"
})
public class Irregularidade {

    @XmlElement(name = "IdOrgao")
    protected int idOrgao;
    @XmlElement(name = "Descricao")
    protected String descricao;
    @XmlElement(name = "Detalhamento")
    protected String detalhamento;
    @XmlElement(name = "Codigo")
    protected int codigo;
    @XmlElement(name = "Excluida")
    protected boolean excluida;
    @XmlElement(name = "CpfAut")
    protected String cpfAut;
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
     * Obtém o valor da propriedade detalhamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetalhamento() {
        return detalhamento;
    }

    /**
     * Define o valor da propriedade detalhamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetalhamento(String value) {
        this.detalhamento = value;
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
     * Obtém o valor da propriedade excluida.
     * 
     */
    public boolean isExcluida() {
        return excluida;
    }

    /**
     * Define o valor da propriedade excluida.
     * 
     */
    public void setExcluida(boolean value) {
        this.excluida = value;
    }

    /**
     * Obtém o valor da propriedade cpfAut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCpfAut() {
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
    public void setCpfAut(String value) {
        this.cpfAut = value;
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
