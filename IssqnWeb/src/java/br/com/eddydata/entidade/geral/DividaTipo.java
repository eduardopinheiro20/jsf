/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.geral;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "divida_tipo")
public class DividaTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Integer id;
    @Column(name = "vl_juros")
    private Double vlJuros;
    @Column(name = "vl_multa")
    private Double vlMulta;
    @Column(name = "e_tributario")
    private Short eTributario;
    @Column(name = "cobrar_guia")
    private Short cobrarGuia;
    @Column(name = "tipo_cobranca")
    private Integer tipoCobranca;
    @Column(name = "valor")
    private Double valor;
    @Column(name = "fixo")
    private Integer fixo;
    @NotNull
    @Column(name = "descricao", length = 80)
    private String descricao;
    @Column(name = "id_tipo_basico")
    private Integer idTipoBasico;
    @Column(name = "receita_orcamentaria", length = 70)
    private String receitaOrcamentaria;
    @Column(name = "receita_orcamentaria_juros", length = 70)
    private String receitaOrcamentariaJuros;
    @Column(name = "receita_orcamentaria_correcao", length = 70)
    private String receitaOrcamentariaCorrecao;
    @Column(name = "cod_tipo")
    private Integer codTipo;
    @Column(name = "receita_orcamentaria_multa", length = 70)
    private String receitaOrcamentariaMulta;

    public DividaTipo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVlJuros() {
        return vlJuros;
    }

    public void setVlJuros(Double vlJuros) {
        this.vlJuros = vlJuros;
    }

    public Double getVlMulta() {
        return vlMulta;
    }

    public void setVlMulta(Double vlMulta) {
        this.vlMulta = vlMulta;
    }

    public Short geteTributario() {
        return eTributario;
    }

    public void seteTributario(Short eTributario) {
        this.eTributario = eTributario;
    }

    public Short getCobrarGuia() {
        return cobrarGuia;
    }

    public void setCobrarGuia(Short cobrarGuia) {
        this.cobrarGuia = cobrarGuia;
    }

    public Integer getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(Integer tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getFixo() {
        return fixo;
    }

    public void setFixo(Integer fixo) {
        this.fixo = fixo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdTipoBasico() {
        return idTipoBasico;
    }

    public void setIdTipoBasico(Integer idTipoBasico) {
        this.idTipoBasico = idTipoBasico;
    }

    public String getReceitaOrcamentaria() {
        return receitaOrcamentaria;
    }

    public void setReceitaOrcamentaria(String receitaOrcamentaria) {
        this.receitaOrcamentaria = receitaOrcamentaria;
    }

    public String getReceitaOrcamentariaJuros() {
        return receitaOrcamentariaJuros;
    }

    public void setReceitaOrcamentariaJuros(String receitaOrcamentariaJuros) {
        this.receitaOrcamentariaJuros = receitaOrcamentariaJuros;
    }

    public String getReceitaOrcamentariaCorrecao() {
        return receitaOrcamentariaCorrecao;
    }

    public void setReceitaOrcamentariaCorrecao(String receitaOrcamentariaCorrecao) {
        this.receitaOrcamentariaCorrecao = receitaOrcamentariaCorrecao;
    }

    public Integer getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(Integer codTipo) {
        this.codTipo = codTipo;
    }

    public String getReceitaOrcamentariaMulta() {
        return receitaOrcamentariaMulta;
    }

    public void setReceitaOrcamentariaMulta(String receitaOrcamentariaMulta) {
        this.receitaOrcamentariaMulta = receitaOrcamentariaMulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DividaTipo)) {
            return false;
        }
        DividaTipo other = (DividaTipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DividaTipo[ idTipo=" + id + " ]";
    }

}
