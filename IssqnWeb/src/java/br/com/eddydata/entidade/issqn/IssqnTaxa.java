/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.eddydata.entidade.issqn;

import br.com.eddydata.entidade.geral.Banco;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author eddydata
 */
@Entity
@Table(name = "issqn_taxa")
public class IssqnTaxa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_taxa", length = 8)
    private Integer id;
    @NotNull
    @Column(name = "nome", length = 80)
    private String nome;
    @Column(name = "nivel")
    private Integer nivel;
    @Column(name = "valor")
    private Double valor;
    @Column(name = "tp_valor", length = 10)
    private String tpValor;
    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "cobra")
    private Integer cobra;
    @Column(name = "cobra_atividade")
    private Integer cobraAtividade;
    @Column(name = "estimado")
    private Integer estimado;
    @Column(name = "receita_orcamentaria")
    private Integer receitaOrcamentaria;
    @Column(name = "receita_orcamentaria_juros")
    private Integer receitaOrcamentariaJuros;
    @Column(name = "receita_orcamentaria_multa")
    private Integer receitaOrcamentariaMulta;
    @Column(name = "receita_orcamentaria_correcao")
    private Integer receitaOrcamentariaCorrecao;
    @Column(name = "is_taxa")
    private Integer isTaxa;

    @JoinColumn(name = "id_parente", referencedColumnName = "id_taxa")
    @ManyToOne(fetch = FetchType.LAZY)
    private IssqnTaxa parente;
    @JoinColumn(name = "id_banco", referencedColumnName = "id_banco")
    @ManyToOne(fetch = FetchType.LAZY)
    private Banco banco;
    @Column(name = "taxa_fixa")
    private Boolean taxaFixa;
    
    @Transient
    private boolean selecionado;
    
    @Transient
    private IssqnContribuinteTaxa contribuinteTaxa;

    public IssqnTaxa() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTpValor() {
        return tpValor;
    }

    public void setTpValor(String tpValor) {
        this.tpValor = tpValor;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getCobra() {
        return cobra;
    }

    public void setCobra(Integer cobra) {
        this.cobra = cobra;
    }

    public Integer getCobraAtividade() {
        return cobraAtividade;
    }

    public void setCobraAtividade(Integer cobraAtividade) {
        this.cobraAtividade = cobraAtividade;
    }

    public Integer getEstimado() {
        return estimado;
    }

    public void setEstimado(Integer estimado) {
        this.estimado = estimado;
    }

    public Integer getReceitaOrcamentaria() {
        return receitaOrcamentaria;
    }

    public void setReceitaOrcamentaria(Integer receitaOrcamentaria) {
        this.receitaOrcamentaria = receitaOrcamentaria;
    }

    public Integer getReceitaOrcamentariaJuros() {
        return receitaOrcamentariaJuros;
    }

    public void setReceitaOrcamentariaJuros(Integer receitaOrcamentariaJuros) {
        this.receitaOrcamentariaJuros = receitaOrcamentariaJuros;
    }

    public Integer getReceitaOrcamentariaMulta() {
        return receitaOrcamentariaMulta;
    }

    public void setReceitaOrcamentariaMulta(Integer receitaOrcamentariaMulta) {
        this.receitaOrcamentariaMulta = receitaOrcamentariaMulta;
    }

    public Integer getReceitaOrcamentariaCorrecao() {
        return receitaOrcamentariaCorrecao;
    }

    public void setReceitaOrcamentariaCorrecao(Integer receitaOrcamentariaCorrecao) {
        this.receitaOrcamentariaCorrecao = receitaOrcamentariaCorrecao;
    }

    public Integer getIsTaxa() {
        return isTaxa;
    }

    public void setIsTaxa(Integer isTaxa) {
        this.isTaxa = isTaxa;
    }

    public IssqnTaxa getParente() {
        return parente;
    }

    public void setParente(IssqnTaxa parente) {
        this.parente = parente;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public IssqnContribuinteTaxa getContribuinteTaxa() {
        return contribuinteTaxa;
    }

    public void setContribuinteTaxa(IssqnContribuinteTaxa contribuinteTaxa) {
        this.contribuinteTaxa = contribuinteTaxa;
    }

    public Boolean getTaxaFixa() {
        return taxaFixa;
    }

    public void setTaxaFixa(Boolean taxaFixa) {
        this.taxaFixa = taxaFixa;
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
        if (!(object instanceof IssqnTaxa)) {
            return false;
        }
        IssqnTaxa other = (IssqnTaxa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IssqnTaxa[ idTaxa=" + id + " ]";
    }
}
