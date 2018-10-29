
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de Municipio complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="Municipio">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Estado" type="{http://tempuri.org/}Estado" minOccurs="0"/>
 *         &lt;element name="SiglaEstado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Conveniado" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CepGeral" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CodigoCONCLA" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CPFAlteradoPor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Excluido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MascaraSQL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoTOM" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IDMunicipio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PossuiConvenio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CodigoConcla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Brasao" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="CPFAut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Meses" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "Municipio", propOrder = {
    "nome",
    "estado",
    "siglaEstado",
    "conveniado",
    "cepGeral",
    "codigoCONCLA",
    "cpfAlteradoPor",
    "excluido",
    "mascaraSQL",
    "codigoTOM",
    "idMunicipio",
    "possuiConvenio",
    "codigoConcla",
    "brasao",
    "cpfAut",
    "meses"
})
public class Municipio {

    @XmlElement(name = "Nome")
    protected String nome;
    @XmlElement(name = "Estado")
    protected Estado estado;
    @XmlElement(name = "SiglaEstado")
    protected String siglaEstado;
    @XmlElement(name = "Conveniado")
    protected boolean conveniado;
    @XmlElement(name = "CepGeral")
    protected boolean cepGeral;
    @XmlElement(name = "CodigoCONCLA")
    protected int codigoCONCLA;
    @XmlElement(name = "CPFAlteradoPor")
    protected String cpfAlteradoPor;
    @XmlElement(name = "Excluido")
    protected boolean excluido;
    @XmlElement(name = "MascaraSQL")
    protected String mascaraSQL;
    @XmlElement(name = "CodigoTOM")
    protected int codigoTOM;
    @XmlElement(name = "IDMunicipio")
    protected int idMunicipio;
    @XmlElement(name = "PossuiConvenio")
    protected int possuiConvenio;
    @XmlElement(name = "CodigoConcla")
    protected String codigoConcla;
    @XmlElement(name = "Brasao")
    protected byte[] brasao;
    @XmlElement(name = "CPFAut")
    protected String cpfAut;
    @XmlElement(name = "Meses")
    protected String meses;
    @XmlAttribute(name = "Id", required = true)
    protected int id;

    /**
     * Obtém o valor da propriedade nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o valor da propriedade nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Obtém o valor da propriedade estado.
     * 
     * @return
     *     possible object is
     *     {@link Estado }
     *     
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Define o valor da propriedade estado.
     * 
     * @param value
     *     allowed object is
     *     {@link Estado }
     *     
     */
    public void setEstado(Estado value) {
        this.estado = value;
    }

    /**
     * Obtém o valor da propriedade siglaEstado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaEstado() {
        return siglaEstado;
    }

    /**
     * Define o valor da propriedade siglaEstado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaEstado(String value) {
        this.siglaEstado = value;
    }

    /**
     * Obtém o valor da propriedade conveniado.
     * 
     */
    public boolean isConveniado() {
        return conveniado;
    }

    /**
     * Define o valor da propriedade conveniado.
     * 
     */
    public void setConveniado(boolean value) {
        this.conveniado = value;
    }

    /**
     * Obtém o valor da propriedade cepGeral.
     * 
     */
    public boolean isCepGeral() {
        return cepGeral;
    }

    /**
     * Define o valor da propriedade cepGeral.
     * 
     */
    public void setCepGeral(boolean value) {
        this.cepGeral = value;
    }

    /**
     * Obtém o valor da propriedade codigoCONCLA.
     * 
     */
    public int getCodigoCONCLA() {
        return codigoCONCLA;
    }

    /**
     * Define o valor da propriedade codigoCONCLA.
     * 
     */
    public void setCodigoCONCLA(int value) {
        this.codigoCONCLA = value;
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
     * Obtém o valor da propriedade mascaraSQL.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMascaraSQL() {
        return mascaraSQL;
    }

    /**
     * Define o valor da propriedade mascaraSQL.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMascaraSQL(String value) {
        this.mascaraSQL = value;
    }

    /**
     * Obtém o valor da propriedade codigoTOM.
     * 
     */
    public int getCodigoTOM() {
        return codigoTOM;
    }

    /**
     * Define o valor da propriedade codigoTOM.
     * 
     */
    public void setCodigoTOM(int value) {
        this.codigoTOM = value;
    }

    /**
     * Obtém o valor da propriedade idMunicipio.
     * 
     */
    public int getIDMunicipio() {
        return idMunicipio;
    }

    /**
     * Define o valor da propriedade idMunicipio.
     * 
     */
    public void setIDMunicipio(int value) {
        this.idMunicipio = value;
    }

    /**
     * Obtém o valor da propriedade possuiConvenio.
     * 
     */
    public int getPossuiConvenio() {
        return possuiConvenio;
    }

    /**
     * Define o valor da propriedade possuiConvenio.
     * 
     */
    public void setPossuiConvenio(int value) {
        this.possuiConvenio = value;
    }

    /**
     * Obtém o valor da propriedade codigoConcla.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoConcla() {
        return codigoConcla;
    }

    /**
     * Define o valor da propriedade codigoConcla.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoConcla(String value) {
        this.codigoConcla = value;
    }

    /**
     * Obtém o valor da propriedade brasao.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBrasao() {
        return brasao;
    }

    /**
     * Define o valor da propriedade brasao.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBrasao(byte[] value) {
        this.brasao = value;
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
     * Obtém o valor da propriedade meses.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeses() {
        return meses;
    }

    /**
     * Define o valor da propriedade meses.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeses(String value) {
        this.meses = value;
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
