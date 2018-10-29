
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Atividade complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Atividade">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Descricao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo" type="{http://tempuri.org/}TipoAtividade"/>
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
@XmlType(name = "Atividade", propOrder = {
    "descricao",
    "tipo",
    "excluido",
    "cpfAlteradoPor"
})
@XmlSeeAlso({
    AtividadeAuxiliar.class,
    AtividadeCNAE.class
})
public class Atividade {

    @XmlElement(name = "Descricao")
    protected String descricao;
    @XmlElement(name = "Tipo", required = true)
    @XmlSchemaType(name = "string")
    protected TipoAtividade tipo;
    @XmlElement(name = "Excluido")
    protected boolean excluido;
    @XmlElement(name = "CPFAlteradoPor")
    protected String cpfAlteradoPor;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

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
     * Obtém o valor da propriedade tipo.
     * 
     * @return
     *     possible object is
     *     {@link TipoAtividade }
     *     
     */
    public TipoAtividade getTipo() {
        return tipo;
    }

    /**
     * Define o valor da propriedade tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoAtividade }
     *     
     */
    public void setTipo(TipoAtividade value) {
        this.tipo = value;
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
